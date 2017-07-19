package com.lzm.knittinghelp2;

public class Step {
    private long id;

    private String content;
    private Part part;

    public Step(long id, Part part, String content) {
        this.id = id;
        this.part = part;
        this.content = content;
    }

    public Step(Part part, String content) {
        this(0, part, content);
    }

    public String getContent() {
        return content;
    }

    public Part getPart() {
        return part;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Step step = (Step) o;

        if (content != null ? !content.equals(step.content) : step.content != null) {
            return false;
        }
        return part != null ? part.equals(step.part) : step.part == null;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (part != null ? part.hashCode() : 0);
        return result;
    }

    public String toString() {
        return content;
    }
}
