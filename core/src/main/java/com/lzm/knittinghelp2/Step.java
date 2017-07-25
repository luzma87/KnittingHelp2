package com.lzm.knittinghelp2;

public class Step {
    private long id;

    private String content;
    private Part part;
    private int order;

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

    public String toString() {
        return content;
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
}
