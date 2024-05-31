package game;

import java.io.Serializable;

public class GameScore implements Serializable {
    public int totalScore;
    public String name;
    public int timeSeconds;

    public GameScore(
            int totalScore,
            String name,
            int timeSeconds
    ) {
        this.totalScore = totalScore;
        this.name = name;
        this.timeSeconds = timeSeconds;
    };
}
