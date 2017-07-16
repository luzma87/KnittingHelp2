package com.lzm.knittinghelp2.enums;

import com.lzm.knittinghelp2.R;

public enum KnittingFragment {
    NOTEBOOK(R.string.title_notebook),
    PATTERN(R.string.title_pattern);

    private int titleId;

    KnittingFragment(final int titleId) {
        this.titleId = titleId;
    }

    public int getTitleId() {
        return titleId;
    }
}
