package nolofinder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Model {

    private String bookstoreFileLocation;
    private String roomlistFileLocation;
    private final ArrayList<Course> courses = new ArrayList<>();

    void setBookstoreFileLocation(String fileLocation) {
        bookstoreFileLocation = fileLocation;
    }

    void setRoomlistFileLocation(String fileLocation) {
        roomlistFileLocation = fileLocation;
    }

    String getBookstoreFile() {
        return bookstoreFileLocation;
    }

    String getRoomlistFile() {
        return roomlistFileLocation;
    }

    void importBookstoreData() throws IOException, CsvValidationException {
        //check if there is a file to work on
        if (bookstoreFileLocation != null && !bookstoreFileLocation.equals("")) {

            int currentCourse = 0; //index for the current course
            final int SUBJECT_INDEX = 0;
            final int DURATION_INDEX = 10;
            final int TITLE_INDEX = 5; //is also the same location as the instructor name
            final int PRICE_INDEX = 13;
            final int REQUIREMENT_INDEX = 2;
            final int SEMESTER_INDEX = 3;
            String[] nextLine; //an array of values from the current line in the csv file


            //reads in CSV file
            CSVReader reader = new CSVReader(new FileReader(bookstoreFileLocation));

            while ((nextLine = reader.readNext()) != null) {

                //if the subject code is not empty, add a new course and relevant information
                if (!nextLine[SUBJECT_INDEX].isEmpty()) {
                    courses.add(new Course());
                    currentCourse = courses.size() - 1;

                    //make sure it's an actual course number
                    //I spent too long figuring out why I was getting out of bounds exceptions
                    if (!nextLine[SUBJECT_INDEX].equals("COURSE")) {

                        //splits string by spaces to separate subject code and course number
                        //replaces ( with a space to remove (All sections)
                        String[] split = nextLine[SUBJECT_INDEX].trim().replace("(", " ").split(" ");

                        courses.get(currentCourse).setCourseSubject(split[0]);
                        courses.get(currentCourse).setCourseNumber(split[1]);
                        //completely removes any lingering "All"s that were connected to other characters
                        //without a space
                        courses.get(currentCourse).setCourseSection(split[2].replaceAll("[aA]ll", ""));

                        //if a course section is empty it's because it was stored in the CSV as
                        // "All Sections" and was thus removed, this replaces it with "A" (meaning all sections)
                        if(courses.get(currentCourse).getCourseSection().isEmpty()) {
                            courses.get(currentCourse).setCourseSection("A");
                        }

                    }

                    //if no subject code was found, it is assumed to be a book
                    //adds all books in course
                } else if (!nextLine[DURATION_INDEX].isEmpty()) {

                    //add the book with the title and price
                    courses.get(currentCourse).addBook(nextLine[TITLE_INDEX], nextLine[PRICE_INDEX], nextLine[REQUIREMENT_INDEX]);

                    //instructor names are on the same line as the listed semester, so if there's a semester
                    //there's an instructor
                    //makes sure that there's a course to add the instructor name to first
                } else if (!nextLine[SEMESTER_INDEX].isEmpty() && !courses.isEmpty()) {

                    //instructor name is stored at same index as the book titles
                    courses.get(currentCourse).setInstructorName(nextLine[TITLE_INDEX]);
                }

            }

            //removes the "COURSE" header from list of courses
            courses.remove(0);

        }
    }

    void importRoomlistData() throws IOException, CsvValidationException {
        //check if there is a file to work on and that there are courses to check
        if (roomlistFileLocation != null && !roomlistFileLocation.equals("") && !courses.isEmpty()) {

            final int CRN_INDEX = 4;
            final int SUBJECT_INDEX = 5;
            final int COURSE_NUM_INDEX = 6;
            final int SECTION_INDEX = 7;
            final int TITLE_INDEX = 8;
            final int EMAIL_INDEX = 30;
            String[] nextLine; //an array of values from the current line in the csv file


            //reads in CSV file
            CSVReader reader = new CSVReader(new FileReader(roomlistFileLocation));

            while ((nextLine = reader.readNext()) != null) {

                //for every course, check if the subject, course number, and section are the same
                //if they are, add the relevant information
                for (Course currentCourse : courses) {

                    if (currentCourse.getCourseSubject().equals(nextLine[SUBJECT_INDEX])
                    && currentCourse.getCourseNumber().equals(nextLine[COURSE_NUM_INDEX])
                    && currentCourse.getCourseSection().equals(nextLine[SECTION_INDEX])) {

                        currentCourse.setCrn(nextLine[CRN_INDEX]);
                        currentCourse.setCourseName(nextLine[TITLE_INDEX]);
                        currentCourse.setInstructorEmail(nextLine[EMAIL_INDEX]);
                    }
                }

            }

            for (Course course : courses) {
                System.out.print(course.getCourseSubject() + " " + course.getCourseNumber() + " ");
                System.out.println(course.getCourseSection() + " " + course.getCrn() + " " + course.getCourseName() + " " + course.getInstructorEmail());
            }
        }
    }

}
