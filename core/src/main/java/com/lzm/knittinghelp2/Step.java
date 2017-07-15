package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.PartException;

import java.util.ArrayList;
import java.util.List;

public class Step {
    private String description;
    private Section section;
    private List<Part> parts;
    private String separator;
    private int activePartIndex;

    public Step(Section section, String description) {
        this.description = description;
        this.section = section;

        this.parts = new ArrayList<>();
        this.separator = ",";
        this.activePartIndex = 0;
    }

    public String getDescription() {
        return description;
    }

    public Section getSection() {
        return section;
    }

    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (!description.equals(step.description)) return false;
        return section != null ? section.equals(step.section) : step.section == null;

    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + (section != null ? section.hashCode() : 0);
        return result;
    }

    public void split() {
        String separatorForSplit = getSeparatorForSplit();

        String[] parts = description.split(separatorForSplit);
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (i < parts.length - 1) {
                part = part + separator;
            }
            Part part1 = new Part(this, part);
            this.parts.add(part1);
        }

    }

    private String getSeparatorForSplit() {
        String separatorForSplit = separator;
        if (separator.equals(".")) {
            separatorForSplit = "\\.";
        }
        return separatorForSplit;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void next() throws PartException {
        if (parts.size() == 0) {
            throw new PartException("No parts found");
        }
        if(activePartIndex == parts.size() - 1) {
            throw new PartException("Next part not found");
        }
        activePartIndex += 1;
    }

    public void prev() throws PartException {
        if (parts.size() == 0) {
            throw new PartException("No parts found");
        }
        if(activePartIndex == 0) {
            throw new PartException("Prev part not found");
        }
        activePartIndex -= 1;
    }

    public Part getActivePart() throws PartException {
        if (parts.size() == 0) {
            throw new PartException("No parts found");
        }
        return parts.get(activePartIndex);
    }
}
