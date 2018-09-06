package stos.projects.scorekeeper.controller;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import stos.projects.scorekeeper.controller.TransportObjects.ScoreTO;
import stos.projects.scorekeeper.model.FinalScore;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.model.MatchType;
import stos.projects.scorekeeper.service.AdminService;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminRestController.class)
public class AdminRestControllerTest {

    private static final String WEMBLEY = "Wembley";
    private static final String ADMIN_PLANNED_MATCHES = "/admin/planned-matches";
    private static final String ADMIN_SET_FINAL_SCORE = "/admin/match/{matchid}/final-score";
    private static final String OLD_TRAFFORD = "Old Trafford";
    private static final ZonedDateTime NOW = ZonedDateTime.now();
    private static final String RED_TEAM = "Reds";
    private static final String BLUE_TEAM = "Blues";
    private static final int MATCH_ID_2 = 2;
    private static final int MATCH_ID_1 = 1;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdminService adminService;

    private static final FootballMatch MATCH_A = FootballMatch.builder().id(MATCH_ID_1).arena(OLD_TRAFFORD).matchTime(NOW)
            .matchType(MatchType.A).homeTeam(RED_TEAM).awayTeam(BLUE_TEAM).build();
    private static final FootballMatch MATCH_B = FootballMatch.builder().id(MATCH_ID_2).arena(WEMBLEY).matchTime(NOW)
            .matchType(MatchType.A).homeTeam(RED_TEAM).awayTeam(BLUE_TEAM).build();
    public static final String POST_JSON_REQUEST_BODY = "{\"homeScore\": 2, \"awayScore\": 1}";

    @Test
    public void should_return_the_list_of_planned_matches() throws Exception {
        //given
        List<FootballMatch> matches = ImmutableList.of(MATCH_A, MATCH_B);
        //when
        Mockito.when(adminService.getPlannedMatches()).thenReturn(matches);

        mvc.perform(get(ADMIN_PLANNED_MATCHES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tournamentMatches", hasSize(2)))
                .andExpect(jsonPath("$.tournamentMatches[1].arena", is(WEMBLEY)));
    }

    @Test
    public void should_return_an_empty_list_of_planned_matches() throws Exception {
        //given
        List<FootballMatch> matches = ImmutableList.of();
        //when
        Mockito.when(adminService.getPlannedMatches()).thenReturn(matches);

        mvc.perform(get(ADMIN_PLANNED_MATCHES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tournamentMatches", hasSize(0)));
    }

    @Test
    public void should_be_able_to_set_final_score_for_match() throws Exception {
        Mockito.when(adminService.matchExists(MATCH_ID_1)).thenReturn(true);
        Mockito.when(adminService.setFinalScore(MATCH_ID_1, new ScoreTO(2, 1)))
                .thenReturn(new FinalScore(MATCH_ID_1, 2, 1));

        mvc.perform(post(ADMIN_SET_FINAL_SCORE, MATCH_ID_1).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(POST_JSON_REQUEST_BODY)).andExpect(status().isCreated())
        .andExpect(jsonPath("$.matchId", is(1)));

    }

    @Test
    public void setting_final_score_should_return_not_found_if_id_doesnt_exist() throws Exception {
        Mockito.when(adminService.matchExists(MATCH_ID_1)).thenReturn(false);
        mvc.perform(post(ADMIN_SET_FINAL_SCORE, MATCH_ID_1).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(POST_JSON_REQUEST_BODY)).andExpect(status().isNotFound());
    }

    @Test
    public void setting_final_score_should_return_unprocessable_if_score_is_already_set_for_id() throws Exception {
        Mockito.when(adminService.matchExists(MATCH_ID_1)).thenReturn(true);
        Mockito.when(adminService.finalScoreIsSet(MATCH_ID_1)).thenReturn(true);

        mvc.perform(post(ADMIN_SET_FINAL_SCORE, MATCH_ID_1).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(POST_JSON_REQUEST_BODY)).andExpect(status().isUnprocessableEntity());
    }
}