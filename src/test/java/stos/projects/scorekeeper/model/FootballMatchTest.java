package stos.projects.scorekeeper.model;

import org.junit.Before;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FootballMatchTest {
    private static final int MATCH_ID_1 = 1;
    private static final String HOME_TEAM = "Brazil";
    private static final String AWAY_TEAM = "France";
    private static final ZonedDateTime now = ZonedDateTime.now();
    private static final String DATE_STRING = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
    private static final String ARENA_NAME_RIO = "Rio";
    private static final String GAME_INFO = "Group A";
    private static final String EXPECTED_TO_STRING_FOR_GROUP_GAME = "MatchId: " + MATCH_ID_1 + "|"
            + "[" +HOME_TEAM + " vs " + AWAY_TEAM + "]" + "|"
            + "Date: " + DATE_STRING + "|"
            + "Arena: "+ ARENA_NAME_RIO + "|"
            + "Group: " + GAME_INFO + "\n";

    private FootballMatch match;

    @Before
    public void setUp() {
        match = FootballMatch.builder().id(1)
                .matchTime(now)
                .homeTeam(HOME_TEAM).awayTeam(AWAY_TEAM)
                .matchType(MatchType.A)
                .arena(ARENA_NAME_RIO).build();
    }


    @Test
    public void to_string_expected() {
        assertThat(match.toString(), is(EXPECTED_TO_STRING_FOR_GROUP_GAME));
    }

    @Test
    public void match_has_MatchType() {
        assertThat(match.getMatchType(), is(MatchType.A));
    }
}