package com.lzm.knittinghelp2;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pattern {
    private String name;
    private String content;
    private List<Section> sections;

    private Date creationDate;
    private Date updateDate;

    private int activeSectionIndex;

    public Pattern(String name, String content) {
        this.name = name;
        this.content = content;
        this.creationDate = new Date();
        this.activeSectionIndex = 0;
        sections = new ArrayList<>();

        String[] parts = content.split("\n\n");
        for (String part : parts) {
            Section section = new Section(this, part);
            sections.add(section);
        }
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void start() {

    }

    public Section getActiveSection() {
        return sections.get(activeSectionIndex);
    }

    public Step getActiveStep() {
        return getActiveSection().getActiveStep();
    }
}
