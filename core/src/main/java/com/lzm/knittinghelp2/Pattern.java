package com.lzm.knittinghelp2;

import com.lzm.knittinghelp2.exceptions.SectionException;
import com.lzm.knittinghelp2.exceptions.StepException;

import java.util.ArrayList;
import java.util.List;

public class Pattern implements Comparable<Pattern> {
    private long id;

    private String name;
    private String content;
    private List<Section> sections;

    private int activeSectionIndex;

    public Pattern(long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.activeSectionIndex = 0;
        sections = new ArrayList<>();

        String[] parts = content.split("\n\n");
        for (String part : parts) {
            Section section = new Section(this, part);
            sections.add(section);
        }
    }

    public Pattern(String name, String content) {
        this(0, name, content);
    }

    public String getName() {
        return name;
    }

    public List<Section> getSections() {
        return sections;
    }

    public String getContent() {
        return content;
    }

    public void first() {
        activeSectionIndex = 0;
    }

    public Section getActiveSection() {
        return sections.get(activeSectionIndex);
    }

    public Part getActivePart() {
        return getActiveSection().getActivePart();
    }

    public void nextPart() throws SectionException {
        try {
            getActiveSection().next();
        } catch (StepException e) {
            nextSection();
        }
    }

    public void prevPart() throws SectionException {
        try {
            getActiveSection().prev();
        } catch (StepException e) {
            prevSection();
            getActiveSection().last();
        }
    }

    public void nextSection() throws SectionException {
        if (activeSectionIndex == sections.size() - 1) {
            throw new SectionException("Next section not found");
        }
        activeSectionIndex += 1;
    }

    public void prevSection() throws SectionException {
        if (activeSectionIndex == 0) {
            throw new SectionException("Prev section not found");
        }
        activeSectionIndex -= 1;
    }

    @Override
    public int compareTo(Pattern pattern) {
        return this.getName().compareTo(pattern.getName());
    }

    public long getId() {
        return id;
    }
}
