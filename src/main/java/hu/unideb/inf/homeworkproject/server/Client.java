package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;
import javafx.scene.control.Alert;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private GameModel gameModel;
    private final Server server;

//    private final Logger clientLogger;

    public Client(GameModel gameModel) {
        this.server = new Server();
        this.gameModel = gameModel;
//        this.clientLogger = LogManager.getLogger();
    }

    public Client() {
        this.server = new Server();
//        this.clientLogger = LogManager.getLogger();

    }

    public void saveGame() {
//        this.clientLogger.info("Game is saved!");
        String game = "";
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                game += this.gameModel.getGameBoardStatus()[i][j];
        this.server.addSavedGame(new Game(Instant.now(),
                                        this.gameModel.getPlayer1Name(),
                                        this.gameModel.getPlayer1Color(),
                                        this.gameModel.getPlayer2Name(),
                                        this.gameModel.getPlayer2Color(),
                                        game,
                                        this.gameModel.getWhosComingNext()));
        this.gameModel.feedBackUser("Game saved successfully!", Alert.AlertType.INFORMATION);
    }

    public void updateLeaderBoard(String playerName) {
//        this.clientLogger.debug("Updating player score in SQL server leaderboard!");
        this.server.increaseScore(playerName);
    }

    public List<Game> returnSavedGames() {
        return this.server.querySavedGames();
    }
}
