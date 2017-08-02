package com.lzm.knittinghelp2;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StepTest {

    private Step step;
    private Part part;

    @Before
    public void setUp() throws Exception {
        String description = "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing";
        part = new Part(null, description);
        step = new Step(part, description);
    }

    @Test
    public void splitShouldSplitOnCommaByDefaultAndReturnNewSteps() throws Exception {
        List<Step> steps = step.split();

        assertThat(step.getContent(), is("8: hdc,"));
        assertThat(steps.size(), is(7));
        assertThat(steps.get(0).getOrder(), is(1));
        assertThat(steps.get(0).getContent(), is("8: hdc,"));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getOrder(), is(2));
        assertThat(steps.get(1).getContent(), is("dc,"));
        assertThat(steps.get(1).getPart(), is(part));
        assertThat(steps.get(2).getOrder(), is(3));
        assertThat(steps.get(2).getContent(), is("dc,"));
        assertThat(steps.get(2).getPart(), is(part));
        assertThat(steps.get(3).getOrder(), is(4));
        assertThat(steps.get(3).getContent(), is("hdc,"));
        assertThat(steps.get(3).getPart(), is(part));
        assertThat(steps.get(4).getOrder(), is(5));
        assertThat(steps.get(4).getContent(), is("st,"));
        assertThat(steps.get(4).getPart(), is(part));
        assertThat(steps.get(5).getOrder(), is(6));
        assertThat(steps.get(5).getContent(), is("sl st,"));
        assertThat(steps.get(5).getPart(), is(part));
        assertThat(steps.get(6).getOrder(), is(7));
        assertThat(steps.get(6).getContent(), is("finish. Leave tail for sewing"));
        assertThat(steps.get(6).getPart(), is(part));
    }

    @Test
    public void settingSeparatorShouldSplitOnSeparatorInstead() throws Exception {
        step.setSeparator(".");
        List<Step> steps = step.split();

        assertThat(steps.size(), is(2));
        assertThat(steps.get(0).getContent(), is("8: hdc, dc, dc, hdc, st, sl st, finish."));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getContent(), is("Leave tail for sewing"));
        assertThat(steps.get(1).getPart(), is(part));
    }
}
