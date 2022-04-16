package hu.unideb.inf.homeworkproject.model;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CircleNodeTest {

    @BeforeAll
    static void checkObjectCreation() {
        CircleNode circleNode = new CircleNode(new Circle(), 2, 2);
        assertNotNull(circleNode);
    }

    @Test
    void getNode() {
        Circle circle = new Circle();
        CircleNode circleNode = new CircleNode(circle, 2, 2);
        assertEquals(circle, circleNode.getNode());
    }

    @Test
    void getRow() {
        CircleNode circleNode = new CircleNode(new Circle(), 2, 2);
        assertEquals(2, circleNode.getRow());
    }

    @Test
    void getColumn() {
        CircleNode circleNode = new CircleNode(new Circle(), 2, 2);
        assertEquals(2, circleNode.getColumn());
    }

    @Test
    void testEquals() {
        Circle circle = new Circle();
        CircleNode circleNode1 = new CircleNode(circle, 2, 2);
        CircleNode circleNode2 = new CircleNode(circle, 2, 2);
        assertEquals(circleNode1, circleNode2);
        CircleNode rectNode = new CircleNode(new Rectangle(), 2, 2);
        assertNotEquals(rectNode, circleNode1);
    }

    @Test
    void testHashCode() {
        Circle circle = new Circle();
        CircleNode circleNode1 = new CircleNode(circle, 2, 2);
        CircleNode circleNode2 = new CircleNode(circle, 2, 2);
        assertEquals(circleNode1.hashCode(), circleNode2.hashCode());

    }
}