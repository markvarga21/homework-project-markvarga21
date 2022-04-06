package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Parent root;
    private GameController gameController;

    @FXML
    private TextField player1NameTextField;
    @FXML
    private TextField player2NameTextField;

    @FXML
    private ColorPicker player1ColorPicker;
    @FXML
    private ColorPicker player2ColorPicker;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        try {
            this.root = fxmlLoader.load();
            this.gameController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void startGame(ActionEvent event) {
        this.gameController.getGameModel().setPlayer1Name(this.player1NameTextField.getText());
        this.gameController.getGameModel().setPlayer2Name(this.player2NameTextField.getText());

        // and displaying/switching to scene2
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setPlayer1Color(ActionEvent event) {
        Color color = this.player1ColorPicker.getValue();
        if (this.gameController.getPlayer2Color().equals(color)) {
            this.gameController.getGameModel().alert("Player 2 already selected this color.\nPlease select another one!");
        } else {
            this.gameController.setPlayer1Color(color);
        }
    }

    @FXML
    public void setPlayer2Color(ActionEvent event) {
        Color color = this.player2ColorPicker.getValue();
        if (this.gameController.getPlayer1Color().equals(color)) {
            this.gameController.getGameModel().alert("Player 1 already selected this color.\nPlease select another one!");
        } else {
            this.gameController.setPlayer2Color(color);
        }
    }

}
