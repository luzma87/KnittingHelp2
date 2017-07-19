package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;
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

    public Section(long id, Pattern pattern, String content) {
        this.id = id;
        this.pattern = pattern;
        this.content = content;
        this.activePartIndex = 0;
        parts = new ArrayList<>();
        String[] parts = content.split("\n");
        boolean isFirstLine = true;
        for (String part : parts) {
            part = part.trim();
            if (isFirstLine) {
                title = part;
                isFirstLine = false;
            } else {
                Part step = new Part(this, part);
                this.parts.add(step);
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

    public void first() {
        activePartIndex = 0;
        getActivePart().first();
    }

    public void last() {
        activePartIndex = parts.size() - 1;
        getActivePart().last();
    }

    public void next() throws StepException {
        try {
            getActivePart().next();
        } catch (PartException e) {
            if (activePartIndex == parts.size() - 1) {
                throw new StepException("Next step not found");
            }
            activePartIndex += 1;
        }
    }

    public void prev() throws StepException {
        try {
            getActivePart().prev();
        } catch (PartException e) {
            if (activePartIndex == 0) {
                throw new StepException("Prev step not found");
            }
            activePartIndex -= 1;
        }
    }

    public Part getActivePart() {
        return parts.get(activePartIndex);
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

    public Step getActiveStep() throws PartException {
        return getActivePart().getActiveStep();
    }
}
