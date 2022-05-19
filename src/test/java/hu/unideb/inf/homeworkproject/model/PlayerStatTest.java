package hu.unideb.inf.homeworkproject.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerStatTest {

    @Test
    void getPlayerName() {
        PlayerStat playerStat = new PlayerStat("Player1", 2);
        assertEquals("Player1", playerStat.getPlayerName());
    }

    @Test
    void getPlayerScore() {
        PlayerStat playerStat = new PlayerStat("Player1", 2);
        assertEquals(2, playerStat.getPlayerScore());
    }
}