package nolofinder;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {

    Model model = new Model();

    @FXML
    private TextField bookstoreTextField;

    @FXML
    private TextField roomlistTextField;

    @FXML
    void importBookstore() {
        model.setBookstoreFile(importFile());

        if (model.getBookstoreFile() != null) {
            bookstoreTextField.setText(model.getBookstoreFile().getAbsolutePath());
        }

    }

    @FXML
    void importRoomlist() {
        model.setRoomlistFile(importFile());

        if (model.getRoomlistFile() != null) {
            roomlistTextField.setText(model.getRoomlistFile().getAbsolutePath());
        }

    }

    @FXML
    void clear() {
        model.setBookstoreFile(null);
        model.setRoomlistFile(null);

        bookstoreTextField.setText("");
        roomlistTextField.setText("");
    }

    File importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Spreadsheet");

        return fileChooser.showOpenDialog(new Stage());
    }

}
