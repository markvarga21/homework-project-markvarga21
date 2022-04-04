package hu.unideb.inf.homeworkproject.model;

import javafx.scene.Node;

public class CircleNode {
    private Node node;
    private int row;
    private int column;

    public CircleNode(Node node, int row, int column) {
        this.node = node;
        this.row = row;
        this.column = column;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Node getNode() {
        return node;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(Object obj) {
        CircleNode objCircleNode = (CircleNode) obj;
        return getNode().equals(objCircleNode.getNode());
    }
}
