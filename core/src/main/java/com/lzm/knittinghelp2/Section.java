package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;
import com.lzm.knittinghelp2.exceptions.StepException;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private String title;
    private List<Step> steps;
    private Pattern pattern;
    private String content;

    private int activeStepIndex;

    public Section(Pattern pattern, String content) {
        this.pattern = pattern;
        this.content = content;
        this.activeStepIndex = 0;
        steps = new ArrayList<>();
        String[] parts = content.split("\n");
        boolean isFirstLine = true;
        for (String part : parts) {
            part = part.trim();
            if (isFirstLine) {
                title = part;
                isFirstLine = false;
            } else {
                Step step = new Step(this, part);
                steps.add(step);
            }
        }
    }

    public List<Step> getSteps() {
        return steps;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public String toString() {
        return title;
    }

    public void first() {
        activeStepIndex = 0;
        getActiveStep().first();
    }

    public void last() {
        activeStepIndex = steps.size() - 1;
        getActiveStep().last();
    }

    public void next() throws StepException {
        try {
            getActiveStep().next();
        } catch (PartException e) {
            if (activeStepIndex == steps.size() - 1) {
                throw new StepException("Next step not found");
            }
            activeStepIndex += 1;
        }
    }

    public void prev() throws StepException {
        try {
            getActiveStep().prev();
        } catch (PartException e) {
            if (activeStepIndex == 0) {
                throw new StepException("Prev step not found");
            }
            activeStepIndex -= 1;
        }
    }

    public Step getActiveStep() {
        return steps.get(activeStepIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        if (!title.equals(section.title)) return false;
        if (pattern != null ? !pattern.equals(section.pattern) : section.pattern != null)
            return false;
        return content != null ? content.equals(section.content) : section.content == null;

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    public Part getActivePart() throws PartException {
        return getActiveStep().getActivePart();
    }
}
