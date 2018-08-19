package stos.projects.scorekeeper.model;

import lombok.Data;

@Data
public class Score {
    private final int homeScore;
    private final int awayScore;

    public Score(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        return "[ " + homeScore + " - " + awayScore + " ]";
    }
}

