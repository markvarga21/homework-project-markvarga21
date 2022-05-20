package hu.unideb.inf.homeworkproject.server;

import hu.unideb.inf.homeworkproject.model.GameModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    void initGame() {
        this.game = new Game(Instant.now(), "Player1", "col1", "Player2", "col2", "1111111111111111", -1);
    }

    @Test
    void getPlayer1Name() {
        assertEquals("Player1", this.game.getPlayer1Name());
    }

    @Test
    void getPlayer2Name() {
        assertEquals("Player2", this.game.getPlayer2Name());
    }

    @Test
    void getGameBoard() {
        assertEquals("1111111111111111", this.game.getGameBoard());
    }

    @Test
    void getWhoWasGoingNext() {
        assertEquals(-1, this.game.getWhoWasGoingNext());
    }

    @Test
    void getPlayer1Color() {
        assertEquals("col1", this.game.getPlayer1Color());
    }

    @Test
    void getPlayer2Color() {
        assertEquals("col2", this.game.getPlayer2Color());
    }

    @Test
    void toStateArray() {
        int[][] temp = new int[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                temp[i][j] = 1;
            }
        }
        assertArrayEquals(temp, this.game.toStateArray());
    }

    @Test
    void testToString() {
        StringBuilder gameMap = new StringBuilder();
        char square = 11036;
        char circle = 11044;
        String gameBoard = "1111111111111111";
        String copyGameBoard = gameBoard.replace('1', circle).replace('0', square);
        gameMap.append(copyGameBoard, 0, 4).append("\n")
                .append(copyGameBoard, 4, 8).append("\n")
                .append(copyGameBoard, 8, 12).append("\n")
                .append(copyGameBoard, 12, 16).append("\n");
        String expected = "Player 1 name: Player1 with the color col1\n" +
                            "Player 2 name: Player2 with the color col2\n"+
                            "GameBoard: \n" + gameMap;
        assertEquals(expected, this.game.toString());
    }
}