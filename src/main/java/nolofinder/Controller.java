package nolofinder;

import com.opencsv.exceptions.CsvValidationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

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

        try {
            //checks that threshold is valid
            double noloThreshhold = Double.parseDouble(threshholdTextField.getText());

            if (noloThreshhold > 0) {
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
                model.importBookstoreData();
                model.importRoomlistData();
                model.claculateNolo(noloThreshhold);
                model.outputFile();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Threshold must be more than zero");
                alert.showAndWait();
            }
            //if a NumberFormatException is given, user did not give a number
        } catch (NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Threshold must be a number");
            alert.showAndWait();
        }

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
    
    //arguments: noloThreshhold, bookstore csv, optional roomlist csv, output file
    void runCommandline(String[] args) throws IOException, CsvValidationException{
        if(Files.exists(Paths.get(args[1]))){
            model.setCommandLine(true);
            model.setBookstoreFileLocation(args[1]);

            //four arguments means the roomlist is included
            if(args.length == 4){
                if(Files.exists(Paths.get(args[2]))){
                    model.setRoomlistFileLocation(args[2]);
                    model.setSaveFile(new File(args[3]));
                } else {
                    System.out.println("ERROR: Roomlist file does not exist");
                }
                //if it's not four arguments, assume it's 3 with the roomlist excluded
            } else {
                model.setSaveFile(new File(args[2]));
            }

            try {
                //checks that threshold is valid
                double noloThreshhold = Double.parseDouble(args[0]);

                if(noloThreshhold > 0){
                    //calls converting methods
                    model.importBookstoreData();
                    model.importRoomlistData();
                    model.claculateNolo(noloThreshhold);
                    model.outputFile();
                }else{
                    System.out.println("ERROR: Threshold must be more than zero");
                }
            } catch (NumberFormatException ex) {
                System.out.println("ERROR: Threshhold must be a number");
            }
        } else {
            System.out.println("ERROR: Bookstore file does not exist");
        }

    }

}
