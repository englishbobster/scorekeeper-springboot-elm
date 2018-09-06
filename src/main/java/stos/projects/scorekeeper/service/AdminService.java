package stos.projects.scorekeeper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stos.projects.scorekeeper.controller.TransportObjects.ScoreTO;
import stos.projects.scorekeeper.model.FinalScore;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.tournamentdata.repositories.MatchFinalScoreRepository;
import stos.projects.scorekeeper.tournamentdata.repositories.TournamentMatchRepository;

import java.util.List;

@Service
public class AdminService {

    TournamentMatchRepository matchRepository;
    MatchFinalScoreRepository scoreRepository;

    @Autowired
    public AdminService(TournamentMatchRepository matchRepository, MatchFinalScoreRepository scoreRepository) {
        this.matchRepository = matchRepository;
        this.scoreRepository = scoreRepository;
    }

    public List<FootballMatch> getPlannedMatches() {
        return matchRepository.findAll();
    }

    public boolean finalScoreIsSet(int matchid) {
        return scoreRepository.existsById(matchid);
    }

    public boolean matchExists(int matchid) {
        return matchRepository.existsById(matchid);
    }

    public FinalScore setFinalScore(int matchId, ScoreTO score)  {
        FinalScore finalScore = new FinalScore(matchId, score.getHomeScore(), score.getAwayScore());
        scoreRepository.saveAndFlush(finalScore);
        return finalScore;
    }
}
