package stos.projects.scorekeeper.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@EqualsAndHashCode
@Builder
public class FootballMatch {
    private final int id;
    private final ZonedDateTime matchTime;
    private final String arena;
    private final String homeTeam;
    private final String awayTeam;
    private final MatchType matchType;

    @Override
    public String toString() {
        return "MatchId: " + id + "|"
                + "[" + homeTeam + " vs " + awayTeam + "]" + "|"
                + "Date: " + matchTime.format(DateTimeFormatter.RFC_1123_DATE_TIME) + "|"
                + "Arena: " + arena + "|"
                + "Group: " + matchType.toString() + "\n";
    }

    public MatchType getMatchType() {
        return matchType;
    }
}
