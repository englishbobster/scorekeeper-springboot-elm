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
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.model.MatchType;
import stos.projects.scorekeeper.tournamentdata.tournamentmatchrepo.TournamentMatchRepository;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminRestController.class)
public class AdminRestControllerTest {

    public static final String WEMBLEY = "Wembley";
    public static final String ADMIN_PLANNED_MATCHES = "/admin/planned-matches";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TournamentMatchRepository matchRepository;

    private static final FootballMatch MATCH_A = FootballMatch.builder().id(1).arena("Old Trafford").matchTime(ZonedDateTime.now())
            .matchType(MatchType.A).homeTeam("Reds").awayTeam("Blues").build();
    private static final FootballMatch MATCH_B = FootballMatch.builder().id(2).arena(WEMBLEY).matchTime(ZonedDateTime.now())
            .matchType(MatchType.A).homeTeam("United").awayTeam("TheArse").build();

    @Test
    public void should_return_the_list_of_planned_matches() throws Exception {
        //given
        List<FootballMatch> matches = ImmutableList.of(MATCH_A, MATCH_B);
        //when
        Mockito.when(matchRepository.findAll()).thenReturn(matches);

        mvc.perform(get(ADMIN_PLANNED_MATCHES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tournamentMatches", hasSize(2)))
                .andExpect(jsonPath("$.tournamentMatches[1].arena", is(WEMBLEY)));
    }

    @Test
    public void should_return_an_empty_list_of_planned_matches() throws Exception {
        //given
        List<FootballMatch> matches = ImmutableList.of();
        //when
        Mockito.when(matchRepository.findAll()).thenReturn(matches);

        mvc.perform(get(ADMIN_PLANNED_MATCHES).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.tournamentMatches", hasSize(0)));
    }
}