package nolofinder;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.ICSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;

public class Model {

    private String bookstoreFileLocation;
    private String roomlistFileLocation;
    private File saveFile;
    private final ArrayList<Course> courses = new ArrayList<>();
    private boolean isCommandLine = false;

    void setBookstoreFileLocation(String fileLocation) {
        bookstoreFileLocation = fileLocation;
    }

    void setRoomlistFileLocation(String fileLocation) {
        roomlistFileLocation = fileLocation;
    }

    void setSaveFile(File newFile) {
        saveFile = newFile;
    }

    void setCommandLine(boolean bool){
        isCommandLine = bool;
    }

    String getBookstoreFile() {
        return bookstoreFileLocation;
    }

    String getRoomlistFile() {
        return roomlistFileLocation;
    }

    void importBookstoreData() throws IOException, CsvValidationException {
        //check if there is a file to work on
        if (bookstoreFileLocation != null && !bookstoreFileLocation.isEmpty()) {

            //reads in CSV file
            CSVReader reader = new CSVReader(new FileReader(bookstoreFileLocation));
            String[] nextLine; //an array of values from the current line in the csv file

            //skips first few lines to check header
            reader.readNext();
            reader.readNext();
            reader.readNext();

            nextLine = reader.readNext();

            //check the header to make sure this is a bookstore file
            if (nextLine[1].equals("STORE 0971:NASHUA COMMUNITY COLLEGE BOOKSTORE")) {

                int currentCourse = 0; //index for the current course
                final int SUBJECT_INDEX = 0;
                final int DURATION_INDEX = 10;
                final int INSTRUCTOR_INDEX = 5; //is also the same location as the book titles
                final int PRICE_INDEX = 13;
                final int REQUIREMENT_INDEX = 2;
                final int SEMESTER_INDEX = 3;
                final int ISBN_INDEX = 11;
                final int AUTHOR_INDEX = 4;
                final int TITLE_INDEX = INSTRUCTOR_INDEX;

                //skips headers and goes straight to the data
                reader.readNext();
                reader.readNext();
                reader.readNext();

                while ((nextLine = reader.readNext()) != null) {

                    //if the subject code is not empty, add a new course and relevant information
                    if (!nextLine[SUBJECT_INDEX].isEmpty()) {
                        courses.add(new Course());
                        currentCourse = courses.size() - 1;

                        //splits string by spaces to separate subject code and course number
                        //replaces ( with a space to remove (All sections)
                        String[] split = nextLine[SUBJECT_INDEX].trim()
                                .replace("(", " ").split(" ");

                        courses.get(currentCourse).setCourseSubject(split[0]);
                        courses.get(currentCourse).setCourseNumber(split[1]);
                        //completely removes any lingering "All"s that were connected to other characters
                        //without a space
                        courses.get(currentCourse).setCourseSection(split[2]
                                .replaceAll("[aA]ll", ""));

                        //if a course section is empty it's because it was stored in the CSV as
                        // "All Sections" and was thus removed, this replaces it with "A" (meaning all sections)
                        if (courses.get(currentCourse).getCourseSection().isEmpty()) {
                            courses.get(currentCourse).setCourseSection("A");
                        }

                        //if no subject code was found, it is assumed to be a book
                        //adds all books in course that can be purchased
                    } else if (nextLine[DURATION_INDEX].trim().equals("N/A")
                            || nextLine[DURATION_INDEX].trim().equals("PURCHASE")) {

                        //checks if the current book has an ISBN
                        if (!nextLine[ISBN_INDEX].isEmpty()) {
                            //add the book with the price, it's requirement type, author, and a cleaned title
                            //price removes the dollar sign to make casting to int easier for calculations
                            courses.get(currentCourse).addBook(nextLine[PRICE_INDEX].replace("$", "")
                                    , nextLine[REQUIREMENT_INDEX], cleanTitle(nextLine[TITLE_INDEX], nextLine[AUTHOR_INDEX]));

                            //if there is no ISBN the course is flagged for manual review
                        } else {
                            courses.get(currentCourse).setManualReview(true);
                        }


                        //instructor names are on the same line as the listed semester, so if there's a semester
                        //there's an instructor
                        //makes sure that there's a course to add the instructor name to first
                    } else if (!nextLine[SEMESTER_INDEX].isEmpty() && !courses.isEmpty()) {
                        courses.get(currentCourse).setInstructorName(nextLine[INSTRUCTOR_INDEX]
                                .replace(",", " "));
                    }

                }
                //gives error if wrong header is given
            } else {
                if(!isCommandLine){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Bookstore file is not in correct format and will not be read");
                    alert.showAndWait();
                } else {
                    System.out.println("ERROR: Bookstore file is not in correct format and will not be read");
                }

            }

            //gives error if file cannot be read
        } else {
            if(!isCommandLine){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot read bookstore file");
                alert.showAndWait();
            } else {
                System.out.println("ERROR: Cannot read bookstore file");
            }

        }
    }

