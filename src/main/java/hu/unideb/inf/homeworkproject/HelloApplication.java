package hu.unideb.inf.homeworkproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        Image icon = new Image(String.valueOf(getClass().getResource("/images/game-logo.png")));

        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Stone game");
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}