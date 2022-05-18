package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.PlayerStat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.time.Instant;
import java.util.List;

/**
 * A class, which manipulates on data along out application, ie querying,
 * increasing players scores, saving games.
 */
public class Server {
    /**
     * A {@code Jdbi} object which creates and handles
     * database connection.
     */
    private final Jdbi jdbi;

    /**
     * A {@code Handle} object which handles data manipulation
     * in the application.
     */
    private Handle handle;

    /**
     * A {@code Logger} for the {@code Server} class.
     */
   final static Logger serverLogger = LogManager.getLogger();

    /**
     * No-args constructor which creates and instantiates the
     * {@code Jdbi} connection with the database.
     */
    public Server() {
        this.jdbi  = Jdbi.create("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11493376", "sql11493376", "qvMw8ZhPl4");
    }

    /**
     * Increases the score of the given player.
     * @param playerName the {@code name} of the player
     * for whom we want to increase the score.
     */
    public void increaseScore(String playerName) {
        this.handle = this.jdbi.open();
        if (isNewPlayer(playerName)) {
            this.handle
                    .createUpdate("INSERT INTO leaderboard (player_name, player_score) VALUES (:name, 1)")
                    .bind("name", playerName)
                    .execute();
            serverLogger.debug("New player added: " + playerName);
        } else {
            var optPrevScore = this.handle
                    .createQuery("SELECT player_score FROM leaderboard WHERE player_name = :name")
                    .bind("name", playerName)
                    .mapTo(Integer.class)
                    .findOne();
            int prevScore = optPrevScore.isEmpty() ? -1 : optPrevScore.get();
            if (prevScore == -1) serverLogger.error("The player " + playerName + " does not exist!");
            else {
                this.handle.createUpdate("UPDATE leaderboard SET player_score = :newScore WHERE player_name = :name")
                        .bind("newScore", prevScore + 1)
                        .bind("name", playerName)
                        .execute();
                serverLogger.debug("Increased the point of " + playerName);
            }
        }
        this.handle.close();
    }

    /**
     * Checks whether a given player exists or not in
     * the database.
     * @param playerName the {@code name} of the player we want to verify.
     * @return {@code true} if the player had already existed in the
     * database, else returns {@code false}.
     */
    private boolean isNewPlayer(final String playerName) {
        this.handle = this.jdbi.open();
        return this.handle
                .createQuery("SELECT * FROM leaderboard WHERE player_name = :name")
                .bind("name", playerName).mapTo(String.class).list().isEmpty();
    }

    /**
     * Pushes/adds a {@code gameState} to the database.
     * @param game the {@code Game} we want to save.
     */
    public void addSavedGame(final Game game) {
        this.handle = this.jdbi.open();
        final Instant dateWhenPlayed = game.getDateWhenPlayed();
        final String player1Name = game.getPlayer1Name();
        final String player1Color = game.getPlayer1Color();
        final String player2Name = game.getPlayer2Name();
        final String player2Color = game.getPlayer2Color();
        final String gameBoard = game.getGameBoard();
        final int whoWasGoingNext = game.getWhoWasGoingNext();

        this.handle.createUpdate("INSERT INTO savedgames (date, player1Name, player1Color, player2Name, player2Color, gameBoardState, whoWasComingNext) " +
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
        this.handle.close();
    }

    /**
     * Queries and converts the saved games in the database to a
     * more readable/processable format, to a {@code List}.
     * @return a {@code List} of {@code Game}s representing
     * the saved games in the database.
     */
    public List<Game> querySavedGames() {
        this.handle = this.jdbi.open();
        var savedGames = this.handle.createQuery("SELECT * FROM savedgames")
                .map((rs, ctx) -> new Game(
                        rs.getString("player1Name"),
                        rs.getString("player1Color"),
                        rs.getString("player2Name"),
                        rs.getString("player2Color"),
                        rs.getString("gameBoardState"),
                        rs.getInt("whoWasComingNext")))
                .list();
        this.handle.close();
        return savedGames;
    }

    public List<PlayerStat> queryLeaderboard() {
        serverLogger.info("Querying leadboard...");
        return null;
    }
}
