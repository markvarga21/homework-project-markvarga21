package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;
import javafx.scene.control.Alert;

import java.time.Instant;

public class Client {
    private final GameModel gameModel;
    private final Server server;

    public Client(GameModel gameModel) {
        this.server = new Server();
        this.gameModel = gameModel;
    }

    public void saveGame() {
        System.out.println("Saved game: ");
        String game = "";
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                game += this.gameModel.getGameBoardStatus()[i][j];
        this.server.addSavedGame(new Game(Instant.now(), this.gameModel.getPlayer1Name(), this.gameModel.getPlayer2Name(), game, this.gameModel.getWhosComingNext()));
        this.gameModel.feedBackUser("Game saved successfully!", Alert.AlertType.INFORMATION);
    }

    public void updateLeaderBoard(String playerName) {
        System.out.println("Updating player score in SQL server leaderboard!");
        this.server.increaseScore(playerName);
    }
}
