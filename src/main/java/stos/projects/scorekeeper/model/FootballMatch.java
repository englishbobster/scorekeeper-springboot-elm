package stos.projects.scorekeeper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="planned_matches")
public class FootballMatch {
    @Id
    private Integer id;
    @Column(name = "match_time")
    private ZonedDateTime matchTime;
    @Column
    private String arena;
    @Column(name = "home_team")
    private String homeTeam;
    @Column(name = "away_team")
    private String awayTeam;
    @Column(name = "match_type")
    private MatchType matchType;

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
