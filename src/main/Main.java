package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * Ein Programm um Munitions Preise von selbst hergestellter Munition zu Berechnen.
 *
 * @author Thomas Saner
 * @version 1.0
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Munitions Kalkulator");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

//TODO Javadoc nötig?
    public static void main(String[] args) {
        launch(args);
    }
}
