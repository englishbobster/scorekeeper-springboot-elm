package stos.projects.scorekeeper.controller.TransportObjects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;
import stos.projects.scorekeeper.model.FinalScore;

@JsonSerialize
@Value
public class FinalScoreTO {
    private final int matchId;
    private final int homeScore;
    private final int awayScore;

    public static FinalScoreTO from(FinalScore score) {
        return new FinalScoreTO(score.getId(), score.getHomeScore(), score.getAwayScore());
    }
}
