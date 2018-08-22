package stos.projects.scorekeeper.tournamentdata;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.tournamentdata.tournamentmatchrepo.TournamentMatchRepository;
import stos.projects.scorekeeper.tournamentdata.tournamentmatchrepo.file.TournamentMatchDataFileReader;

import java.util.List;

@Component
@ConfigurationProperties(prefix="tournament.planned-matches")
@Slf4j
public class MatchDataLoader {

    private TournamentMatchRepository matchRepository;

    private String fileLocation;

    @Autowired
    public MatchDataLoader(TournamentMatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initializeDatabase();
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    private void initializeDatabase() {
        log.info("Initializing match repository");
        if (!repoIsPopulated()) {
            populateRepo(readPlannedMatchList());
        }
    }

    private List<FootballMatch> readPlannedMatchList() {
        TournamentMatchDataFileReader fileReader = new TournamentMatchDataFileReader(fileLocation);
        List<FootballMatch> footballMatches = fileReader.getFootballMatches();
        log.info("Loading match data from file. {} matches loaded", footballMatches.size());
        return footballMatches;
    }

    private boolean repoIsPopulated() {
        long count = matchRepository.count();
        log.info("{} matches found in repository, planned_matches table", count);
        return count == 0 ? false : true;
    }

    private void populateRepo(List<FootballMatch> footballMatches) {
        log.info("Populating tournament planned_matches repository");
        matchRepository.saveAll(footballMatches);
    }
}
