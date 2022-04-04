package hu.unideb.inf.homeworkproject.model;

import java.util.stream.IntStream;

public class Validator {
    private final GameModel gameModel;
    public Validator(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public boolean isValidSelection(CircleNode node) {
        final int rowToCheck = node.getRow();
        final int columnToCheck = node.getColumn();

        if (this.gameModel.getRemovableNodes().size() == 1) {
            return this.gameModel.getRemovableNodes().get(0).getColumn() == columnToCheck ||
                    this.gameModel.getRemovableNodes().get(0).getRow() == rowToCheck;
        } else return switch (getOrientation()) {
            case VERTICAL ->
                    this.gameModel.getClickedCirclesCount() <= 3
                            && checkColumnInterference(columnToCheck);
            case HORIZONTAL ->
                    this.gameModel.getClickedCirclesCount() <= 3
                            && checkRowInterference(rowToCheck);
            case NOT_CALIBRATED -> true;
        };
    }

    public boolean isValidSelection(int rowToCheck, int columnToCheck) {
        if (this.gameModel.getRemovableNodes().size() == 1) {
            return this.gameModel.getRemovableNodes().get(0).getColumn() == columnToCheck ||
                    this.gameModel.getRemovableNodes().get(0).getRow() == rowToCheck;
        } else return switch (getOrientation()) {
            case VERTICAL ->
                    this.gameModel.getClickedCirclesCount() <= 3
                            && checkColumnInterference(columnToCheck);
            case HORIZONTAL ->
                    this.gameModel.getClickedCirclesCount() <= 3
                            && checkRowInterference(rowToCheck);
            case NOT_CALIBRATED -> true;
        };
    }

    /**
     * Mivel a jatekos nem feltetlen sorrend szerint valaszt, ezert csak a vegeredmenyt ellenorizhetjuk le, hogy
     * ne legyen hezag a korok kozott.
     * @return true if ...
     */
    public boolean isValidSelection() {
        return switch (getOrientation()) {
            case VERTICAL -> {
                int maxRow = this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getRow).max().getAsInt();
                int minRow = this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getRow).min().getAsInt();

                int wannaBeSum = IntStream.range(minRow, maxRow).sum() + maxRow; // bcs the second one is exclusive

                int testSum = this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getRow).sum();

                System.out.println("minRow: " + minRow + ", maxRow: " + maxRow + ", wannaBeSum: " + wannaBeSum + ", testSum: " + testSum);
                System.out.println("Rows: ");
                this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getRow).forEach(System.out::println);

                yield testSum == wannaBeSum;
            }
            case HORIZONTAL -> {
                int maxColumn = this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getColumn).max().getAsInt();
                int minColumn = this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getColumn).min().getAsInt();

                int wannaBeSum = IntStream.range(minColumn, maxColumn).sum() + maxColumn;

                int testSum = this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getColumn).sum();

                System.out.println("Columns: ");
                this.gameModel.getRemovableNodes().stream().mapToInt(CircleNode::getColumn).forEach(System.out::println);

                System.out.println("minColumn: " + minColumn + ", maxColumn: " + maxColumn + ", wannaBeSum: " + wannaBeSum + ", testSum: " + testSum);
                yield testSum == wannaBeSum;
            }
            case NOT_CALIBRATED -> true;
        };
    }

    private Orientation getOrientation() {
        if (this.gameModel.getRemovableNodes().size() >= 2) { // not working with this
            return this.gameModel.getRemovableNodes().get(0).getColumn() == this.gameModel.getRemovableNodes().get(1).getColumn() ?
                    Orientation.VERTICAL : Orientation.HORIZONTAL;
        } else return Orientation.NOT_CALIBRATED;
    }

    private boolean checkRowInterference(int row) {
        final int mainRow = this.gameModel.getRemovableNodes().get(0).getRow();
        System.out.println("Main row: " + mainRow);
        return row == mainRow;
    }

    private boolean checkColumnInterference(int column) {
        final int mainColumn = this.gameModel.getRemovableNodes().get(0).getColumn();
        System.out.println("Main col: " + mainColumn);
        return column == mainColumn;
    }

    public boolean checkWinner() {
//        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                int state = this.gameModel.getGameBoardStatus()[i][j];
//                if (state == 1 && isValidSelection(i, j)) return true;
//            }
//        }
//        return false;
        return false;
    }

}
