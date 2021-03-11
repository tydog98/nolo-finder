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
            final int TITLE_INDEX = 5;
            final int PRICE_INDEX = 13;
            final int REQUIREMENT_INDEX = 2;
            String[] nextLine; //an array of values from the line


            //reads in CSV file
            CSVReader reader = new CSVReader(new FileReader(bookstoreFileLocation));

            while ((nextLine = reader.readNext()) != null) {

                //if the subject code is not empty, add a new course and relevant information
                if (!nextLine[SUBJECT_INDEX].equals("")) {
                    courses.add(new Course());
                    currentCourse = courses.size() - 1;
                    courses.get(currentCourse).setCourseSubject(nextLine[SUBJECT_INDEX]);

                    //if no subject code was found, it is assumed to be a book
                    //if the duration of the book is N/A or PURCHASE, and the book is required, add the book
                } else if ((nextLine[DURATION_INDEX].equals("   N/A") || nextLine[DURATION_INDEX].equals("   PURCHASE"))
                        && (nextLine[REQUIREMENT_INDEX].equals("REQ") || nextLine[REQUIREMENT_INDEX].equals("CHC"))) {
                    //add the book with the title and price
                    courses.get(currentCourse).addBook(nextLine[TITLE_INDEX], nextLine[PRICE_INDEX]);
                }

            }

            //removes the "COURSE" header from list of courses
            courses.remove(0);

            //print courses and their books for testing purposes
            for (Course course : courses) {
                System.out.println(course.getCourseSubject());
                course.printBookList();
            }
        }
    }

}
