package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;

import java.util.Arrays;

public class Client {
    private GameModel gameModel;

    public Client(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void saveGame() {
        System.out.println("Saved game: ");
        System.out.println(Arrays.deepToString(this.gameModel.getGameBoardStatus()));
    }
}
