package hu.unideb.inf.homeworkproject.model;

/**
 * A class for managing player stats like players name and score.
 */
public class PlayerStat {
    /**
     * The name of the player.
     */
    private String playerName;

    /**
     * The score of the player.
     */
    private int playerScore;

    /**
     * Full-args constructor for {@code PlayerStat} class.
     * @param playerName the name of the player.
     * @param playerScore the score of the player.
     */
    public PlayerStat(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    /**
     * A getter method for the players name.
     * @return the name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * A getter method for the players score.
     * @return the score of the player.
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * An override {@code toString()} method for a more aesthetic
     * {@code String} representation of a {@code PlayerStat}.
     * @return a {@code String} representing a {@code PlayerStat}.
     */
    @Override
    public String toString() {
        return "Player name: " + this.playerName
                + "\nPlayer score: " + this.playerScore;
    }

    /**
     * An override {@code equals()} method for checking custom
     * equality.
     * @param obj the {@code Object} we want to check the equality with.
     * @return {@code true} if they are the same (the names and the scores are equal),
     * otherwise returns {@code false}.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerStat playerStat) {
            return playerStat.getPlayerName().equals(this.playerName)
                    && playerStat.getPlayerScore() == this.playerScore;
        }
        return false;
    }
}
