package stos.projects.scorekeeper.tournamentdata.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import stos.projects.scorekeeper.model.FinalScore;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.model.MatchType;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class TournamentMatchRepositoryTest {

    public static final Integer MATCH_ID = 1;
    private static final ZonedDateTime now = ZonedDateTime.now();
    private static final String HOME_TEAM = "Brazil";
    private static final String AWAY_TEAM = "France";
    private static final String ARENA_NAME_RIO = "Rio";

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TournamentMatchRepository matchRepo;
    @Autowired
    MatchFinalScoreRepository scoreRepo;

    @Test
    public void should_get_back_score_when_retrieving_a_match_when_score_is_set() {
        FinalScore finalScore = new FinalScore(MATCH_ID, 4, 3);
        FootballMatch match_withFinalScore = FootballMatch.builder().id(MATCH_ID)
                .matchTime(now)
                .homeTeam(HOME_TEAM).awayTeam(AWAY_TEAM)
                .matchType(MatchType.FINAL)
                .arena(ARENA_NAME_RIO)
                .finalScore(finalScore)
                .build();

        testEntityManager.persist(match_withFinalScore);
        testEntityManager.flush();

        Optional<FinalScore> score = scoreRepo.findById(MATCH_ID);

        assertThat(score.get(), is(equalTo(finalScore)));
    }


    @Test
    public void should_get_back_score_when_retrieving_a_match_when_score_is_not_set() {
        FinalScore finalScore = new FinalScore(MATCH_ID, 4, 3);
        FootballMatch match_withFinalScore = FootballMatch.builder().id(MATCH_ID)
                .matchTime(now)
                .homeTeam(HOME_TEAM).awayTeam(AWAY_TEAM)
                .matchType(MatchType.FINAL)
                .arena(ARENA_NAME_RIO)
                .build();

        testEntityManager.persist(match_withFinalScore);
        testEntityManager.flush();

        Optional<FinalScore> score = scoreRepo.findById(MATCH_ID);

        assertThat(score.get(), is(equalTo(finalScore)));
    }

}