package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Section;
import com.lzm.knittinghelp2.Part;
import com.lzm.knittinghelp2.helpers.Utils;

import java.util.List;

public class SectionCardView extends CardView {

    public SectionCardView(Context context, Section section) {
        super(context);
        initialize(context, section);
    }

    public SectionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, null);
    }

    public SectionCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, null);
    }

    private void initialize(Context context, Section section) {
        String titleText;

        if (section != null) {
            titleText = section.getTitle();

            int sectionBackgroundColor = ContextCompat.getColor(context, R.color.element_section_default_background);
            int sectionBorderColor = ContextCompat.getColor(context, R.color.element_section_default_border);

            int headerBackgroundColor = ContextCompat.getColor(context, R.color.element_section_default_header_background);
            int headerBorderColor = ContextCompat.getColor(context, R.color.element_section_default_header_border);
            int sectionPadding = context.getResources().getDimensionPixelSize(R.dimen.element_section_padding);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_section_titled_card, this, true);

            LinearLayout layout = (LinearLayout) getChildAt(0);

            TextView title = (TextView) layout.getChildAt(0);
            title.setText(titleText);
            title.setTextColor(headerBorderColor);
            Utils.setBackgroundAndBorder(title, headerBackgroundColor, headerBorderColor);

            LinearLayout stepsLayout = (LinearLayout) layout.getChildAt(1);
            stepsLayout.setPadding(sectionPadding, sectionPadding, sectionPadding, sectionPadding);

            Utils.setBackgroundAndBorder(stepsLayout, sectionBackgroundColor, sectionBorderColor);

            List<Part> parts = section.getParts();

            for (Part part : parts) {
                PartFlexboxLayout partFlexboxLayout = new PartFlexboxLayout(context, part);
                stepsLayout.addView(partFlexboxLayout);
            }
        }
    }
}
