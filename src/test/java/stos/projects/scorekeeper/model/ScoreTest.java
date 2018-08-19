package stos.projects.scorekeeper.model;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ScoreTest {

    @Test
    public void scores_are_equal() {
        Score score1 = new Score(1, 1);
        Score score2 = new Score(1, 1);

        assertThat(score1, is(equalTo(score2)));
    }

    @Test
    public void scores_are_not_equal() throws Exception {
        Score score1 = new Score(1, 1);
        Score score2 = new Score(1, 3);

        assertThat(score1, is(not(equalTo(score2))));
    }

    @Test
    public void toString_is_printed_correctly() throws Exception {
        Score score1 = new Score(1, 7);

        String expectedString = "[ 1 - 7 ]";
        assertThat(score1.toString(), is(expectedString));
    }

}