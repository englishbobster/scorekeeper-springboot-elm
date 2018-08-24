package stos.projects.scorekeeper.controller.TransportObjects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;

import java.util.List;

@JsonSerialize
@Value
public class TournamentMatchListTO {
    List<FootballMatchTO> tournamentMatches;
}
