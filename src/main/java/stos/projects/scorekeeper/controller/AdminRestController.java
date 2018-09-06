package stos.projects.scorekeeper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import stos.projects.scorekeeper.controller.TransportObjects.FinalScoreTO;
import stos.projects.scorekeeper.controller.TransportObjects.ScoreTO;
import stos.projects.scorekeeper.controller.TransportObjects.TournamentMatchListTO;
import stos.projects.scorekeeper.model.FinalScore;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("admin/")
public class AdminRestController {

    private final AdminService adminService;

    @Autowired
    public AdminRestController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "planned-matches")
    ResponseEntity<TournamentMatchListTO> getAllPlannedTournamentMatches() {
        List<FootballMatch> plannedMatches = adminService.getPlannedMatches();
        TournamentMatchListTO matchListTO = TournamentMatchListTO.tournamentMatches(plannedMatches);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(matchListTO);
    }

    @RequestMapping(method = RequestMethod.POST, path = "match/{matchid}/final-score")
    ResponseEntity<FinalScoreTO> setFinalScoreForMatch(@PathVariable("matchid") int matchid, @RequestBody ScoreTO finalScore) {
        if (!adminService.matchExists(matchid)) {
            return ResponseEntity.notFound().build();
        }
        if (adminService.finalScoreIsSet(matchid)) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
        } else {
            FinalScore fulltimeScore = adminService.setFinalScore(matchid, finalScore);
            return ResponseEntity.status(HttpStatus.CREATED).body(FinalScoreTO.from(fulltimeScore));
        }
    }
}
