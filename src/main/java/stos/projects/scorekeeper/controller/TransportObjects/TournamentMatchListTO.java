package stos.projects.scorekeeper.controller.TransportObjects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;
import stos.projects.scorekeeper.model.FootballMatch;

import java.util.List;
import java.util.stream.Collectors;

@JsonSerialize
@Value
public class TournamentMatchListTO {
    List<FootballMatchTO> tournamentMatches;

    public static TournamentMatchListTO tournamentMatches(List<FootballMatch> matches) {
        List<FootballMatchTO> matchListTO = matches.stream().map(match -> FootballMatchTO.from(match)).collect(Collectors.toList());
        return new TournamentMatchListTO(matchListTO);
    }

}
