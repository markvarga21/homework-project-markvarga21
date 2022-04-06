package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private Parent root;

    @FXML
    private TextField player1NameTextField;
    @FXML
    private TextField player2NameTextField;

    @FXML
    public void startGame(ActionEvent event) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("game-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        this.root = fxmlLoader.load();

        // setting the name in the GameController
        GameController gameController = fxmlLoader.getController();


        gameController.getGameModel().setPlayer1Name(this.player1NameTextField.getText());
        gameController.getGameModel().setPlayer2Name(this.player2NameTextField.getText());

        // and displaying/switching to scene2
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
