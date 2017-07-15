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
        description = "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing\n\n";
        section = new Section(null, description);
    }

    @Test
    public void shouldBeCreatedFromStringAndSetFirstStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: st 4 in magic ring (4)");
        assertThat(section.getContent(), is(description));
        assertThat(section.getTitle(), is("LEGS (make 2)"));
        List<Step> steps = section.getSteps();
        assertThat(steps.size(), is(3));
        assertThat(steps.get(0).getDescription(), is("1: st 4 in magic ring (4)"));
        assertThat(steps.get(0).getSection(), is(section));
        assertThat(steps.get(1).getDescription(), is("2: st 2 in each around (8)"));
        assertThat(steps.get(1).getSection(), is(section));
        assertThat(steps.get(2).getDescription(), is("3-7: st in each (8) Finish. Leave tail for sewing"));
        assertThat(steps.get(2).getSection(), is(section));

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void firstShouldSetFirstStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "1: st 4 in magic ring (4)");
        section.next();
        section.next();
        section.first();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void lastShouldSetLastStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "3-7: st in each (8) Finish. Leave tail for sewing");
        section.last();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextShouldSetNextStepAsActive() throws Exception {
        Step expectedActiveStep = new Step(section, "2: st 2 in each around (8)");
        section.next();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));

        section.next();
        expectedActiveStep = new Step(section, "3-7: st in each (8) Finish. Leave tail for sewing");

        activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextShouldThrowExceptionWhenPastNumberOfSteps() throws Exception {
        expectedException.expect(StepException.class);
        expectedException.expectMessage("Next step not found");
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
        Step expectedActiveStep = new Step(section, "2: st 2 in each around (8)");
        section.next();
        section.next();
        section.prev();

        Step activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));

        section.prev();
        expectedActiveStep = new Step(section, "1: st 4 in magic ring (4)");

        activeStep = section.getActiveStep();
        assertThat(activeStep, is(expectedActiveStep));
    }
}
