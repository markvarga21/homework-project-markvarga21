package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.server.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameLoaderController implements Initializable {
    private GameModel gameModel;

    private Parent root;
    private GameController gameController;

    @FXML
    private ListView<Game> savedGamesList;
    private Game selectedGame;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.savedGamesList.getSelectionModel().selectedItemProperty().addListener((observableValue, game, t1) -> selectedGame = savedGamesList.getSelectionModel().getSelectedItem());
    }

    public void setSavedGamesList(List<Game> games) {
        this.savedGamesList.getItems().addAll(games);
    }

// miert van baja az initializerrel ha itt is van??

    @FXML
    public void loadGame(ActionEvent event) {
        System.out.println("Loading FXML, and initializing gameModel...");
        init();
//
//        // clearing the board before loading
//        this.gameController.clearBoard();

        this.gameController.replaceNodesWithLoaded(this.selectedGame);
        this.gameModel.replaceGameInfosWithLoaded(this.selectedGame);
        // setting back colors
        System.out.println("P1color: " + this.selectedGame.getPlayer1Color());
        System.out.println("P2color: " + this.selectedGame.getPlayer2Color());

        this.gameController.setPlayer1Color(Color.web(this.selectedGame.getPlayer1Color()));
        this.gameController.setPlayer2Color(Color.web(this.selectedGame.getPlayer2Color()));

        System.out.println("Switching back to main scene with the GameState of: ");
        System.out.println(this.selectedGame.getGameBoard());
        switchBack(event);
    }

    private void switchBack(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
        try {
            this.root = fxmlLoader.load();
            this.gameController = fxmlLoader.getController();
            this.gameModel = this.gameController.getGameModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
