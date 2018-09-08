package stos.projects.scorekeeper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.time.ZonedDateTime;

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
    @Column(name = "full_time")
    private boolean fullTime;
    @OneToOne(fetch = FetchType.EAGER, cascade= CascadeType.ALL, optional = true)
    @PrimaryKeyJoinColumn(name="match_id")
    private FinalScore finalScore;

    public MatchType getMatchType() {
        return matchType;
    }
}
