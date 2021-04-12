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
        //opens save dialogue
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setInitialFileName("converted.csv");
        
         fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV File", "*.csv"),
                new FileChooser.ExtensionFilter("All File", "*"));

        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            model.setSaveFile(selectedFile);
        }
        
        //calls converting methods
        double noloThreshhold = Double.parseDouble(threshholdTextField.getText());
        model.importBookstoreData();
        model.importRoomlistData();
        model.claculateNolo(noloThreshhold);
        model.outputFile();
    }

    String importFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Spreadsheet");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"),
                new FileChooser.ExtensionFilter("All Files", "*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return "";
        }

    }

}
