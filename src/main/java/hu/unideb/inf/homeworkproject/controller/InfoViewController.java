package hu.unideb.inf.homeworkproject.controller;

import hu.unideb.inf.homeworkproject.model.PlayerStat;
import hu.unideb.inf.homeworkproject.server.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

/**
 * A class for controlling the info view scene.
 */
public class InfoViewController {
    /**
     * A {@code Text} field where information is shown.
     */
    @FXML
    private Text infoText;

    /**
     * A {@code Logger} for {@code InfoViewController} class.
     */
    final static Logger gameLoaderControllerLogger = LogManager.getLogger();

    /**
     * A {@code Parent} root for the main game.
     */
    private Parent gameRoot;

    /**
     * A field for storing the current game's data.
     * It is refreshed on every menu click.
     */
    private Game gameStatusBeforeMenu;

    /**
     * A method for displaying the Rules inside the {@code infoText} text field.
     * @param game the current {@code Game}.
     */
    public void displayRules(Game game) {
        this.gameStatusBeforeMenu = game;
        gameLoaderControllerLogger.info("Displaying rules.");
        String rulesText = """
                There is 16 stones (circles) on the board.
                The players can alternately remove minimum 1,
                and maximum 4 stones (circles) from the board.
                The removable stones have to be all together
                in the same row/same column
                and there cannot be an empty board cell between them.
                Who makes the last move, loses.
                """;
        this.infoText.setText(rulesText);
    }

    /**
     * A method for displaying information about the game and its creator.
     * @param game the current {@code Game}.
     */
    public void displayAbout(Game game) {
        String aboutText = """
                This is a simple game made in JavaFX,
                where players can play together a game,
                where they remove circles from a board.
                
                Made by: Varga József Márk
                """;
        this.gameStatusBeforeMenu = game; // mivel a backnel egy uj controllert hoz be uj adatokkal, nem a regikkel
        gameLoaderControllerLogger.info("Displaying about.");
        this.infoText.setText(aboutText);
    }

    /**
     * A method for displaying the leaderboard.
     * @param playerStats a {@code List} of {@code PlayerStat}s which
     * represents the names and the scores of the players.
     * @param game the current {@code Game}.
     */
    public void displayLeaderboard(List<PlayerStat> playerStats, Game game) {
        this.gameStatusBeforeMenu = game;
        if (playerStats != null) {
            gameLoaderControllerLogger.info("Displaying leaderboard.");
            StringBuilder leaderBoard = new StringBuilder();
            for (int i = 1; i < playerStats.size()+1; i++) {
                leaderBoard.append(i).append(". ")
                        .append(playerStats.get(i - 1).getPlayerName())
                        .append(" - ")
                        .append(playerStats.get(i - 1).getPlayerScore())
                        .append(" points\n");
            }
            this.infoText.setText(leaderBoard.toString());
        } else {
            this.infoText.setText("Leaderboard is empty!");
            gameLoaderControllerLogger.error("There was an error while loading the leaderboard!");
        }
    }

    /**
     * A method for loading back the main game, and its current game's
     * information, because otherwise it clears the game data and starts
     * a plain new game.
     * @param event representing an {@code ActionEvent}.
     */
    @FXML
    public void backToGame(ActionEvent event) {
        gameLoaderControllerLogger.info("Going back to game...");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
        try {
            this.gameRoot = fxmlLoader.load();

            GameController gameController = fxmlLoader.getController();

            gameController.replaceNodesWithLoaded(this.gameStatusBeforeMenu);
            gameController.getGameModel().replaceGameInfosWithLoaded(this.gameStatusBeforeMenu);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(gameRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
