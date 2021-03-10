package nolofinder;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        int currentCourse = 0; //index for the current course
        final int SUBJECT_INDEX = 0;
        final int DURATION_INDEX = 10;
        final int TITLE_INDEX = 5;
        final int PRICE_INDEX = 13;

        //regex pattern for 3-4 character subject codes
        Pattern subjectPattern = Pattern.compile("\\w\\w\\w\\w?");

        //reads in CSV file
        CSVReader reader = new CSVReader(new FileReader(bookstoreFileLocation));
        String[] nextLine; //an array of values from the line

        while ((nextLine = reader.readNext()) != null) {
            //searches first column of line to see if it contains a subject code
            Matcher matcher = subjectPattern.matcher(nextLine[SUBJECT_INDEX]);

            //if a subject code is found, add a new course and relevant information
            if (matcher.find()) {
                courses.add(new Course());
                currentCourse = courses.size() - 1;
                courses.get(currentCourse).setCourseSubject(nextLine[SUBJECT_INDEX]);

                //if no subject code was found, it is assumed to be a book
                //if the duration of the book is N/A or PURCHASE, add the book
            } else if (nextLine[DURATION_INDEX].equals("   N/A") || nextLine[DURATION_INDEX].equals("   PURCHASE")) {
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
