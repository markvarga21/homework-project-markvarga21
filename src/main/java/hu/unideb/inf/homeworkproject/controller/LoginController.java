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

/**
 * A class which controls the login screen of the
 * application.
 */
public class LoginController implements Initializable {
    /**
     * A {@code Parent} object representing the root.
     */
    private Parent root;

    /**
     * The main {@code GameController} for further invocations.
     */
    private GameController gameController;

    /**
     * Logger for {@code LoginController} class.
     */
    final static Logger loginControllerLogger = LogManager.getLogger();

    /**
     * A {@code TextField} which holds the first players name.
     */
    @FXML
    private TextField player1NameTextField;

    /**
     * A {@code TextField} which holds the second players name.
     */
    @FXML
    private TextField player2NameTextField;

    /**
     * A {@code ColorPicker} which holds the first players {@code Color}.
     */
    @FXML
    private ColorPicker player1ColorPicker;

    /**
     * A {@code ColorPicker} which holds the second players {@code Color}.
     */
    @FXML
    private ColorPicker player2ColorPicker;

    /**
     * This initializes everything before loading the scene.
     * @param url for resolving relative paths for the root object.
     * @param resourceBundle used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
        try {
            this.root = fxmlLoader.load();
            this.gameController = fxmlLoader.getController();
        } catch (IOException e) {
            loginControllerLogger.error(e.getMessage());
        }
    }

    /**
     * Starts the game/application, which means switching to root, which is
     * the main {@code game-view}.
     * @param event representing an {@code ActionEvent}.
     */
    @FXML
    public void startGame(ActionEvent event) {
        if (this.player1NameTextField.getText().isEmpty() && this.player2NameTextField.getText().isEmpty()) {
            this.gameController.getGameModel().feedBackUser("No player names provided!", Alert.AlertType.WARNING);
        } else if (this.player1NameTextField.getText().isEmpty()) {
            this.gameController.getGameModel().feedBackUser("First players name is empty!", Alert.AlertType.WARNING);
        } else if (this.player2NameTextField.getText().isEmpty()) {
            this.gameController.getGameModel().feedBackUser("Second players name is empty!", Alert.AlertType.WARNING);
        } else {
            this.gameController.getGameModel().setPlayer1Name(this.player1NameTextField.getText());
            this.gameController.getGameModel().setPlayer2Name(this.player2NameTextField.getText());
            this.gameController.getGameModel().setPlayer1Color(this.player1ColorPicker.getValue().toString());
            this.gameController.getGameModel().setPlayer2Color(this.player2ColorPicker.getValue().toString());

            loginControllerLogger.info("player1Color: {}", this.gameController.getGameModel().getPlayer1Color());
            loginControllerLogger.info("player2Color: {}", this.gameController.getGameModel().getPlayer2Color());

            this.gameController.getWhosComingLabel().setText("It is " + this.player2NameTextField.getText() + "'s turn now!");

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * A method for setting up, and informing the main game controller about
     * the first players color.
     * @param event representing an {@code ActionEvent}.
     */
    @FXML
    public void setPlayer1Color(ActionEvent event) {
        Color color = this.player1ColorPicker.getValue();
        if (this.gameController.getPlayer2Color().equals(color)) {
            this.gameController.getGameModel().feedBackUser("Player 2 already selected this color.\nPlease select another one!", Alert.AlertType.WARNING);
        } else {
            this.gameController.setPlayer1Color(color);
        }
    }

    /**
     * A method for setting up, and informing the main game controller about
     * the second players color.
     * @param event representing an {@code ActionEvent}.
     */
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
