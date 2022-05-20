package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.PlayerStat;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Scanner;

/**
 * A class, which manipulates on data along out application, ie querying,
 * increasing players scores, saving games.
 */
public class Server {
    /**
     * A {@code Jdbi} object which creates and handles
     * database connection.
     */
    private Jdbi jdbi;

    /**
     * A {@code Logger} for the {@code Server} class.
     */
   final static Logger serverLogger = LogManager.getLogger();

    /**
     * Timeout limit for server connection.
     */
   private final int timeoutLimit = 50000;

    /**
     * No-args constructor which creates and instantiates the
     * {@code Jdbi} connection with the database.
     */
    public Server() {
        try (var connection = DriverManager.getConnection("jdbc:mysql://azure-swe-server.mysql.database.azure.com:3306/swe", "markvarga21", "SWEProject_2145")) {
            if (connection.isValid(this.timeoutLimit)) {
                this.jdbi = Jdbi.create("jdbc:mysql://azure-swe-server.mysql.database.azure.com:3306/swe", "markvarga21", "SWEProject_2145");
                serverLogger.info("Successfully connected to database!");
            } else {
                serverLogger.error("Connection timed out!");
                connection.close();
            }
        } catch (SQLException e) {
            serverLogger.error("Exception thrown: {}", e.getMessage());
        } finally {
            serverLogger.error("Something went wrong when connecting to the database, loading from config file backup!");
            URL fileURL = getClass().getResource("/server/backup-config.txt");
            try {
                File configBackup = new File(fileURL.toURI());
                try (var scanner = new Scanner(configBackup)) {
                    String unprocessedContent = scanner.nextLine();

                    if (!unprocessedContent.equals("noItem")) {
                        String[] components = unprocessedContent.split(" ");
                        String url = components[0];
                        String user = components[1];
                        String password = components[2];
                        this.jdbi = Jdbi.create(url, user, password);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(null);
                        alert.setHeaderText(null);
                        alert.setContentText("Backup server configuration not found, please set it up inside the DEV menu!");
                        alert.showAndWait();
                    }
                } catch (FileNotFoundException e) {
                    serverLogger.error(e.getMessage());
                }
            } catch (URISyntaxException e) {
                serverLogger.error(e.getMessage());
            }
        }
    }

    /**
     * Increases the score of the given player.
     * @param playerName the {@code name} of the player
     * for whom we want to increase the score.
     */
    public void increaseScore(String playerName) {
        try (var handle = jdbi.open()) {
            if (isNewPlayer(playerName)) {
                handle
                        .createUpdate("INSERT INTO leaderboard (player_name, player_score) VALUES (:name, 1)")
                        .bind("name", playerName)
                        .execute();
                serverLogger.debug("New player added: " + playerName);
            } else {
                var optPrevScore = handle
                        .createQuery("SELECT player_score FROM leaderboard WHERE player_name = :name")
                        .bind("name", playerName)
                        .mapTo(Integer.class)
                        .findOne();
                int prevScore = optPrevScore.isEmpty() ? -1 : optPrevScore.get();
                if (prevScore == -1) serverLogger.error("The player " + playerName + " does not exist!");
                else {
                    handle.createUpdate("UPDATE leaderboard SET player_score = :newScore WHERE player_name = :name")
                            .bind("newScore", prevScore + 1)
                            .bind("name", playerName)
                            .execute();
                    serverLogger.debug("Increased the point of " + playerName);
                }
            }
        }
    }

    /**
     * Checks whether a given player exists or not in
     * the database.
     * @param playerName the {@code name} of the player we want to verify.
     * @return {@code true} if the player had already existed in the
     * database, else returns {@code false}.
     */
    private boolean isNewPlayer(final String playerName) {
        try (var handle = this.jdbi.open()) {
            return handle
                    .createQuery("SELECT * FROM leaderboard WHERE player_name = :name")
                    .bind("name", playerName).mapTo(String.class).list().isEmpty();
        }
    }

    /**
     * Pushes/adds a {@code gameState} to the database.
     * @param game the {@code Game} we want to save.
     */
    public void addSavedGame(final Game game) {
        try (var handle = this.jdbi.open()) {
            final Instant dateWhenPlayed = game.getDateWhenPlayed();
            final String player1Name = game.getPlayer1Name();
            final String player1Color = game.getPlayer1Color();
            final String player2Name = game.getPlayer2Name();
            final String player2Color = game.getPlayer2Color();
            final String gameBoard = game.getGameBoard();
            final int whoWasGoingNext = game.getWhoWasGoingNext();

            handle.createUpdate("INSERT INTO savedgames (date, player1Name, player1Color, player2Name, player2Color, gameBoardState, whoWasComingNext) " +
                            "VALUES (:d, :p1Name, :p1Color, :p2Name, :p2Color, :gbState, :nextComing)")
                    .bind("d", dateWhenPlayed)
                    .bind("p1Name", player1Name)
                    .bind("p1Color", player1Color)
                    .bind("p2Name", player2Name)
                    .bind("p2Color", player2Color)
                    .bind("gbState", gameBoard)
                    .bind("nextComing", whoWasGoingNext)
                    .execute();

            serverLogger.trace("Saved game with properties: \n Date: {} \n Player 1 name: {} \n Player 2 name: {} \n Game board states: {} \n Who was coming next: {}",
                    dateWhenPlayed, player1Name,player2Name, gameBoard, whoWasGoingNext);
        }
    }

    /**
     * Queries and converts the saved games in the database to a
     * more readable/processable format, to a {@code List}.
     * @return a {@code List} of {@code Game}s representing
     * the saved games in the database.
     */
    public List<Game> querySavedGames() {
        try (var handle = this.jdbi.open()) {
            return handle.createQuery("SELECT * FROM savedgames")
                    .map((rs, ctx) -> new Game(
                            rs.getString("player1Name"),
                            rs.getString("player1Color"),
                            rs.getString("player2Name"),
                            rs.getString("player2Color"),
                            rs.getString("gameBoardState"),
                            rs.getInt("whoWasComingNext")))
                    .list();
        }
    }

    /**
     * A method for querying the {@code Leaderboard} from the server, and
     * converting it to a {@code List}.
     * @return a {@code List} of {@code PlayerStat}s, containing the
     * players names and scores.
     */
    public List<PlayerStat> queryLeaderboard() {
        try (var handle = this.jdbi.open()) {
            serverLogger.info("Querying leadboard...");
            return handle.createQuery("SELECT * FROM leaderboard ORDER BY player_score DESC")
                    .map((rs, ctx) -> new PlayerStat(
                            rs.getString("player_name"),
                            rs.getInt("player_score")))
                    .list();
        }
    }
}
