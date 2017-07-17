package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Section;

public class SectionCardView extends CardView {

    String titleText = "TITLE";
    String contentText = "CONTENT";
    int titleTextColor;
    int contentTextColor;

    Section section;

    public SectionCardView(Context context, Section section) {
        super(context);
        this.section = section;
        initialize(context);
    }

    public SectionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public SectionCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {

        if (section != null) {
            titleText = section.getTitle();
            contentText = section.getContent();
        }

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_section_titled_card, this, true);

        LinearLayout layout = (LinearLayout) getChildAt(0);

        TextView title = (TextView) layout.getChildAt(0);
        title.setText(titleText);

        TextView content = (TextView) layout.getChildAt(1);
        content.setText(contentText);

        LinearLayout stepsLayout = (LinearLayout) layout.getChildAt(2);

    }
}
