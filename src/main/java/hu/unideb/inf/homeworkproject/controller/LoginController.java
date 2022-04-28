package hu.unideb.inf.homeworkproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Parent root;
    private GameController gameController;

    final static Logger loginControllerLogger = LogManager.getLogger();

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
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
        this.gameController.getGameModel().setPlayer1Color(this.player1ColorPicker.getValue().toString());
        this.gameController.getGameModel().setPlayer2Color(this.player2ColorPicker.getValue().toString());

        loginControllerLogger.info("player1Color: {}", this.gameController.getGameModel().getPlayer1Color());
        loginControllerLogger.info("player2Color: {}", this.gameController.getGameModel().getPlayer2Color());

        // and displaying/switching to scene2
        // extracting the source of the node (which is casted), and than casting to stange, and after this
        // getting window.
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setPlayer1Color(ActionEvent event) {
        Color color = this.player1ColorPicker.getValue();
        if (this.gameController.getPlayer2Color().equals(color)) {
            this.gameController.getGameModel().feedBackUser("Player 2 already selected this color.\nPlease select another one!", Alert.AlertType.WARNING);
        } else {
            this.gameController.setPlayer1Color(color);
        }
    }

    @FXML
    public void setPlayer2Color(ActionEvent event) {
        Color color = this.player2ColorPicker.getValue();
        if (this.gameController.getPlayer1Color().equals(color)) {
            this.gameController.getGameModel().feedBackUser("Player 1 already selected this color.\nPlease select another one!", Alert.AlertType.WARNING);
        } else {
            this.gameController.setPlayer2Color(color);
        }
    }

}
