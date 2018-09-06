package stos.projects.scorekeeper.controller.TransportObjects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;
import stos.projects.scorekeeper.model.FootballMatch;

import java.time.format.DateTimeFormatter;

@JsonSerialize
@Value
public class FootballMatchTO {
    private int matchid;
    private String matchTime;
    private String arena;
    private String homeTeam;
    private String awayTeam;
    private String group;

    public static FootballMatchTO from(FootballMatch match) {
        return new FootballMatchTO(match.getId(),
                match.getMatchTime().format(DateTimeFormatter.RFC_1123_DATE_TIME),
                match.getArena(), match.getHomeTeam(), match.getAwayTeam(), match.getMatchType().toString());
    }

}
