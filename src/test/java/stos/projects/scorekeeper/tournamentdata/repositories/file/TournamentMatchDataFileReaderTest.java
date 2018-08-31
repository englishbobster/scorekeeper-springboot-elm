package stos.projects.scorekeeper.tournamentdata.repositories.file;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import stos.projects.scorekeeper.model.FootballMatch;
import stos.projects.scorekeeper.model.MatchType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class TournamentMatchDataFileReaderTest {
    private static final String FILE_SEPARATOR = File.separator;
    private static final String COMMENT_LINE = "### This line is a comment!";
    private static final String MATCH_LINE_1 = "1,Thu,\"Jun 12, 2014\",21:00,Brazil,Croatia,Sao Paulo, A";
    private static final String[] PARSED_MATCH_LINE_1 = {"1", "Thu", "Jun 12, 2014", "21:00", "Brazil",
            "Croatia", "Sao Paulo", "A"};
    private static final String MATCH_LINE_2 = "2,Fri,\"Jun 13, 2014\",17:00,Mexico,Cameroon,Natal, A";
    private static final String MATCH_LINE_3 = "3, Fri,\"Jun 13, 2014\",20:00,Spain,Netherlands,Salvador,FINAL";
    private static final String WONKY_LINE = ",Fri, \"\", 20:00, Spain,Spain   , Rubbish";
    private static final String[] PARSED_WONKY_LINE = {"", "Fri", "", "20:00", "Spain", "Spain", "Rubbish"};
    private static final String TEST_FILE_NAME = "testCsv.csv";
    public static final String NON_EXISTANT_FILE = "this_file_should_not_exist.txt";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private TournamentMatchDataFileReader tournamentMatchDataFileReader;

    @Before
    public void setUp() throws IOException {
        File testCsv = folder.newFile(TEST_FILE_NAME);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(testCsv))){
            writer.write(COMMENT_LINE);
            writer.newLine();
            writer.write(MATCH_LINE_1);
            writer.newLine();
            writer.write(MATCH_LINE_2);
            writer.newLine();
            writer.write(MATCH_LINE_3);
            writer.newLine();
            writer.flush();
        }
        tournamentMatchDataFileReader = new TournamentMatchDataFileReader(folder.getRoot().toPath().toString() + FILE_SEPARATOR + TEST_FILE_NAME);
    }

    @Test
    public void load_matches_from_file() {
        List<FootballMatch> matchRows = tournamentMatchDataFileReader.getFootballMatches();
        assertThat(matchRows.size(), is(3));
        //check some field values.
        assertThat(matchRows.get(0).getMatchType(), is(MatchType.A));
        assertThat(matchRows.get(2).getMatchType(), is(MatchType.FINAL));
    }

    @Test
    (expected = RuntimeException.class)
    public void fail_to_load_matches_from_file() {
        tournamentMatchDataFileReader = new TournamentMatchDataFileReader(NON_EXISTANT_FILE);
        tournamentMatchDataFileReader.getFootballMatches();
    }

    @Test
    public void parse_a_file_line_to_a_FootballMatch() {
        Optional<FootballMatch> matchOptional = tournamentMatchDataFileReader.parseLine(MATCH_LINE_1);
        FootballMatch footballMatch = matchOptional.orElse(null);
        ZonedDateTime matchTime = ZonedDateTime.of(2014, 6, 12, 21, 0, 0, 0, ZoneId.systemDefault());
        FootballMatch expectedMatch = FootballMatch.builder().id(1).matchTime(matchTime).arena("Sao Paulo")
                .matchType(MatchType.A).homeTeam("Brazil").awayTeam("Croatia").build();
        assertThat(footballMatch, is(equalTo(expectedMatch)));
    }

    @Test
    public void split_line_to_component_strings() {
        List<String> splitLine = tournamentMatchDataFileReader.splitLine(MATCH_LINE_1);
        assertThat(splitLine, contains(PARSED_MATCH_LINE_1));
    }

    @Test
    public void split_line_to_component_strings_very_wrong_line_format() {
        List<String> splitLine = tournamentMatchDataFileReader.splitLine(WONKY_LINE);
        assertThat(splitLine, contains(PARSED_WONKY_LINE));
    }

    @Test
    public void create_ZonedDateTime_from_line_fields() {
        String monthDateYear = "Jun 12, 2014";
        String time = "20:00";
        ZonedDateTime expectedResult = ZonedDateTime.of(2014,
                6, 12, 20, 0, 0, 0, ZoneId.systemDefault());
        assertThat(tournamentMatchDataFileReader.createMatchTime(monthDateYear, time), is(equalTo(expectedResult)));
    }

    @Test
    public void create_another_ZonedDateTime_from_line_fields() {
        String monthDateYear = "Aug 1, 1999";
        String time = "8:55";
        ZonedDateTime expectedResult = ZonedDateTime.of(1999,
                8, 1, 8, 55, 0, 0, ZoneId.systemDefault());
        assertThat(tournamentMatchDataFileReader.createMatchTime(monthDateYear, time), is(equalTo(expectedResult)));
    }

}