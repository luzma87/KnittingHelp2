package com.lzm.knittinghelp2;

public class Step {
    private String description;
    private Part part;

    public Step(Part part, String description) {
        this.part = part;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Part getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Step step = (Step) o;

        if (description != null ? !description.equals(step.description) : step.description != null)
            return false;
        return part != null ? part.equals(step.part) : step.part == null;

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (part != null ? part.hashCode() : 0);
        return result;
    }

    public String toString() {
        return description;
    }
}
