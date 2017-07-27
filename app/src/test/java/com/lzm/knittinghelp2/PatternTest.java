package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PatternException;
import com.lzm.knittinghelp2.exceptions.SectionException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class PatternTest {

    private String description;
    private Pattern pattern;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

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
    public void getActiveSectionShouldThrowExceptionWhenNotInitialized() throws Exception {
        assertThat(pattern.getName(), is("TMNT"));
        assertThat(pattern.getContent(), is(description));

        List<Section> sections = pattern.getSections();
        assertThat(sections.size(), is(2));

        expectedException.expect(PatternException.class);
        expectedException.expectMessage("Pattern not initialized!");

        pattern.getActiveSection();
    }

    @Test
    public void getActiveStepShouldThrowExceptionWhenNotInitialized() throws Exception {
        assertThat(pattern.getName(), is("TMNT"));
        assertThat(pattern.getContent(), is(description));

        List<Section> sections = pattern.getSections();
        assertThat(sections.size(), is(2));

        expectedException.expect(PatternException.class);
        expectedException.expectMessage("Pattern not initialized!");

        pattern.getActiveStep();
    }

    @Test
    public void shouldCreatePatternWithTitleAndSectionsAndSetFirstStepFromFirstSectionActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "1: st 4 in magic ring (4)");
        Step expectedActiveStep = new Step(expectedActivePart, "1: st 4 in magic ring (4)");

        assertThat(pattern.getName(), is("TMNT"));
        assertThat(pattern.getContent(), is(description));

        pattern.start();

        List<Section> sections = pattern.getSections();
        assertThat(sections.size(), is(2));

        Section firstSection = sections.get(0);
        assertThat(firstSection.getPattern(), is(pattern));
        assertThat(firstSection.getTitle(), is("LEGS (make 2)"));
        assertThat(firstSection.getOrder(), is(1));
        List<Part> firstSectionParts = firstSection.getParts();
        assertThat(firstSectionParts.size(), is(3));
        assertThat(firstSectionParts.get(0).getContent(), is("1: st 4 in magic ring (4)"));
        assertThat(firstSectionParts.get(0).getSection(), is(firstSection));
        assertThat(firstSectionParts.get(1).getContent(), is("2: st 2 in each around (8)"));
        assertThat(firstSectionParts.get(1).getSection(), is(firstSection));
        assertThat(firstSectionParts.get(2).getContent(), is("3-7: st in each (8) Finish. Leave tail for sewing"));
        assertThat(firstSectionParts.get(2).getSection(), is(firstSection));

        Section secondSection = sections.get(1);
        assertThat(secondSection.getPattern(), is(pattern));
        assertThat(secondSection.getTitle(), is("ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)"));
        assertThat(secondSection.getOrder(), is(2));
        List<Part> secondSectionParts = secondSection.getParts();
        assertThat(secondSectionParts.size(), is(4));
        assertThat(secondSectionParts.get(0).getContent(), is("1: st 4 in magic ring (4)"));
        assertThat(secondSectionParts.get(0).getSection(), is(secondSection));
        assertThat(secondSectionParts.get(1).getContent(), is("2: st 2 in each (8)"));
        assertThat(secondSectionParts.get(1).getSection(), is(secondSection));
        assertThat(secondSectionParts.get(2).getContent(), is("3-7: st in each (8)"));
        assertThat(secondSectionParts.get(2).getSection(), is(secondSection));
        assertThat(secondSectionParts.get(3).getContent(), is("8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing"));

        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
        assertThat(secondSectionParts.get(3).getSection(), is(secondSection));
    }

    @Test
    public void nextPartShouldthrowExceptionWhenNotInitialized() throws Exception {
        expectedException.expect(PatternException.class);
        expectedException.expectMessage("Pattern not initialized!");

        pattern.nextPart();
    }

    @Test
    public void nextPartShouldSetNextPartFromActiveSectionAsActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "2: st 2 in each around (8)");
        Step expectedActiveStep = new Step(expectedActivePart, "2: st 2 in each around (8)");

        pattern.start();

        pattern.nextPart();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextSectionShouldSetNextSectionAsActiveAndStartIt() throws Exception {
        Section expectedActiveSection = new Section(pattern, "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "1: st 4 in magic ring (4)");
        Step expectedActiveStep = new Step(expectedActivePart, "1: st 4 in magic ring (4)");

        pattern.start();
        pattern.nextSection();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextSectionShouldThrowExceptionWhenPastNumberOfSections() throws Exception {
        expectedException.expect(SectionException.class);
        expectedException.expectMessage("Next section not found");
        pattern.nextSection();
        pattern.nextSection();
        pattern.nextSection();
    }

    @Test
    public void prevSectionShouldThrowExceptionWhenNotInitialized() throws Exception {
        expectedException.expect(PatternException.class);
        expectedException.expectMessage("Pattern not initialized!");
        pattern.prevSection();
    }

    @Test
    public void prevSectionShouldThrowExceptionWhenGoingBefore0() throws Exception {
        pattern.start();
        expectedException.expect(SectionException.class);
        expectedException.expectMessage("Prev section not found");
        pattern.prevSection();
    }

    @Test
    public void prevSectionShouldSetPrevSectionAsActiveAndStartIt() throws Exception {
        pattern.start();
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "1: st 4 in magic ring (4)");
        Step expectedActiveStep = new Step(expectedActivePart, "1: st 4 in magic ring (4)");

        pattern.nextSection();
        pattern.prevSection();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void nextPartShouldGoOnToNextSectionAfterFinishingAllStepsInSection() throws Exception {
        Section expectedActiveSection = new Section(pattern, "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "1: st 4 in magic ring (4)");
        Step expectedActiveStep = new Step(expectedActivePart, "1: st 4 in magic ring (4)");

        pattern.start();
        pattern.nextPart();
        pattern.nextPart();
        pattern.nextPart();
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
        Part expectedActivePart = new Part(expectedActiveSection, "1: st 4 in magic ring (4)");
        Step expectedActiveStep = new Step(expectedActivePart, "1: st 4 in magic ring (4)");

        pattern.nextSection();
        pattern.nextPart();

        pattern.first();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void prevPartShouldSetPrevPartFromSectionAsActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "2: st 2 in each around (8)");
        Step expectedActiveStep = new Step(expectedActivePart, "2: st 2 in each around (8)");

        pattern.start();
        pattern.nextPart();
        pattern.nextPart();
        pattern.prevPart();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void prevPartShouldSetLastStepFromPreviousSectionAsActive() throws Exception {
        Section expectedActiveSection = new Section(pattern, "LEGS (make 2)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each around (8)\n" +
                "3-7: st in each (8) Finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "3-7: st in each (8) Finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActivePart, "3-7: st in each (8) Finish. Leave tail for sewing");

        pattern.start();
        pattern.nextSection();
        pattern.prevPart();
        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }

    @Test
    public void shouldSetAsActiveAnyStep() throws Exception {
        Section expectedActiveSection = new Section(pattern, "ARMS (make 2, I used a smaller hook to make them slightly smaller than the legs)\n" +
                "1: st 4 in magic ring (4)\n" +
                "2: st 2 in each (8)\n" +
                "3-7: st in each (8)\n" +
                "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Part expectedActivePart = new Part(expectedActiveSection, "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");
        Step expectedActiveStep = new Step(expectedActivePart, "8: hdc, dc, dc, hdc, st, sl st, finish. Leave tail for sewing");

        assertThat(pattern.getName(), is("TMNT"));
        assertThat(pattern.getContent(), is(description));

        pattern.start();
        pattern.setSelected(2, 4, 1);

        Section activeSection = pattern.getActiveSection();
        Step activeStep = pattern.getActiveStep();

        assertThat(activeSection, is(expectedActiveSection));
        assertThat(activeStep, is(expectedActiveStep));
    }
}
