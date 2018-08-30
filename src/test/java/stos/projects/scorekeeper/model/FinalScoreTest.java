package stos.projects.scorekeeper.model;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class FinalScoreTest {

    @Test
    public void scores_are_equal() {
        FinalScore finalScore1 = new FinalScore(1, 1);
        FinalScore finalScore2 = new FinalScore(1, 1);

        assertThat(finalScore1, is(equalTo(finalScore2)));
    }

    @Test
    public void scores_are_not_equal() throws Exception {
        FinalScore finalScore1 = new FinalScore(1, 1);
        FinalScore finalScore2 = new FinalScore(1, 3);

        assertThat(finalScore1, is(not(equalTo(finalScore2))));
    }

    @Test
    public void toString_is_printed_correctly() throws Exception {
        FinalScore finalScore1 = new FinalScore(1, 7);

        String expectedString = "[ 1 - 7 ]";
        assertThat(finalScore1.toString(), is(expectedString));
    }

}