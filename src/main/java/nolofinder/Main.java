package nolofinder;

import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
        primaryStage.setTitle("NCC NoLo Finder");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException, CsvValidationException {
        if (args.length != 0) {
            if (args.length == 3 || args.length == 4) {
                Controller controller = new Controller();
                controller.runCommandline(args);
            } else {
                System.out.println("ERROR: Invalid number of parameters");
            }
        } else {
            launch(args);
        }
    }
}

