package hu.unideb.inf.homeworkproject.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final Jdbi jdbi;
    private Handle handle;

    private final Logger serverLogger;

    public Server() {
        this.jdbi  = Jdbi.create("jdbc:mysql://localhost:3306/projectdatabase", "java", "SWEHomeWorkProject");
        this.handle = this.jdbi.open();
        this.serverLogger = LogManager.getLogger();
    }

    public void increaseScore(String playerName) {
//        Handle handle = this.jdbi.open();
        if (isNewPlayer(playerName)) {
            this.handle
                    .createUpdate("INSERT INTO leaderboard (player_name, player_score) VALUES (:name, 1)")
                    .bind("name", playerName)
                    .execute();
            this.serverLogger.debug("New player added: " + playerName);
        } else {
            var optPrevScore = this.handle
                    .createQuery("SELECT player_score FROM leaderboard WHERE player_name = :name")
                    .bind("name", playerName)
                    .mapTo(Integer.class)
                    .findOne();
            int prevScore = optPrevScore.isEmpty() ? -1 : optPrevScore.get();
            if (prevScore == -1) System.out.println("The player " + playerName + " does not exist!");
            else {
                this.handle.createUpdate("UPDATE leaderboard SET player_score = :newScore WHERE player_name = :name")
                        .bind("newScore", prevScore + 1)
                        .bind("name", playerName)
                        .execute();
                this.serverLogger.debug("Increased the point of " + playerName);
            }
        }
    }

    private boolean isNewPlayer(final String playerName) {
        return this.handle
                .createQuery("SELECT * FROM leaderboard WHERE player_name = :name")
                .bind("name", playerName).mapTo(String.class).list().isEmpty();
    }

    public void addSavedGame(final Game game) {
        final Instant dateWhenPlayed = game.getDateWhenPlayed();
        final String player1Name = game.getPlayer1Name();
        final String player2Name = game.getPlayer2Name();
        final String gameBoard = game.getGameBoard();
        final int whoWasGoingNext = game.getWhoWasGoingNext();

        this.handle.createUpdate("INSERT INTO savedgames (date, player1Name, player2Name, gameBoardState, whoWasComingNext) " +
                                    "VALUES (:d, :p1Name, :p2Name, :gbState, :nextComing)")
                .bind("d", dateWhenPlayed)
                .bind("p1Name", player1Name)
                .bind("p2Name", player2Name)
                .bind("gbState", gameBoard)
                .bind("nextComing", whoWasGoingNext)
                .execute();

        // logging to console
        this.serverLogger.trace("Saved game with properties: \n"
                + "Date: " + dateWhenPlayed
                + "\nPlayer 1 name: " + player1Name
                + "\nPlayer 2 name: " + player2Name
                + "\nGame board states: " + gameBoard
                + "\nWho was coming next: " + whoWasGoingNext);
    }

    public List<Game> querySavedGames() {
        // Instant does not work bcs SQL does not support this type of Date ?? alth it prints out in mySQL Workbench when it's queried
        return this.handle.createQuery("SELECT * FROM savedgames")
                .map((rs, ctx) -> new Game(
                        rs.getString("player1Name"),
                        rs.getString("player2Name"),
                        rs.getString("gameBoardState"),
                        rs.getInt("whoWasComingNext")))
                .list();
    }
}
