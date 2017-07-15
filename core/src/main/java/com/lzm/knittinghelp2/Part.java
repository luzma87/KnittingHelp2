package com.lzm.knittinghelp2;

public class Part {
    private String description;
    private Step step;

    public Part(Step step, String description) {
        this.step = step;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Step getStep() {
        return step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Part part = (Part) o;

        if (description != null ? !description.equals(part.description) : part.description != null)
            return false;
        return step != null ? step.equals(part.step) : part.step == null;

    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (step != null ? step.hashCode() : 0);
        return result;
    }

    public String toString() {
        return description;
    }
}
