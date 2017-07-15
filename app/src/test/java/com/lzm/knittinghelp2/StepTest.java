package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class StepTest {

    private String description;
    private Step step;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        description = "3: st in first, st 3 in next, st in next 2, st 3 in next, st in next 2 (16)";
        step = new Step(null, description);
    }

    @Test
    public void shouldBeCreatedFromStringAsDefaultWithSinglePart() throws Exception {
        assertThat(step.getContent(), is(description));
        List<Part> parts = step.getParts();
        assertThat(parts.size(), is(1));
        assertThat(parts.get(0).getDescription(), is("3: st in first, st 3 in next, st in next 2, st 3 in next, st in next 2 (16)"));
    }

    @Test
    public void splitShouldSplitOnCommaByDefaultAndSetFirstPartAsActive() throws Exception {
        Part expectedPart = new Part(step, "3: st in first,");
        step.split();

        List<Part> parts = step.getParts();
        assertThat(parts.size(), is(5));
        assertThat(parts.get(0).getDescription(), is("3: st in first,"));
        assertThat(parts.get(0).getStep(), is(step));
        assertThat(parts.get(1).getDescription(), is("st 3 in next,"));
        assertThat(parts.get(1).getStep(), is(step));
        assertThat(parts.get(2).getDescription(), is("st in next 2,"));
        assertThat(parts.get(2).getStep(), is(step));
        assertThat(parts.get(3).getDescription(), is("st 3 in next,"));
        assertThat(parts.get(3).getStep(), is(step));
        assertThat(parts.get(4).getDescription(), is("st in next 2 (16)"));
        assertThat(parts.get(4).getStep(), is(step));
        assertThat(step.getActivePart(), is(expectedPart));
    }

    @Test
    public void settingSeparatorShouldSplitOnSeparatorInstead() throws Exception {
        description = "3-7: st in each (8) Finish. Leave tail for sewing";
        step = new Step(null, description);

        step.setSeparator(".");
        step.split();

        List<Part> parts = step.getParts();
        assertThat(parts.size(), is(2));
        assertThat(parts.get(0).getDescription(), is("3-7: st in each (8) Finish."));
        assertThat(parts.get(0).getStep(), is(step));
        assertThat(parts.get(1).getDescription(), is("Leave tail for sewing"));
        assertThat(parts.get(1).getStep(), is(step));
    }

    @Test
    public void nextShouldThrowExceptionByDefault() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("No parts found");
        step.next();
    }

    @Test
    public void nextShouldSetNextPartAsActiveWhenSplit() throws Exception {
        Part expectedPart = new Part(step, "st 3 in next,");
        step.split();
        step.next();
        assertThat(step.getActivePart(), is(expectedPart));
    }

    @Test
    public void nextShouldThrowExceptionWhenPastNumberOfParts() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("Next part not found");
        step.split();
        step.next();
        step.next();
        step.next();
        step.next();
        step.next();
    }

    @Test
    public void prevShouldThrowExceptionByDefault() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("No parts found");
        step.prev();
    }

    @Test
    public void prevShouldThrowExceptionWhenGoingBefore0() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("Prev part not found");
        step.split();
        step.prev();
    }

    @Test
    public void prevShouldSetPreviousPartAsActive() throws Exception {
        Part expectedPart = new Part(step, "st 3 in next,");
        step.split();
        step.next();
        step.next();
        step.prev();
        assertThat(step.getActivePart(), is(expectedPart));
    }

    @Test
    public void firstShouldSetFirstPartAsActive() throws Exception {
        Part expectedPart = new Part(step, "3: st in first,");
        step.split();
        step.next();
        step.next();
        step.first();
        assertThat(step.getActivePart(), is(expectedPart));
    }

    @Test
    public void lastShouldSetLastPartAsActive() throws Exception {
        Part expectedPart = new Part(step, "st in next 2 (16)");
        step.split();
        step.last();
        assertThat(step.getActivePart(), is(expectedPart));
    }
}
