package nolofinder;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {

    Model model = new Model();

    @FXML
    private TextField bookstoreTextField;

    @FXML
    private TextField roomlistTextField;

    @FXML
    private TextField threshholdTextField;

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

    @FXML
    void processFiles() throws IOException, CsvValidationException {
        double noloThreshhold = Double.parseDouble(threshholdTextField.getText());
        model.importBookstoreData();
        model.importRoomlistData();
        model.claculateNolo(noloThreshhold);
    }

    String importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Spreadsheet");

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return "";
        }

    }

}
