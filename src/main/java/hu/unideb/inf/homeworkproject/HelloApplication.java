package hu.unideb.inf.homeworkproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
//        System.out.println(getClass().getResource("/fxmls/login-view.fxml").getPath());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        String cssLink = Objects.requireNonNull(this.getClass().getResource("style.css")).toExternalForm();
//        scene.getStylesheets().add(cssLink);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}