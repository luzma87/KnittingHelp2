package com.lzm.knittinghelp2.domain;

import com.lzm.knittinghelp2.domain.exceptions.PartException;
import com.lzm.knittinghelp2.domain.exceptions.PatternException;
import com.lzm.knittinghelp2.domain.exceptions.SectionException;
import com.lzm.knittinghelp2.domain.exceptions.StepException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pattern implements Comparable<Pattern> {
    private long id;

    private String name;
    private String content;
    private List<Section> sections;

    private int activeSectionIndex;
    private Date creationDate;
    private Date updateDate;

    public Pattern(long id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.activeSectionIndex = -1;
        sections = new ArrayList<>();

        String[] parts = content.split("\n\n");
        int order = 1;
        for (String part : parts) {
            Section section = new Section(this, part);
            section.setOrder(order);
            sections.add(section);
            order += 1;
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

    public void start() {
        first();
    }

    public void first() {
        activeSectionIndex = 0;
        try {
            getActiveSection().start();
        } catch (PatternException ignored) {
        }
    }

    public Section getActiveSection() throws PatternException {
        checkInitialized();
        return sections.get(activeSectionIndex);
    }

    public Step getActiveStep() throws PartException, SectionException, PatternException {
        checkInitialized();
        return getActiveSection().getActiveStep();
    }

    public void nextPart() throws SectionException, PatternException {
        checkInitialized();

        try {
            getActiveSection().next();
        } catch (StepException e) {
            nextSection();
        }
    }

    public void prevPart() throws SectionException, PatternException {
        checkInitialized();

        try {
            getActiveSection().prev();
        } catch (StepException e) {
            prevSection();
            getActiveSection().last();
        }
    }

    public void nextSection() throws SectionException, PatternException {
        if (activeSectionIndex == sections.size() - 1) {
            throw new SectionException("Next section not found");
        }
        activeSectionIndex += 1;
        getActiveSection().start();
    }

    public void prevSection() throws SectionException, PatternException {
        checkInitialized();

        if (activeSectionIndex == 0) {
            throw new SectionException("Prev section not found");
        }
        activeSectionIndex -= 1;
    }

    public long getId() {
        return id;
    }

    public void setSelected(int sectionOrder, int partOrder, int stepOrder) {
        try {
            activeSectionIndex = sectionOrder - 1;
            getActiveSection().setSelected(partOrder, stepOrder);
        } catch (PatternException e) {
            e.printStackTrace();
        }
    }

    private void checkInitialized() throws PatternException {
        if (activeSectionIndex == -1) {
            throw new PatternException("Pattern not initialized!");
        }
    }

    @Override
    public int compareTo(Pattern pattern) {
        return this.getName().compareTo(pattern.getName());
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
