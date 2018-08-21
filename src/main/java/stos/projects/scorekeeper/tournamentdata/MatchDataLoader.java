package stos.projects.scorekeeper.tournamentdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stos.projects.scorekeeper.tournamentdata.tournamentmatchrepo.TournamentMatchRepository;

@Component
public class MatchDataLoader {

    private TournamentMatchRepository matchRepository;

    @Autowired
    public MatchDataLoader(TournamentMatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

}
