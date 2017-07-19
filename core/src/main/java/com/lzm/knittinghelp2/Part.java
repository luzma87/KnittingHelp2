package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;

import java.util.ArrayList;
import java.util.List;

public class Part {
    private long id;

    private String content;
    private Section section;
    private List<Step> steps;
    private String separator;
    private int activeStepIndex;
    private int order;

    public Part(long id, Section section, String content) {
        this.id = id;
        this.content = content;
        this.section = section;

        this.steps = new ArrayList<>();
        Step step = new Step(this, content);
        step.setOrder(1);
        steps.add(step);
        this.separator = ",";
        this.activeStepIndex = 0;
    }

    public Part(Section section, String content) {
        this(0, section, content);
    }

    public String getContent() {
        return content;
    }

    public Section getSection() {
        return section;
    }

    public String toString() {
        return content;
    }

    public void split() {
        this.steps = new ArrayList<>();
        String separatorForSplit = getSeparatorForSplit();

        String[] parts = content.split(separatorForSplit);
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (i < parts.length - 1) {
                part = part + separator;
            }
            Step step = new Step(this, part);
            step.setOrder(i + 1);
            this.steps.add(step);
        }

    }

    private String getSeparatorForSplit() {
        String separatorForSplit = separator;
        if (separator.equals(".")) {
            separatorForSplit = "\\.";
        }
        return separatorForSplit;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void next() throws PartException {
        if (steps.size() == 1) {
            throw new PartException("No parts found");
        }
        if (activeStepIndex == steps.size() - 1) {
            throw new PartException("Next part not found");
        }
        activeStepIndex += 1;
    }

    public void prev() throws PartException {
        if (steps.size() == 1) {
            throw new PartException("No parts found");
        }
        if (activeStepIndex == 0) {
            throw new PartException("Prev part not found");
        }
        activeStepIndex -= 1;
    }

    public Step getActiveStep() throws PartException {
        return steps.get(activeStepIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Part part = (Part) o;

        if (!content.equals(part.content)) {
            return false;
        }
        return section != null ? section.equals(part.section) : part.section == null;

    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + (section != null ? section.hashCode() : 0);
        return result;
    }

    public void first() {
        activeStepIndex = 0;
    }

    public void last() {
        activeStepIndex = steps.size() - 1;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public long getId() {
        return id;
    }
}
