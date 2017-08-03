package com.lzm.knittinghelp2.domain;


import com.lzm.knittinghelp2.R;

public enum Separator {

    COMMA(R.string.separator_comma, ",", ",", true),
    PERIOD(R.string.separator_period, ".", "\\.", true),
    PARENTHESIS_OPEN(R.string.separator_parenthesis, "(", "\\(", false),
    PARENTHESIS_CLOSE(R.string.separator_parenthesis, ")", "\\)", true);


    private int label;
    private String separator;
    private String forSplit;
    private boolean isAfter;

    Separator(final int label, final String separator, final String forSplit, final boolean isAfter) {
        this.label = label;
        this.separator = separator;
        this.forSplit = forSplit;
        this.isAfter = isAfter;
    }

    public int getLabel() {
        return label;
    }

    public String getSeparator() {
        return separator;
    }

    public String getForSplit() {
        return forSplit;
    }

    public boolean isAfter() {
        return isAfter;
    }
}
