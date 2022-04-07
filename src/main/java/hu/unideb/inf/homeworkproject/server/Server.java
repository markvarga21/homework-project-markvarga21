package hu.unideb.inf.homeworkproject.server;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

public class Server {
    private final Jdbi jdbi;
    private Handle handle;

    public Server() {
        this.jdbi  = Jdbi.create("jdbc:mysql://localhost:3306/projectdatabase", "java", "SWEHomeWorkProject");
        this.handle = this.jdbi.open();
    }

    public void increaseScore(String playerName) {
//        Handle handle = this.jdbi.open();
        if (isNewPlayer(playerName)) {
            this.handle
                    .createUpdate("INSERT INTO leaderboard (player_name, player_score) VALUES (:name, 1)")
                    .bind("name", playerName)
                    .execute();
            System.out.println("New player added: " + playerName);
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
                System.out.println("Increased the point of " + playerName);
            }
        }
    }

    private boolean isNewPlayer(final String playerName) {
        return this.handle
                .createQuery("SELECT * FROM leaderboard WHERE player_name = :name")
                .bind("name", playerName).mapTo(String.class).list().isEmpty();
    }
}
