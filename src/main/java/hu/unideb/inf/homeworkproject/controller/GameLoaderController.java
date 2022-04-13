package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.HelloApplication;
import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.server.Client;
import hu.unideb.inf.homeworkproject.server.Game;
import hu.unideb.inf.homeworkproject.server.Server;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
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
        this.savedGamesList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Game>() {
            @Override
            public void changed(ObservableValue<? extends Game> observableValue, Game game, Game t1) {
                selectedGame = savedGamesList.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void setSavedGamesList(List<Game> games) {
        this.savedGamesList.getItems().addAll(games);


    }

// miert van baja az initializerrel ha itt is van??

    @FXML
    public void loadGame(ActionEvent event) {
        System.out.println("Loading FXML, and initializing gameModel...");
        init();

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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("game-view.fxml"));
        try {
            this.root = fxmlLoader.load();
            this.gameController = fxmlLoader.getController();
            this.gameModel = this.gameController.getGameModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
