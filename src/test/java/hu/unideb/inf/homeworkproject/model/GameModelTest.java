package hu.unideb.inf.homeworkproject.model;

import javafx.scene.shape.Circle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    private static final Logger mainLogger = LogManager.getLogger();

    @BeforeAll
    static void testObjectConstruction() {
        GameModel gameModel = new GameModel();
        assertNotNull(gameModel.getRemovableNodes());
        assertEquals(0, gameModel.getClickedCirclesCount());
        assertNotNull(gameModel.getGameBoardStatus());
        assertNotNull(gameModel.getPrevNodes());
        assertNotNull(gameModel.getClient());
        assertEquals("", gameModel.getPlayer1Name());
        assertEquals("", gameModel.getPlayer2Name());
    }

    @Test
    void setStatus() {
        GameModel gameModel = new GameModel();
        assertTrue(gameModel.setStatus(2, 2, 1));
        assertFalse(gameModel.setStatus(5, 2, 1));
        assertFalse(gameModel.setStatus(2, 5, 1));
        assertFalse(gameModel.setStatus(2, 2, 5));
    }

    @Test
    void clearDeletions() {
        GameModel gameModel = new GameModel();

        gameModel.setClickedCirclesCount(5);

        ArrayList<CircleNode> nodes = new ArrayList<>();
        nodes.add(new CircleNode(null, 2, 2));
        gameModel.setRemovableNodes(nodes);

        gameModel.setPrevNodes(nodes);

        gameModel.clearDeletions();

        assertEquals(0, gameModel.getClickedCirclesCount());
        assertEquals(0, gameModel.getRemovableNodes().size());
        assertEquals(0, gameModel.getPrevNodes().size());
    }

    @Test
    void switchPlayer() {
        GameModel gameModel = new GameModel();
        gameModel.setWhosComingNext(-1);
        gameModel.switchPlayer();
        assertEquals(1, gameModel.getWhosComingNext());
    }

    @Test
    void addRemovableNode() {
        GameModel gameModel = new GameModel();
        Circle circle = new Circle();
        CircleNode node = new CircleNode(circle, 2, 2);
        gameModel.addRemovableNode(node);

        // checking if it is true
        assertTrue(gameModel.getRemovableNodes().contains(node));
        assertEquals(1, gameModel.getClickedCirclesCount());

        // checking the false branch
        gameModel.addRemovableNode(node);
        assertFalse(gameModel.getRemovableNodes().contains(node));
        assertEquals(0, gameModel.getClickedCirclesCount());
    }

    @Test
    void addPrevNode() {
        GameModel gameModel = new GameModel();
        Circle circle = new Circle();
        CircleNode node = new CircleNode(circle, 2, 2);

        gameModel.addPrevNode(node);

        assertTrue(gameModel.getPrevNodes().contains(node));
    }

    @Test
    void clearPrev() {
        GameModel gameModel = new GameModel();
        Circle circle = new Circle();
        CircleNode node = new CircleNode(circle, 2, 2);

        gameModel.addPrevNode(node);

        gameModel.clearPrev();

        assertFalse(gameModel.getPrevNodes().contains(node));
    }

    // azok am eotdusok amik hivnak mas metodusokat, eleg osszessegeben egyszer tesztelni egy helyen, vagy minden hivasi helyen kell?
    // pl GameModel.save()

    @Test
    void startNewGame() {
        final int gameBoardSize = 4;
        GameModel gameModel = new GameModel();
        Circle circle = new Circle();
        CircleNode node = new CircleNode(circle, 2, 2);

        gameModel.addRemovableNode(node);
        gameModel.addPrevNode(node);
        gameModel.setClickedCirclesCount(1);
        gameModel.setStatus(2, 2, 1);

        gameModel.startNewGame();

        assertEquals(0, gameModel.getClickedCirclesCount());
        assertEquals(0, gameModel.getPrevNodes().size());
        assertEquals(0, gameModel.getRemovableNodes().size());

        int[][] plainBoard = new int[gameBoardSize][gameBoardSize];
        for (int i = 0; i < gameBoardSize; i++) {
            for (int j = 0; j < gameBoardSize; j++) {
                plainBoard[i][j] = 1;
            }
        }

        assertArrayEquals(plainBoard, gameModel.getGameBoardStatus());
    }

    @Test
    void setPlayer1Name() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayer1Name("John");
        assertEquals("John", gameModel.getPlayer1Name());
    }

    @Test
    void setPlayer2Name() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayer2Name("John");
        assertEquals("John", gameModel.getPlayer2Name());
    }

    @Test
    void setPrevNodes() {
        GameModel gameModel = new GameModel();
        ArrayList<CircleNode> nodes = new ArrayList<>();
        Circle circle = new Circle();
        CircleNode circleNode = new CircleNode(circle, 2, 2);
        nodes.add(circleNode);

        gameModel.setPrevNodes(nodes);

        assertEquals(nodes, gameModel.getPrevNodes());
    }

    @Test
    void getPlayer1Name() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayer1Name("John");
        assertEquals("John", gameModel.getPlayer1Name());
    }

    @Test
    void getPlayer2Name() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayer2Name("John");
        assertEquals("John", gameModel.getPlayer2Name());
    }

    @Test
    void getWinner() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayer1Name("John");
        gameModel.setPlayer2Name("Jenny");

        gameModel.setWhosComingNext(-1);
        assertEquals("John", gameModel.getWinner());


        gameModel.setWhosComingNext(1);
        assertEquals("Jenny", gameModel.getWinner());


        gameModel.setWhosComingNext(5);
        assertEquals("NoOne", gameModel.getWinner());
    }

    @Test
    void getGameBoardStatus() {
        GameModel gameModel = new GameModel();
        int[][] status = new int[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        status[0][0] = 1;
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                gameModel.setStatus(i, j, 0);
            }
        }
        gameModel.setStatus(0, 0, 1);

        assertArrayEquals(status, gameModel.getGameBoardStatus());
    }

    @Test
    void getRemovableNodes() {
        GameModel gameModel = new GameModel();
        Circle circle = new Circle();
        CircleNode circleNode = new CircleNode(circle, 2, 2);
        ArrayList<CircleNode> expected = new ArrayList<>();
        expected.add(circleNode);

        gameModel.addRemovableNode(circleNode);

        assertEquals(expected, gameModel.getRemovableNodes());

    }

    @Test
    void getWhosComingNext() {
        GameModel gameModel = new GameModel();
        gameModel.setWhosComingNext(-1);
        assertEquals(-1, gameModel.getWhosComingNext());
    }

    @Test
    void getPlayerForIndex() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayer1Name("John");
        gameModel.setPlayer2Name("Jenny");

        assertEquals("John", gameModel.getPlayerForIndex(1));
        assertEquals("Jenny", gameModel.getPlayerForIndex(-1));
        assertEquals("No player", gameModel.getPlayerForIndex(5));

    }

    @Test
    void getClient() {
        GameModel gameModel = new GameModel();
        assertNotNull(gameModel.getClient());
    }

    @Test
    void getClickedCirclesCount() {
        GameModel gameModel = new GameModel();
        gameModel.setClickedCirclesCount(2);
        assertEquals(2, gameModel.getClickedCirclesCount());
    }

    @Test
    void getPrevNodes() {
        GameModel gameModel = new GameModel();
        Circle circle = new Circle();
        CircleNode circleNode = new CircleNode(circle, 2, 2);
        ArrayList<CircleNode> expected = new ArrayList<>();
        expected.add(circleNode);
        gameModel.addPrevNode(circleNode);
        assertEquals(expected, gameModel.getPrevNodes());
    }
}