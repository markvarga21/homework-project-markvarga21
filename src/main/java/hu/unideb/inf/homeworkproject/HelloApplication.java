package hu.unideb.inf.homeworkproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class for starting the application.
 */
public class HelloApplication extends Application {

    /**
     * Loads the FXMLs and starts the application.
     * @param stage the first {@code Scene} we want to display.
     * @throws IOException if something goes wrong when the
     * method is invoked.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launching the application.
     * @param args optional command-line arguments.
     */
    public static void main(String[] args) {
        launch();
    }
}