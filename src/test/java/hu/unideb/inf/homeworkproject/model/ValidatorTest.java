package hu.unideb.inf.homeworkproject.model;

import javafx.scene.shape.Circle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @BeforeAll
    static void checkObjectCreation() {
        Validator validator = new Validator(new GameModel());
        assertNotNull(validator);
        // checking logger too when it is working correctly
    }

    @Test
    void isValidSelection() {
        GameModel gameModel = new GameModel();
        // checking first if for true case
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        Validator validator = new Validator(gameModel);

        // same row
        assertTrue(validator.isValidSelection(new CircleNode(new Circle(), 2, 3)));
        // same column
        assertTrue(validator.isValidSelection(new CircleNode(new Circle(), 1, 2)));
        // check for false
        assertFalse(validator.isValidSelection(new CircleNode(new Circle(), 3, 3)));

        // clearing removableNodes
        gameModel.setRemovableNodes(new ArrayList<>());
        // column check for node no. > 1
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 1, 2));
        assertTrue(validator.isValidSelection(new CircleNode(new Circle(), 0, 2)));
        // if selection is invalid when checking columns
        assertFalse(validator.isValidSelection(new CircleNode(new Circle(), 0, 0)));

        // clearing removableNodes
        gameModel.setRemovableNodes(new ArrayList<>());
        // setting this to 0, because clearing the nodes does not clear the no. of clicked circles
        gameModel.setClickedCirclesCount(0);

        // row check for node no. > 1
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 1));
        assertTrue(validator.isValidSelection(new CircleNode(new Circle(), 2, 3)));
        // if selection is invalid when checking rows
        assertFalse(validator.isValidSelection(new CircleNode(new Circle(), 0, 0)));

        // checking if clicked circle count is <= the GameModel.MAX_NUMBER_OF_CIRCLES_TO_CLICK
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 0));
        assertFalse(validator.isValidSelection(new CircleNode(new Circle(), 0, 0)));


    }

    @Test
    void testIsValidSelection() {
        GameModel gameModel = new GameModel();
        Validator validator = new Validator(gameModel);

        // checking default, NOT_CALIBRATED Orientation branch
        assertTrue(validator.isValidSelection());

        // checking for false
        // row check
        gameModel.addRemovableNode(new CircleNode(new Circle(), 0, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 0, 0));
        assertFalse(validator.isValidSelection());

        // clearing removableNodes
        gameModel.setRemovableNodes(new ArrayList<>());
        // setting this to 0, because clearing the nodes does not clear the no. of clicked circles
        gameModel.setClickedCirclesCount(0);

        // column check
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 0, 2));
        assertFalse(validator.isValidSelection());

        // checking for true
        // clearing removableNodes
        gameModel.setRemovableNodes(new ArrayList<>());
        // setting this to 0, because clearing the nodes does not clear the no. of clicked circles
        gameModel.setClickedCirclesCount(0);

        // row
        // row check
        gameModel.addRemovableNode(new CircleNode(new Circle(), 0, 1));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 0, 0));
        assertTrue(validator.isValidSelection());

        // clearing removableNodes
        gameModel.setRemovableNodes(new ArrayList<>());
        // setting this to 0, because clearing the nodes does not clear the no. of clicked circles
        gameModel.setClickedCirclesCount(0);

        // column check
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 1, 2));
        assertTrue(validator.isValidSelection());

    }

    @Test
    void getOrientation() {
        GameModel gameModel = new GameModel();
        Validator validator = new Validator(gameModel);
        // row
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 1));
        assertEquals(Orientation.HORIZONTAL, validator.getOrientation());

        // clearing
        gameModel.setRemovableNodes(new ArrayList<>());
        gameModel.setClickedCirclesCount(0);

        // column
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        gameModel.addRemovableNode(new CircleNode(new Circle(), 1, 2));
        assertEquals(Orientation.VERTICAL, validator.getOrientation());

        // clearing
        gameModel.setRemovableNodes(new ArrayList<>());
        gameModel.setClickedCirclesCount(0);

        assertEquals(Orientation.NOT_CALIBRATED, validator.getOrientation());
    }

    @Test
    void checkRowInterference() {
        GameModel gameModel = new GameModel();
        Validator validator = new Validator(gameModel);
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        assertTrue(validator.checkRowInterference(2));
        assertFalse(validator.checkRowInterference(1));
    }

    @Test
    void checkColumnInterference() {
        GameModel gameModel = new GameModel();
        Validator validator = new Validator(gameModel);
        gameModel.addRemovableNode(new CircleNode(new Circle(), 2, 2));
        assertTrue(validator.checkColumnInterference(2));
        assertFalse(validator.checkColumnInterference(1));
    }

    @Test
    void checkWinner() {
        GameModel gameModel = new GameModel();
        Validator validator = new Validator(gameModel);
        int[][] boardToCheck = new int[GameModel.GAME_BOARD_SIZE][GameModel.GAME_BOARD_SIZE];
        boardToCheck[0][0] = 1;
        gameModel.setGameBoardStatus(boardToCheck);
        assertFalse(validator.checkWinner());
        boardToCheck[0][0] = 0;
        gameModel.setGameBoardStatus(boardToCheck);
        assertTrue(validator.checkWinner());
    }
}