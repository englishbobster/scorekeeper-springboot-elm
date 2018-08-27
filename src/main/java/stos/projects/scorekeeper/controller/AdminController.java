package stos.projects.scorekeeper.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import stos.projects.scorekeeper.controller.TransportObjects.FootballMatchTO;
import stos.projects.scorekeeper.controller.TransportObjects.TournamentMatchListTO;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.tournamentdata.tournamentmatchrepo.TournamentMatchRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("admin/")
public class AdminController {

    TournamentMatchRepository matchRepository;

    public AdminController(TournamentMatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "planned-matches")
    ResponseEntity<TournamentMatchListTO> getAllPlannedTournamentMatches() {
        List<FootballMatch> plannedMatches = matchRepository.findAll();
        TournamentMatchListTO matchListTO = tournamentMatches(plannedMatches);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(matchListTO);
    }

    private FootballMatchTO from(FootballMatch match) {
        return new FootballMatchTO(match.getId(),
                match.getMatchTime().format(DateTimeFormatter.RFC_1123_DATE_TIME),
                match.getArena(), match.getHomeTeam(), match.getAwayTeam(), match.getMatchType().toString());
    }

    private TournamentMatchListTO tournamentMatches(List<FootballMatch> matches) {
        List<FootballMatchTO> matchListTO = matches.stream().map(match -> from(match)).collect(Collectors.toList());
        return new TournamentMatchListTO(matchListTO);
    }
}
