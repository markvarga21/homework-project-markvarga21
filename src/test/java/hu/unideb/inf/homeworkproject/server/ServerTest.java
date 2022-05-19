package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.PlayerStat;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    private Server server;
    private Jdbi jdbi;

    @BeforeEach
    void initServerConnection() {
        this.server = new Server();
        this.jdbi = Jdbi.create("jdbc:mysql://sql11.freesqldatabase.com:3306/sql11493376", "sql11493376", "qvMw8ZhPl4");
    }

    @Test
    void increaseScore() {
        try (var handle = this.jdbi.open()) {
            handle.createUpdate("INSERT INTO leaderboard (player_name, player_score) VALUES (:pName, :score)")
                    .bind("pName", "DUMMY_NAME")
                    .bind("score", 999)
                    .execute();
            this.server.increaseScore("DUMMY_NAME");
            var DUMMY_SCORE = handle
                    .createQuery("SELECT player_score FROM leaderboard WHERE player_name = :name")
                    .bind("name", "DUMMY_NAME")
                    .mapTo(Integer.class)
                    .findOne()
                    .get();
            assertEquals(1000, DUMMY_SCORE);
            handle.createUpdate("DELETE FROM leaderboard WHERE player_name=:pName")
                    .bind("pName", "DUMMY_NAME")
                    .execute();
        }
    }

    @Test
    void addSavedGame() {
        try (var handle = this.jdbi.open()) {
            Game expected = new Game(Instant.now(), "DUMMY_1", "D_COLOR1", "DUMMY_2", "D_COLOR2", "1111111111111111", -1);
            this.server.addSavedGame(expected);
            var actual = handle.createQuery("SELECT * FROM savedgames WHERE player1Name = :p1Name AND player2Name = :p2Name")
                    .bind("p1Name", "DUMMY_1")
                    .bind("p2Name", "DUMMY_2")
                    .map((rs, ctx) -> new Game(
                            rs.getString("player1Name"),
                            rs.getString("player1Color"),
                            rs.getString("player2Name"),
                            rs.getString("player2Color"),
                            rs.getString("gameBoardState"),
                            rs.getInt("whoWasComingNext")))
                    .findOne()
                    .get();
            System.out.println("ACTUAL: " + actual);
            System.out.println("EXPECTED: " + expected);

            assertEquals(expected, actual);

            handle.createUpdate("DELETE FROM savedgames WHERE player1Name = :p1Name AND player2Name = :p2Name")
                    .bind("p1Name", "DUMMY_1")
                    .bind("p2Name", "DUMMY_2")
                    .execute();
        }
    }

    @Test
    void querySavedGames() {
        try (var handle = this.jdbi.open()) {
            var expected = handle.createQuery("SELECT * FROM savedgames")
                    .map((rs, ctx) -> new Game(
                            rs.getString("player1Name"),
                            rs.getString("player1Color"),
                            rs.getString("player2Name"),
                            rs.getString("player2Color"),
                            rs.getString("gameBoardState"),
                            rs.getInt("whoWasComingNext")))
                    .list();
            handle.close();
            assertEquals(expected, this.server.querySavedGames());
        }
    }

    @Test
    void queryLeaderboard() {
        try (var handle = this.jdbi.open()) {
            var expected = handle.createQuery("SELECT * FROM leaderboard ORDER BY player_score DESC")
                    .map((rs, ctx) -> new PlayerStat(
                            rs.getString("player_name"),
                            rs.getInt("player_score")))
                    .list();
            handle.close();
            assertArrayEquals(expected.toArray(), this.server.queryLeaderboard().toArray());
        }
    }
}