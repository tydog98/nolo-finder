package nolofinder;

import java.io.File;
import java.util.ArrayList;

public class Model {

    private File bookstoreFile;
    private File roomlistFile;
    private ArrayList<Course> courses = new ArrayList<>();

    void setBookstoreFile(File newFile) {
        bookstoreFile = newFile;
    }

    void setRoomlistFile(File newFile) {
        roomlistFile = newFile;
    }

    File getBookstoreFile() {
        return bookstoreFile;
    }

    File getRoomlistFile() {
        return roomlistFile;
    }

}