    void importRoomlistData() throws IOException, CsvValidationException {
        //check if there is a file to work on and that there are courses to check
        if (roomlistFileLocation != null && !roomlistFileLocation.isEmpty() && !courses.isEmpty()) {

            String[] nextLine; //an array of values from the current line in the csv file

            //reads in CSV file
            CSVReader reader = new CSVReader(new FileReader(roomlistFileLocation));

            nextLine = reader.readNext();

            //check the header to make sure it's a roomlist file
            if (nextLine[0].equals("BLDG") && nextLine[1].equals("RM")) {
                final int CRN_INDEX = 4;
                final int SUBJECT_INDEX = 5;
                final int COURSE_NUM_INDEX = 6;
                final int SECTION_INDEX = 7;
                final int TITLE_INDEX = 8;
                final int EMAIL_INDEX = 30;

                while ((nextLine = reader.readNext()) != null) {

                    //for every course, check if the subject, course number, and section are the same
                    //if they are, add the relevant information
                    for (Course currentCourse : courses) {

                        if (currentCourse.getCourseSubject().equals(nextLine[SUBJECT_INDEX])
                                && currentCourse.getCourseNumber().equals(nextLine[COURSE_NUM_INDEX])
                                && currentCourse.getCourseSection().equals(nextLine[SECTION_INDEX])) {

                            if (!nextLine[CRN_INDEX].isEmpty()) {
                                currentCourse.setCrn(nextLine[CRN_INDEX]);
                            }

                            if (!nextLine[TITLE_INDEX].isEmpty()) {
                                currentCourse.setCourseName(nextLine[TITLE_INDEX]);
                            }

                            if (!nextLine[EMAIL_INDEX].isEmpty()) {
                                currentCourse.setInstructorEmail(nextLine[EMAIL_INDEX]);
                            }
                        }
                    }

                }
            } else {
                if(!isCommandLine){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Roomlist file is not in correct format and will not be read");
                    alert.showAndWait();
                } else {
                    System.out.println("ERROR: Roomlist file is not in correct format and will not be read");
                }

            }
        }
    }

    void claculateNolo(double noloThreshhold) {
        for (Course course : courses) {
            course.calculateNolo(noloThreshhold);
        }

    }

    void outputFile() throws IOException {
        //checks that the path is valid
        if (saveFile != null && !saveFile.getAbsolutePath().isEmpty()) {

            // create a write
            Writer writer = Files.newBufferedWriter(saveFile.toPath());

            // header record
            String[] headerRecord = {"CRN", "SUBJECT", "NUMBER", "SECTION", "COURSE NAME", "COST", "NOLO"
                    , "INSTRUCTOR NAME", "INSTRUCTOR EMAIL", "NOTE"};

            // create a csv writer
            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            // write header record
            csvWriter.writeNext(headerRecord);

            for (Course course : courses) {

                // write data records
                csvWriter.writeNext(new String[]{course.getCrn(), course.getCourseSubject(), course.getCourseNumber()
                        , course.getCourseSection(), course.getCourseName(), course.getTotalCost(), course.isNolo()
                        , course.getInstructorName(), course.getInstructorEmail(), course.getManualReview()});
            }

            // close writers
            csvWriter.close();
            writer.close();

            //removes all data so a new file can be read in
            courses.clear();

            //informs user that processing is done
            if(!isCommandLine){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Conversion complete");
                alert.showAndWait();
            } else {
                System.out.println("Conversion complete");
            }
        } else {
            if (!isCommandLine){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Selected save directory is not valid");
                alert.showAndWait();
            } else {
                System.out.println("ERROR: Selected save directory is not valid");
            }

        }


    }

    //removes common words, whitespace, the authors name, and punctuation from title
    public String cleanTitle(String title, String author) {

        return title.replaceAll("\\(.*$", "").replaceAll("\\b(THE|A|OF|IS|AN|AND)\\b", "")
                .replaceAll(author, "").replaceAll(" ", "")
                .replaceAll("[^0-9a-zA-Z]+", "");

    }

}
