package nolofinder;

import java.io.File;

public class Model {

    private File bookstoreFile;
    private File roomlistFile;

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
