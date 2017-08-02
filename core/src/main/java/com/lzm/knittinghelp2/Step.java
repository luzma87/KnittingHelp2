package com.lzm.knittinghelp2;

import java.util.ArrayList;
import java.util.List;

public class Step {
    private long id;

    private String content;
    private Part part;
    private int order;

    private String separator;

    public Step(long id, Part part, String content) {
        this.id = id;
        this.part = part;
        this.content = content;
        this.separator = ",";
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

    public List<Step> split() {
        List<Step> steps = new ArrayList<>();
        List<String> separatorsForSplit = getSeparatorsForSplit();

        for (String separatorForSplit : separatorsForSplit) {
            String[] parts = content.split(separatorForSplit);
            for (int i = 0; i < parts.length; i++) {
                String stepContent = parts[i].trim();
                if (i < parts.length - 1) {
                    stepContent = stepContent + separator;
                }
                if (i == 0) {
                    this.content = stepContent;
                }
                Step step = new Step(this.part, stepContent);
                step.setOrder(i + 1);
                steps.add(step);
            }
        }
        return steps;
    }

    private List<String> getSeparatorsForSplit() {
        List<String> separatorForSplit = new ArrayList<>();
        separatorForSplit.add(separator);
        if (separator.equals(".")) {
            separatorForSplit.add("\\.");
        } else if (separator.equals("(")) {
            separatorForSplit.add("\\(");
            separatorForSplit.add("\\)");
        }
        return separatorForSplit;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
