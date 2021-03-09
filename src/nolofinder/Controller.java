package nolofinder;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Controller {

    Model model = new Model();

    @FXML
    private TextField bookstoreTextField;

    @FXML
    private TextField roomlistTextField;

    @FXML
    void importBookstore() {
        model.setBookstoreFileLocation(importFile());

        if (model.getBookstoreFile() != null) {
            bookstoreTextField.setText(model.getBookstoreFile());
        }

    }

    @FXML
    void importRoomlist() {
        model.setRoomlistFileLocation(importFile());

        if (model.getRoomlistFile() != null) {
            roomlistTextField.setText(model.getRoomlistFile());
        }

    }

    @FXML
    void clear() {
        model.setBookstoreFileLocation("");
        model.setRoomlistFileLocation("");

        bookstoreTextField.setText("");
        roomlistTextField.setText("");
    }

    String importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Spreadsheet");

        return fileChooser.showOpenDialog(new Stage()).getAbsolutePath();
    }

}
