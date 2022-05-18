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

public class InfoViewController {
    @FXML
    private Text infoText;

    final static Logger gameLoaderControllerLogger = LogManager.getLogger();

    private Parent gameRoot;

    private Game gameStatusBeforeMenu;

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

    @FXML
    public void backToGame(ActionEvent event) {
        gameLoaderControllerLogger.info("Going back to game...");

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/game-view.fxml"));
        try {
            this.gameRoot = fxmlLoader.load();

            GameController gameController = fxmlLoader.getController();
            // maskepp kitoroli es ujat kezd
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
