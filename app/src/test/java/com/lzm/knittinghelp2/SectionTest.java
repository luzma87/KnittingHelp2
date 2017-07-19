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
        Part expectedActivePart = new Part(section, "1: ch 5, wait");
        assertThat(section.getContent(), is(description));
        assertThat(section.getTitle(), is("BELLY PLATE"));
        List<Part> parts = section.getParts();
        assertThat(parts.size(), is(5));
        assertThat(parts.get(0).getOrder(), is(1));
        assertThat(parts.get(0).getContent(), is("1: ch 5, wait"));
        assertThat(parts.get(0).getSection(), is(section));
        assertThat(parts.get(1).getOrder(), is(2));
        assertThat(parts.get(1).getContent(), is("2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)"));
        assertThat(parts.get(1).getSection(), is(section));
        assertThat(parts.get(2).getOrder(), is(3));
        assertThat(parts.get(2).getContent(), is("3: st 2 in first, st in next 2, st 2 in next 3, st in next 2, st 2 in next 2 (16)"));
        assertThat(parts.get(2).getSection(), is(section));
        assertThat(parts.get(3).getOrder(), is(4));
        assertThat(parts.get(3).getContent(), is("4: st 2 in first, st in next 4, st 2 in next, st in next 2, st 2 in next, st in next 4, st 2 in next, st in next 2 (20)"));
        assertThat(parts.get(3).getSection(), is(section));
        assertThat(parts.get(4).getOrder(), is(5));
        assertThat(parts.get(4).getContent(), is("5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)"));
        assertThat(parts.get(4).getSection(), is(section));

        Part activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void firstShouldSetFirstStepAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "1: ch 5, wait");
        section.next();
        section.next();
        section.first();

        Part activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void firstShouldSetFirstStepFirstPartAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "1: ch 5, wait");
        Step expectedActiveStep = new Step(expectedActivePart, "1: ch 5,");
        section.getParts().get(0).split();
        section.next();
        section.next();
        section.first();

        Part activePart = section.getActivePart();
        Step activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void lastShouldSetLastStepAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)");
        section.last();

        Part activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void lastShouldSetLastStepLastPartAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "5: st 2 in first, st in next 6, st 3 in next, st in next 3, st 3 in next, st in next 7, st 3 in next, sl st, finish. Leave tail for sewing (27)");
        Step expectedActiveStep = new Step(expectedActivePart, "finish. Leave tail for sewing (27)");
        section.getParts().get(section.getParts().size() - 1).split();
        section.last();

        Part activePart = section.getActivePart();
        Step activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextShouldSetNextStepAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)");
        section.next();

        Part activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));

        section.next();
        expectedActivePart = new Part(section, "3: st 2 in first, st in next 2, st 2 in next 3, st in next 2, st 2 in next 2 (16)");

        activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));
    }

    @Test
    public void nextShouldSetNextPartAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "1: ch 5, wait");
        Step expectedActiveStep = new Step(expectedActivePart, "1: ch 5,");

        section.getParts().get(0).split();

        Part activePart = section.getActivePart();
        Step activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));

        section.next();

        expectedActiveStep = new Step(expectedActivePart, "wait");
        activePart = section.getActivePart();
        activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextShouldSetFirstPartOfNextStepAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)");
        Step expectedActiveStep = new Step(expectedActivePart, "2: st in 2nd from hook,");

        section.getParts().get(0).split();
        section.getParts().get(1).split();

        section.next();
        section.next();

        Part activePart = section.getActivePart();
        Step activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));

        section.next();

        expectedActiveStep = new Step(expectedActivePart, "st in next 2,");
        activePart = section.getActivePart();
        activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void prevShouldSetThePreviousPartAsActive() throws Exception {
        Part expectedActivePart = new Part(section, "1: ch 5, wait");
        Step expectedActiveStep = new Step(expectedActivePart, "1: ch 5,");

        section.getParts().get(0).split();

        section.next();
        section.prev();

        Part activePart = section.getActivePart();
        Step activeStep = section.getActiveStep();
        assertThat(activePart, is(expectedActivePart));
        assertThat(activeStep, is(expectedActiveStep));

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
        Part expectedActivePart = new Part(section, "2: st in 2nd from hook, st in next 2, st 3 in next. Continue on the other side of the ch, st in next 2, st 2 in next (10)");
        section.next();
        section.next();
        section.prev();

        Part activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));

        section.prev();
        expectedActivePart = new Part(section, "1: ch 5, wait");

        activePart = section.getActivePart();
        assertThat(activePart, is(expectedActivePart));
    }
}
