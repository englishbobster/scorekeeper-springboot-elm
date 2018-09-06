package stos.projects.scorekeeper.model;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FootballMatchTest {
    private static final int MATCH_ID_1 = 1;
    private static final int MATCH_ID_2 = 2;
    private static final String HOME_TEAM = "Brazil";
    private static final String AWAY_TEAM = "France";
    private static final ZonedDateTime now = ZonedDateTime.now();
    private static final String DATE_STRING = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
    private static final String ARENA_NAME_RIO = "Rio";
    private static final String GAME_INFO = "Group A";

    private FootballMatch match_withoutFinalScore;
    private FootballMatch match_withFinalScore;
    private FinalScore finalScore;

    @Before
    public void setUp() {
        match_withoutFinalScore = FootballMatch.builder().id(MATCH_ID_1)
                .matchTime(now)
                .homeTeam(HOME_TEAM).awayTeam(AWAY_TEAM)
                .matchType(MatchType.A)
                .arena(ARENA_NAME_RIO)
                .build();

        finalScore = new FinalScore(MATCH_ID_2, 4, 3);
        match_withFinalScore = FootballMatch.builder().id(MATCH_ID_2)
                .matchTime(now)
                .homeTeam(HOME_TEAM).awayTeam(AWAY_TEAM)
                .matchType(MatchType.FINAL)
                .arena(ARENA_NAME_RIO)
                .finalScore(finalScore)
                .build();
    }

    @Test
    public void value_of_final_score_is_null() {
        assertThat(match_withoutFinalScore.getFinalScore(), Matchers.nullValue());
    }

    @Test
    public void value_of_final_score_is_equal() {
        assertThat(match_withFinalScore.getFinalScore(), is(equalTo(finalScore)));
    }

    @Test
    public void match_has_MatchType() {
        assertThat(match_withoutFinalScore.getMatchType(), is(MatchType.A));
    }
}