package hu.unideb.inf.homeworkproject.model;

public class PlayerStat {
    private String playerName;
    private int playerScore;

    public PlayerStat(String playerName, int playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public String toString() {
        return "Player name: " + this.playerName
                + "\nPlayer score: " + this.playerScore;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PlayerStat playerStat) {
            return playerStat.getPlayerName().equals(this.playerName)
                    && playerStat.getPlayerScore() == this.playerScore;
        }
        return false;
    }
}
