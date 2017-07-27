package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;
import com.lzm.knittinghelp2.exceptions.SectionException;
import com.lzm.knittinghelp2.exceptions.StepException;

import java.util.ArrayList;
import java.util.List;

public class Section {
    private long id;

    private String title;
    private List<Part> parts;
    private Pattern pattern;
    private String content;

    private int activePartIndex;
    private int order;

    public Section(long id, Pattern pattern, String content) {
        this.id = id;
        this.pattern = pattern;
        this.content = content;
        this.activePartIndex = -1;
        parts = new ArrayList<>();
        String[] parts = content.split("\n");
        boolean isFirstLine = true;
        int order = 1;
        for (String part : parts) {
            part = part.trim();
            if (isFirstLine) {
                title = part;
                isFirstLine = false;
            } else {
                Part newPart = new Part(this, part);
                newPart.setOrder(order);
                this.parts.add(newPart);
                order += 1;
            }
        }
    }

    public Section(Pattern pattern, String content) {
        this(0, pattern, content);
    }

    public List<Part> getParts() {
        return parts;
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

    public void start() {
        first();
    }

    public void first() {
        activePartIndex = 0;
        try {
            getActivePart().start();
        } catch (SectionException ignored) {

        }
    }

    public void last() throws SectionException {
        activePartIndex = parts.size() - 1;
        getActivePart().last();
    }

    public void next() throws SectionException, StepException {
        checkInitialized();

        try {
            getActivePart().next();
        } catch (PartException | StepException e) {
            if (activePartIndex == parts.size() - 1) {
                throw new StepException("Next step not found");
            }
            activePartIndex += 1;
            getActivePart().start();
        }
    }

    public void prev() throws StepException, SectionException {
        checkInitialized();

        try {
            getActivePart().prev();
        } catch (PartException | StepException e) {
            if (activePartIndex == 0) {
                throw new StepException("Prev step not found");
            }
            activePartIndex -= 1;
        }
    }

    public Part getActivePart() throws SectionException {
        checkInitialized();

        return parts.get(activePartIndex);
    }

    private void checkInitialized() throws SectionException {
        if (activePartIndex == -1) {
            throw new SectionException("Section not initialized!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Section section = (Section) o;

        if (!title.equals(section.title)) {
            return false;
        }
        if (pattern != null ? !pattern.equals(section.pattern) : section.pattern != null) {
            return false;
        }
        return content != null ? content.equals(section.content) : section.content == null;

    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (pattern != null ? pattern.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    public Step getActiveStep() throws PartException, SectionException {
        return getActivePart().getActiveStep();
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

    public void setSelected(int partOrder, int stepOrder) {
        try {
            activePartIndex = partOrder - 1;
            getActivePart().setSelected(stepOrder);
        } catch (SectionException e) {
            e.printStackTrace();
        }
    }
}
