package hu.unideb.inf.homeworkproject.model;

import javafx.scene.Node;

/**
 * A class representing a {@code Node}, which also stores the indexes, because by default we cannot extract them directly
 * from a {@code Node}.
 */
public class CircleNode {
    private final Node node;
    private final int row;
    private final int column;

    /**
     * CircleNode constructor, accepting three parameters.
     * @param node the plain {@code Node} to which we want to add the indexes.
     * @param row the row of the {@code Node}
     * @param column the column of the {@code Node}
     */
    public CircleNode(Node node, int row, int column) {
        this.node = node;
        this.row = row;
        this.column = column;
    }

    /**
     * @return the {@code Node} field of a {@code CircleClass} instance.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return the row field of a {@code CircleClass} instance.
     */
    public int getRow() {
        return row;
    }

    /**
     * @return the column field of a {@code CircleClass} instance.
     */
    public int getColumn() {
        return column;
    }

    /**
     * It checks whether to {@code CircleNode} are equal, using their {@code Node} fields.
     * @param obj an object representing another {@code Node}.
     * @return {@code true} if their {@code Node} fields are equals, else returns {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        CircleNode objCircleNode = (CircleNode) obj;
        return getNode().equals(objCircleNode.getNode());
    }
}
