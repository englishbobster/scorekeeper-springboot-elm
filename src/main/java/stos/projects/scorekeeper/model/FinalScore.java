package stos.projects.scorekeeper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="full_time_scores")
public class FinalScore {
    @Id
    private Integer id;
    @Column(name = "home_score")
    private int homeScore;
    @Column(name = "away_score")
    private int awayScore;

    @Override
    public String toString() {
        return "[ " + homeScore + " - " + awayScore + " ]";
    }
}

