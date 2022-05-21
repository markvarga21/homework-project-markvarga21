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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A controller class for the loading scene.
 */
public class GameLoaderController implements Initializable {
    /**
     * The main {@code GameModel}.
     */
    private GameModel gameModel;

    /**
     * A {@code Parent} object representing the root.
     */
    private Parent root;

    /**
     * The main {@code GameController} for further invocations.
     */
    private GameController gameController;

    /**
     * A {@code ListView} which contains {@code Game}s.
     */
    @FXML
    private ListView<Game> savedGamesList;

    /**
     * A {@code Game} container which stores the currently
     * clicked {@code Game} in the {@code ListView}.
     */
    private Game selectedGame;

    /**
     * Logger for {@code GameLoaderController} class.
     */
    final static Logger gameLoaderControllerLogger = LogManager.getLogger();

    /**
     * A {@code Game} representing the current game.
     */
    private Game currentGame;

    /**
     * This initializes everything before loading the scene. Here is a
     * property and a listener too, so we don't have to verify
     * and assign it every time.
     * @param url for resolving relative paths for the root object.
     * @param resourceBundle used to localize the root object.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.savedGamesList.getSelectionModel().selectedItemProperty().addListener((observableValue, game, t1) -> selectedGame = savedGamesList.getSelectionModel().getSelectedItem());
    }

    /**
     * Sets the {@code ListView} items to the specified list passed
     * in the arguments.
     * @param games the {@code Game}s we want to display.
     */
    public void setSavedGamesList(List<Game> games) {
        this.savedGamesList.getItems().addAll(games);
    }

    /**
     * A method which is used to load a game.
     * @param event representing an {@code ActionEvent}.
     */
    @FXML
    public void loadGame(ActionEvent event) {
        gameLoaderControllerLogger.debug("Loading FXML, and initializing gameModel...");
        init();

        this.gameController.replaceNodesWithLoaded(this.selectedGame);
        this.gameModel.replaceGameInfosWithLoaded(this.selectedGame);

        gameLoaderControllerLogger.info("P1color: {}", this.selectedGame.getPlayer1Color());
        gameLoaderControllerLogger.info("P2color: {}", this.selectedGame.getPlayer2Color());

        this.gameController.setPlayer1Color(Color.web(this.selectedGame.getPlayer1Color()));
        this.gameController.setPlayer2Color(Color.web(this.selectedGame.getPlayer2Color()));

        gameLoaderControllerLogger.debug("Switching back to main scene with the GameState of: {}", this.selectedGame.getGameBoard());
        switchBack(event);
    }

    /**
     * Used by {@code GameLoaderController} class for switching scenes.
     * @param event representing an {@code ActionEvent}.
     */
    private void switchBack(ActionEvent event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the current game to a {@code Game} passed inside the parameters.
     * @param game the {@code Game} we want to set to.
     */
    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

    /**
     * A method for loading back the game-view, and also reloading the
     * current game infos, because otherwise it clears all the infos
     * and starts a new plain game.
     * @param event representing an {@code ActionEvent}.
     */
    @FXML
    public void backToGameView(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
        try {
            this.root = fxmlLoader.load();

            GameController gameController = fxmlLoader.getController();
            gameController.replaceNodesWithLoaded(this.currentGame);
            gameController.getGameModel().replaceGameInfosWithLoaded(this.currentGame);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            gameLoaderControllerLogger.error(e.getMessage());
        }
    }

    /**
     * Initialization for the game-view scene.
     */
    private void init() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
        try {
            this.root = fxmlLoader.load();
            this.gameController = fxmlLoader.getController();
            this.gameModel = this.gameController.getGameModel();
        } catch (IOException e) {
            gameLoaderControllerLogger.error(e.getMessage());
        }
    }
}
