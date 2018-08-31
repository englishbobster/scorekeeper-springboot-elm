package stos.projects.scorekeeper.tournamentdata.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import stos.projects.scorekeeper.model.FootballMatch;

public interface TournamentMatchRepository extends JpaRepository<FootballMatch, Integer> {
}
