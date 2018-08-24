package stos.projects.scorekeeper.controller.TransportObjects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;

@JsonSerialize
@Value
public class FootballMatchTO {
    private int matchid;
    private String matchTime;
    private String arena;
    private String homeTeam;
    private String awayTeam;
    private String group;
}
