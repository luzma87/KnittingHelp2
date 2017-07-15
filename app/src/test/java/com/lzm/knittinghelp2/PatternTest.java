package com.lzm.knittinghelp2;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PatternTest {

    private String description;
    private Pattern pattern;

    @Before
    public void setUp() throws Exception {
        description = "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing\n\n" +
                "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing\n\n";

        pattern = new Pattern("TMNT", description);
    }

    @Test
    public void shouldCreatePatternWithTitleAndSectionsAndSetFirstStepFromFirstSectionActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "1: st 4 in magic ring (4)");

        assertThat(pattern.getName(), is("TMNT"));
        assertThat(pattern.getContent(), is(description));

        List<Section> sections = pattern.getSections();
        assertThat(sections.size(), is(2));

        Section firstSection = sections.get(0);
        assertThat(firstSection.getPattern(), is(pattern));
        assertThat(firstSection.getTitle(), is("LEGS (make 2)"));
        List<Step> firstSectionSteps = firstSection.getSteps();
        assertThat(firstSectionSteps.size(), is(3));
        assertThat(firstSectionSteps.get(0).getDescription(), is("1: st 4 in magic ring (4)"));
        assertThat(firstSectionSteps.get(0).getSection(), is(firstSection));
        assertThat(firstSectionSteps.get(1).getDescription(), is("2: st 2 in each around (8)"));
        assertThat(firstSectionSteps.get(1).getSection(), is(firstSection));
        assertThat(firstSectionSteps.get(2).getDescription(), is("3-7: st in each (8) Finish. Leave tail for sewing"));
        assertThat(firstSectionSteps.get(2).getSection(), is(firstSection));

        Section secondSection = sections.get(1);
        assertThat(secondSection.getPattern(), is(pattern));
        assertThat(secondSection.getTitle(), is("ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)"));
        List<Step> secondSectionSteps = secondSection.getSteps();
        assertThat(secondSectionSteps.size(), is(4));
        assertThat(secondSectionSteps.get(0).getDescription(), is("1: st 4 in magic ring (4)"));
        assertThat(secondSectionSteps.get(0).getSection(), is(secondSection));
        assertThat(secondSectionSteps.get(1).getDescription(), is("2: st 2 in each (8)"));
        assertThat(secondSectionSteps.get(1).getSection(), is(secondSection));
        assertThat(secondSectionSteps.get(2).getDescription(), is("3-7: st in each (8)"));
        assertThat(secondSectionSteps.get(2).getSection(), is(secondSection));
        assertThat(secondSectionSteps.get(3).getDescription(), is("8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing"));

        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(secondSectionSteps.get(3).getSection(), is(secondSection));
    }

    @Test
    public void nextStepShouldSetNextStepFromActiveSectionAsActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "2: st 2 in each around (8)");

        pattern.nextStep();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextSectionShouldSetNextSSectionAsActiveAndStartIt() throws Exception {
        Section expectedActiveSection = new Section(pattern, "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "1: st 4 in magic ring (4)");

        pattern.nextSection();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextSectionShouldNotGoPastNumberOfSections() throws Exception {
        Section expectedActiveSection = new Section(pattern, "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "1: st 4 in magic ring (4)");

        pattern.nextSection();
        pattern.nextSection();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextStepShouldGoOnToNextSectionAfterFinishingAllStepsInSection() throws Exception {
        Section expectedActiveSection = new Section(pattern, "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "1: st 4 in magic ring (4)");

        pattern.nextStep();
        pattern.nextStep();
        pattern.nextStep();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void firstShouldSetFirstSectionAsActiveAndStartIt() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "1: st 4 in magic ring (4)");

        pattern.nextSection();
        pattern.nextStep();

        pattern.first();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void prevStepShouldSetPrevStepFromSectionAsActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "2: st 2 in each around (8)");

        pattern.nextStep();
        pattern.nextStep();
        pattern.prevStep();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void prevStepShouldSetLastStepFromPreviousSectionAsActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActiveSection, "3-7: st in each (8) Finish. Leave tail for sewing");

        pattern.nextSection();
        pattern.prevStep();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }
}
