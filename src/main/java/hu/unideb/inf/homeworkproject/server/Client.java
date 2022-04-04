package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;

public class Client {
    private GameModel gameModel;

    public Client(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void saveGame() {
        System.out.println("Saved game: ");
        String game = "";
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                game += this.gameModel.getGameBoardStatus()[i][j];
        System.out.println(game);
    }
}
