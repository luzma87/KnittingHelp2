package com.lzm.knittinghelp2;


import com.lzm.knittinghelp2.exceptions.StepException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SectionTest {

    private String description;
    private Section section;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        description = "BELLY PLATE\n" +
                "1: ch 5, wait\n" +
                "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)\n" +
                "3: st 2 in first, st in next 2, st 2 in next 3, st in next 2, st 2 in next 2 (16)\n" +
                "4: st 2 in first, st in next 4, st 2 in next, st in next 2, st 2 in next, st in next 4, st 2 in next, st in next 2 (20)\n" +
                "5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)\n\n";
        section = new Section(null, description);
    }

    @Test
    public void shouldBeCreatedFromStringAndSetFirstStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: ch 5, wait");
        assertThat(section.getContent(), is(description));
        assertThat(section.getTitle(), is("BELLY PLATE"));
        List<Step> steps = section.getSteps();
        assertThat(steps.size(), is(5));
        assertThat(steps.get(0).getContent(), is("1: ch 5, wait"));
        assertThat(steps.get(0).getSection(), is(section));
        assertThat(steps.get(1).getContent(), is("2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)"));
        assertThat(steps.get(1).getSection(), is(section));
        assertThat(steps.get(2).getContent(), is("3: st 2 in first, st in next 2, st 2 in next 3, st in next 2, st 2 in next 2 (16)"));
        assertThat(steps.get(2).getSection(), is(section));
        assertThat(steps.get(3).getContent(), is("4: st 2 in first, st in next 4, st 2 in next, st in next 2, st 2 in next, st in next 4, st 2 in next, st in next 2 (20)"));
        assertThat(steps.get(3).getSection(), is(section));
        assertThat(steps.get(4).getContent(), is("5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)"));
        assertThat(steps.get(4).getSection(), is(section));

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void firstShouldSetFirstStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: ch 5, wait");
        section.next();
        section.next();
        section.first();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void firstShouldSetFirstStepFirstPartAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: ch 5, wait");
        Part expectedActivePart = new Part(expectedActiveStep, "1: ch 5,");
        section.getSteps().get(0).split();
        section.next();
        section.next();
        section.first();

        Step activeStep = section.getActiveStep();
        Part activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void lastShouldSetLastStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)");
        section.last();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void lastShouldSetLastStepLastPartAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)");
        Part expectedActivePart = new Part(expectedActiveStep, "finish. Leave tail for sewing (27)");
        section.getSteps().get(section.getSteps().size() - 1).split();
        section.last();

        Step activeStep = section.getActiveStep();
        Part activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void nextShouldSetNextStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)");
        section.next();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));

        section.next();
        expectedActiveStep = new Step(section, "3: st 2 in first, st in next 2, st 2 in next 3, st in next 2, st 2 in next 2 (16)");

        activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextShouldSetNextPartAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: ch 5, wait");
        Part expectedActivePart = new Part(expectedActiveStep, "1: ch 5,");

        section.getSteps().get(0).split();

        Step activeStep = section.getActiveStep();
        Part activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));

        section.next();

        expectedActivePart = new Part(expectedActiveStep, "wait");
        activeStep = section.getActiveStep();
        activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void nextShouldSetFirstPartOfNextStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)");
        Part expectedActivePart = new Part(expectedActiveStep, "2: st in 2nd from hook,");

        section.getSteps().get(0).split();
        section.getSteps().get(1).split();

        section.next();
        section.next();

        Step activeStep = section.getActiveStep();
        Part activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));

        section.next();

        expectedActivePart = new Part(expectedActiveStep, "st in next 2,");
        activeStep = section.getActiveStep();
        activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void prevShouldSetThePreviousPartAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: ch 5, wait");
        Part expectedActivePart = new Part(expectedActiveStep, "1: ch 5,");

        section.getSteps().get(0).split();

        section.next();
        section.prev();

        Step activeStep = section.getActiveStep();
        Part activePart = section.getActivePart();
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(activePart, is(expectedActivePart));

    }

    @Test
    public void nextShouldThrowExceptionWhenPastNumberOfSteps() throws Exception {
        expectedException.expect(StepException.class);
        expectedException.expectMessage("Next step not found");
        section.next();
        section.next();
        section.next();
        section.next();
        section.next();
    }

    @Test
    public void prevShouldThrowExceptionWhenGoingBefore0() throws Exception {
        expectedException.expect(StepException.class);
        expectedException.expectMessage("Prev step not found");
        section.prev();
    }

    @Test
    public void prevShouldSetPreviousStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)");
        section.next();
        section.next();
        section.prev();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));

        section.prev();
        expectedActiveStep = new Step(section, "1: ch 5, wait");

        activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }
}
