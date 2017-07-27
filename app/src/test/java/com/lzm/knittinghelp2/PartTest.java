package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;
import com.lzm.knittinghelp2.exceptions.StepException;

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
        assertThat(steps.get(0).getOrder(), is(1));
        assertThat(steps.get(0).getContent(), is("3: st in first, st 3 in next, st in next 2, st 3 in next, st in next 2 (16)"));
    }

    @Test
    public void splitShouldSplitOnCommaByDefaultAndNotSetFirstPartAsActive() throws Exception {
        part.split();

        List<Step> steps = part.getSteps();
        assertThat(steps.size(), is(5));
        assertThat(steps.get(0).getOrder(), is(1));
        assertThat(steps.get(0).getContent(), is("3: st in first,"));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getOrder(), is(2));
        assertThat(steps.get(1).getContent(), is("st 3 in next,"));
        assertThat(steps.get(1).getPart(), is(part));
        assertThat(steps.get(2).getOrder(), is(3));
        assertThat(steps.get(2).getContent(), is("st in next 2,"));
        assertThat(steps.get(2).getPart(), is(part));
        assertThat(steps.get(3).getOrder(), is(4));
        assertThat(steps.get(3).getContent(), is("st 3 in next,"));
        assertThat(steps.get(3).getPart(), is(part));
        assertThat(steps.get(4).getOrder(), is(5));
        assertThat(steps.get(4).getContent(), is("st in next 2 (16)"));
        assertThat(steps.get(4).getPart(), is(part));

        expectedException.expect(PartException.class);
        expectedException.expectMessage("Part not initialized!");

        part.getActiveStep();
    }

    @Test
    public void startShouldSetFirstPartAsActive() throws Exception {
        Step expectedStep = new Step(part, "3: st in first,");
        part.split();
        part.start();

        List<Step> steps = part.getSteps();
        assertThat(steps.size(), is(5));
        assertThat(steps.get(0).getOrder(), is(1));
        assertThat(steps.get(0).getContent(), is("3: st in first,"));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getOrder(), is(2));
        assertThat(steps.get(1).getContent(), is("st 3 in next,"));
        assertThat(steps.get(1).getPart(), is(part));
        assertThat(steps.get(2).getOrder(), is(3));
        assertThat(steps.get(2).getContent(), is("st in next 2,"));
        assertThat(steps.get(2).getPart(), is(part));
        assertThat(steps.get(3).getOrder(), is(4));
        assertThat(steps.get(3).getContent(), is("st 3 in next,"));
        assertThat(steps.get(3).getPart(), is(part));
        assertThat(steps.get(4).getOrder(), is(5));
        assertThat(steps.get(4).getContent(), is("st in next 2 (16)"));
        assertThat(steps.get(4).getPart(), is(part));
        assertThat(part.getActiveStep(), is(expectedStep));
    }

    @Test
    public void settingSeparatorShouldSplitOnSeparatorInstead() throws Exception {
        description = "3-7: st in each (8) Finish. Leave tail for sewing";
        part = new Part(null, description);

        part.setSeparator(".");
        part.split();

        List<Step> steps = part.getSteps();
        assertThat(steps.size(), is(2));
        assertThat(steps.get(0).getContent(), is("3-7: st in each (8) Finish."));
        assertThat(steps.get(0).getPart(), is(part));
        assertThat(steps.get(1).getContent(), is("Leave tail for sewing"));
        assertThat(steps.get(1).getPart(), is(part));
    }

    @Test
    public void nextShouldThrowExceptionByDefault() throws Exception {
        part.start();
        expectedException.expect(StepException.class);
        expectedException.expectMessage("No steps found");
        part.next();
    }

    @Test
    public void nextShouldSetNextPartAsActiveWhenSplit() throws Exception {
        part.start();
        Step expectedStep = new Step(part, "st 3 in next,");
        part.split();
        part.next();
        assertThat(part.getActiveStep(), is(expectedStep));
    }

    @Test
    public void nextShouldThrowExceptionWhenNotInitialized() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("Part not initialized!");
        part.next();
    }

    @Test
    public void nextShouldThrowExceptionWhenPastNumberOfParts() throws Exception {
        part.start();
        expectedException.expect(StepException.class);
        expectedException.expectMessage("Next step not found");
        part.split();
        part.next();
        part.next();
        part.next();
        part.next();
        part.next();
    }

    @Test
    public void prevShouldThrowExceptionWhenNotInitialized() throws Exception {
        expectedException.expect(PartException.class);
        expectedException.expectMessage("Part not initialized!");
        part.prev();
    }

    @Test
    public void prevShouldThrowExceptionByDefault() throws Exception {
        part.start();
        expectedException.expect(StepException.class);
        expectedException.expectMessage("No steps found");
        part.prev();
    }

    @Test
    public void prevShouldThrowExceptionWhenGoingBefore0() throws Exception {
        part.start();
        expectedException.expect(StepException.class);
        expectedException.expectMessage("Prev step not found");
        part.split();
        part.prev();
    }

    @Test
    public void prevShouldSetPreviousPartAsActive() throws Exception {
        part.start();
        Step expectedStep = new Step(part, "st 3 in next,");
        part.split();
        part.next();
        part.next();
        part.prev();
        assertThat(part.getActiveStep(), is(expectedStep));
    }

    @Test
    public void firstShouldSetFirstPartAsActive() throws Exception {
        part.start();
        Step expectedStep = new Step(part, "3: st in first,");
        part.split();
        part.next();
        part.next();
        part.first();
        assertThat(part.getActiveStep(), is(expectedStep));
    }

    @Test
    public void lastShouldSetLastPartAsActive() throws Exception {
        Step expectedStep = new Step(part, "st in next 2 (16)");
        part.split();
        part.last();
        assertThat(part.getActiveStep(), is(expectedStep));
    }

    @Test
    public void shouldSetAnyStepAsActive() throws Exception {
        part.split();
        part.start();
        part.setSelected(3);

        Step expectedStep = new Step(part, "st in next 2,");
        assertThat(part.getActiveStep(), is(expectedStep));
    }
}
