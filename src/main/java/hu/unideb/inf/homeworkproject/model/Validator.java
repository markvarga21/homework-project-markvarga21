package hu.unideb.inf.homeworkproject.model;

import java.util.stream.IntStream;

/**
 * A class which is used to validate certain situations,
 * which will up come during playing the game.
 */
public class Validator {
    /**
     * A reference to the main {@code GameModel}, to use its functionalities.
     */
    private final GameModel gameModel;

    /**
     * Constructs the {@code Validator} using a {@code GameModel} reference
     * as its parameter.
     * @param constrGameModel representing the {@code GameModel}
     * which stores the data.
     */
    public Validator(final GameModel constrGameModel) {
        this.gameModel = constrGameModel;
    }

    /**
     * It checks if a {@code CircleNode} selection is correct or not.
     * Firstly, it handles the case when there is only one {@code CircleNode}
     * is selected, where it verifies it belongs to the main column/row.
     * Secondly, if there are multiple {@code CircleNode}s selected, it verifies,
     * so they are in the same column/row.
     * @param node representing the {@code CircleNode} we want to verify.
     * @return {@code true} if the selection if correct,
     * else returns {@code false}.
     */
    @SuppressWarnings("CheckStyle")
    public boolean isValidSelection(final CircleNode node) {
        final int rowToCheck = node.getRow();
        final int columnToCheck = node.getColumn();

        if (this.gameModel.getRemovableNodes().size() == 1) {
            return this.gameModel.getRemovableNodes().get(0).getColumn() == columnToCheck
                    || this.gameModel.getRemovableNodes().get(0).getRow() == rowToCheck;
        }
        return switch (getOrientation()) {
            case VERTICAL -> this.gameModel.getClickedCirclesCount()
                    <= GameModel.MAX_NUMBER_OF_CIRCLES_TO_CLICK
                    && checkColumnInterference(columnToCheck);
            case HORIZONTAL -> this.gameModel.getClickedCirclesCount()
                    <= GameModel.MAX_NUMBER_OF_CIRCLES_TO_CLICK
                    && checkRowInterference(rowToCheck);
            case NOT_CALIBRATED -> true;
        };
    }

    /**
     * It checks, whether the selected {@code CircleNode} selection
     * is valid or not. It is valid, if there is no gap between the
     * selected {@code CircleNode}s. It calculates the sum of the
     * columns/rows of {@code removableNodes}, and if it is equal
     * with the sum of the lowest/leftmost- and the uppest/rightmost
     * columns/rows, the selection is valid, so it returns {@code true}.
     * @return {@code true} it the selection is valid.
     */
    public boolean isValidSelection() {
        return switch (getOrientation()) {
            case VERTICAL -> {
                int maxRow = this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getRow)
                        .max()
                        .getAsInt();

                int minRow = this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getRow)
                        .min()
                        .getAsInt();

                final int wannaBeSum = IntStream.range(minRow, maxRow)
                        .sum() + maxRow; // bcs the second one is exclusive

                final int testSum = this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getRow)
                        .sum();

                // printing out values
                System.out.println("minRow: " + minRow
                                + ", maxRow: " + maxRow
                                + ", wannaBeSum: " + wannaBeSum
                                + ", testSum: " + testSum);

                System.out.println("Rows: ");
                this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getRow)
                        .forEach(System.out::println);

                yield testSum == wannaBeSum;
            }
            case HORIZONTAL -> {
                int maxColumn = this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getColumn)
                        .max()
                        .getAsInt();

                int minColumn = this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getColumn)
                        .min()
                        .getAsInt();

                int wannaBeSum = IntStream.range(minColumn, maxColumn)
                        .sum() + maxColumn;

                int testSum = this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getColumn)
                        .sum();

                System.out.println("Columns: ");
                this.gameModel.getRemovableNodes().stream()
                        .mapToInt(CircleNode::getColumn)
                        .forEach(System.out::println);

                System.out.println("minColumn: " + minColumn
                                + ", maxColumn: " + maxColumn
                                + ", wannaBeSum: " + wannaBeSum
                                + ", testSum: " + testSum);
                yield testSum == wannaBeSum;
            }
            case NOT_CALIBRATED -> true;
        };
    }

    /**
     * It returns the {@code CircleNode}s orientation, which can be either
     * {@code HORIZONTAL}, {@code VERTICAL} or {@code NOT_CALIBRATED}.
     * @return the orientation of the circles.
     */
    private Orientation getOrientation() {
        if (this.gameModel.getRemovableNodes().size() >= 2) {
            return this.gameModel.getRemovableNodes().get(0).getColumn()
                    == this.gameModel.getRemovableNodes().get(1).getColumn()
                    ? Orientation.VERTICAL : Orientation.HORIZONTAL;
        }
        return Orientation.NOT_CALIBRATED;
    }

    /**
     * Checks if the interested {@code row} is equal
     * with the main row. So that if fits the games rules.
     * @param row representing the row which has to be checked.
     * @return {@code true}, if it is in the main row,
     * else returns {@code false}.
     */
    private boolean checkRowInterference(final int row) {
        final int mainRow = this.gameModel.getRemovableNodes().get(0).getRow();
        System.out.println("Main row: " + mainRow);
        return row == mainRow;
    }

    /**
     * Checks if the interested {@code column} is equal
     * with the main column. So that if fits the games rules.
     * @param column representing the column which has to be checked.
     * @return {@code true}, if it is in the main column,
     * else returns {@code false}.
     */
    @SuppressWarnings("CheckStyle")
    private boolean checkColumnInterference(final int column) {
        final int mainColumn = this.gameModel.getRemovableNodes().get(0).getColumn();
        System.out.println("Main col: " + mainColumn);
        return column == mainColumn;
    }

    /**
     * It checks whether there is a winner or no.
     * If the {@code gameBoard} contains a {@code CircleNode},
     * it means, that there is a possible move, so there is no winner yet.
     * Although, if the board is empty
     * the {@code player} won the game!
     * @return {@code true} if there is no possible move left.
     */
    public boolean checkWinner() {
        for (int i = 0; i < GameModel.GAME_BOARD_SIZE; i++) {
            for (int j = 0; j < GameModel.GAME_BOARD_SIZE; j++) {
                if (this.gameModel.getGameBoardStatus()[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

}
