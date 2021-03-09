package nolofinder;

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

}
