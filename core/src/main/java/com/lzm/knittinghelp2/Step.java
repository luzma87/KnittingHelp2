package com.lzm.knittinghelp2;

public class Step {
    private String description;
    private Section section;

    public Step(Section section, String description) {
        this.description = description;
        this.section = section;
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
}
