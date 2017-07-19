package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PartTest {

    private String description;
    private Part part;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        description = "3: st in first, st 3 in next, st in next 2, st 3 in next, st in next 2 (16)";
        part = new Part(null, description);
    }

    @Test
    public void shouldBeCreatedFromStringAsDefaultWithSinglePart() throws Exception {
        assertThat(part.getContent(), is(description));
        List<Step> steps = part.getSteps();
        assertThat(steps.size(), is(1));
        assertThat(steps.get(0).getDescription(), is("3: st in first, st 3 in next, st in next 2, st 3 in next, st in next 2 (16)"));
    }

    @Test
    public void splitShouldSplitOnCommaByDefaultAndSetFirstPartAsActive() throws Exception {
        Step expectedStep = new Step(part, "3: st in first,");
        part.split();

        List<Step> steps = part.getSteps();
        assertThat(steps.size(), is(5));
        assertThat(steps.get(0).getDescription(), is("3: st in first,"));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getDescription(), is("st 3 in next,"));
        assertThat(steps.get(1).getPart(), is(part));
        assertThat(steps.get(2).getDescription(), is("st in next 2,"));
        assertThat(steps.get(2).getPart(), is(part));
        assertThat(steps.get(3).getDescription(), is("st 3 in next,"));
        assertThat(steps.get(3).getPart(), is(part));
        assertThat(steps.get(4).getDescription(), is("st in next 2 (16)"));
        assertThat(steps.get(4).getPart(), is(part));
        assertThat(part.getActivePart(), is(expectedStep));
    }

    @Test
    public void settingSeparatorShouldSplitOnSeparatorInstead() throws Exception {
        description = "3-7: st in each (8) Finish. Leave tail for sewing";
        part = new Part(null, description);

        part.setSeparator(".");
        part.split();

        List<Step> steps = part.getSteps();
        assertThat(steps.size(), is(2));
        assertThat(steps.get(0).getDescription(), is("3-7: st in each (8) Finish."));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getDescription(), is("Leave tail for sewing"));
        assertThat(steps.get(1).getPart(), is(part));
    }

    @Test
    public void nextShouldThrowExceptionByDefault() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("No parts found");
        part.next();
    }

    @Test
    public void nextShouldSetNextPartAsActiveWhenSplit() throws Exception {
        Step expectedStep = new Step(part, "st 3 in next,");
        part.split();
        part.next();
        assertThat(part.getActivePart(), is(expectedStep));
    }

    @Test
    public void nextShouldThrowExceptionWhenPastNumberOfParts() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("Next part not found");
        part.split();
        part.next();
        part.next();
        part.next();
        part.next();
        part.next();
    }

    @Test
    public void prevShouldThrowExceptionByDefault() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("No parts found");
        part.prev();
    }

    @Test
    public void prevShouldThrowExceptionWhenGoingBefore0() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("Prev part not found");
        part.split();
        part.prev();
    }

    @Test
    public void prevShouldSetPreviousPartAsActive() throws Exception {
        Step expectedStep = new Step(part, "st 3 in next,");
        part.split();
        part.next();
        part.next();
        part.prev();
        assertThat(part.getActivePart(), is(expectedStep));
    }

    @Test
    public void firstShouldSetFirstPartAsActive() throws Exception {
        Step expectedStep = new Step(part, "3: st in first,");
        part.split();
        part.next();
        part.next();
        part.first();
        assertThat(part.getActivePart(), is(expectedStep));
    }

    @Test
    public void lastShouldSetLastPartAsActive() throws Exception {
        Step expectedStep = new Step(part, "st in next 2 (16)");
        part.split();
        part.last();
        assertThat(part.getActivePart(), is(expectedStep));
    }
}
