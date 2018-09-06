package stos.projects.scorekeeper.controller.TransportObjects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@JsonDeserialize
@RequiredArgsConstructor
@Data
public class ScoreTO {
    private final int homeScore;
    private final int awayScore;
}
