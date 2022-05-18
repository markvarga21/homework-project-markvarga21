package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;
import hu.unideb.inf.homeworkproject.model.PlayerStat;
import javafx.scene.control.Alert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.Instant;
import java.util.List;

/**
 * A class which is used to invoke and handle {@code Server}
 * methods.
 */
public class Client {
    /**
     * The main {@code GameModel}.
     */
    private GameModel gameModel;

    /**
     * The {@code Server} component.
     */
    private final Server server;

    /**
     * Logger for {@code Client} class.
     */
    final static Logger clientLogger = LogManager.getLogger();

    /**
     * One-arg constructor for {@code Client} class.
     * @param gameModel the {@code GameModel} of the
     * application/game.
     */
    public Client(GameModel gameModel) {
        this.server = new Server();
        this.gameModel = gameModel;
    }

    /**
     * A no-arg constructor for the {@code Client} class.
     */
    public Client() {
        this.server = new Server();
    }

    /**
     * Builds up a {@code String} of {@code gameBoard} state,
     * and invokes the {@code Server} method on it.
     */
    public void saveGame() {
        clientLogger.info("Game is saved successfully!");
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

    /**
     * Updates the leaderboard.
     * @param playerName the name of the player for who
     * we want to increase the score.
     */
    public void updateLeaderBoard(String playerName) {
        clientLogger.debug("Updating player score in SQL server leaderboard!");
        this.server.increaseScore(playerName);
    }

    public List<PlayerStat> returnLeaderboard() { return this.server.queryLeaderboard(); }

    /**
     * A method which returns the saved games using {@code Server}
     * utilities.
     * @return a {@code List} of {@code Game}s.
     */
    public List<Game> returnSavedGames() {
        return this.server.querySavedGames();
    }
}
