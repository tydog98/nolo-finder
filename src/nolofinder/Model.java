package nolofinder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Model {

    private String bookstoreFileLocation;
    private String roomlistFileLocation;
    private ArrayList<Course> courses = new ArrayList<>();

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
                        String[] split = nextLine[SUBJECT_INDEX].trim().split(" ");

                        courses.get(currentCourse).setCourseSubject(split[0]);
                        courses.get(currentCourse).setCourseNumber(split[1]);

                        //all course numbers end with N, so filtering "N " will split the
                        //course section while keeping the "All Sections" whole
                        split = nextLine[SUBJECT_INDEX].trim().split("N ");

                        courses.get(currentCourse).setCourseSection(split[1]);

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
        //check if there is a file to work on
        if (roomlistFileLocation != null && !roomlistFileLocation.equals("")) {

            int currentCourse = 0; //index for the current course
            String[] nextLine; //an array of values from the current line in the csv file


            //reads in CSV file
            CSVReader reader = new CSVReader(new FileReader(roomlistFileLocation));

            while ((nextLine = reader.readNext()) != null) {

            }
        }
    }

}
