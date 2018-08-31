package stos.projects.scorekeeper.tournamentdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stos.projects.scorekeeper.model.FinalScore;

public interface MatchFinalScoreRepository extends JpaRepository<FinalScore, Integer> {
}
