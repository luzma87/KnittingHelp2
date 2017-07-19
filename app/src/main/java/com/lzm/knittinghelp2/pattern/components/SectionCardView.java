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
import com.lzm.knittinghelp2.Step;
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
            int sectionPadding = context.getResources().getDimensionPixelSize(R.dimen.element_section_padding);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_section_titled_card, this, true);

            LinearLayout layout = (LinearLayout) getChildAt(0);

            TextView title = (TextView) layout.getChildAt(0);
            title.setText(titleText);

            LinearLayout stepsLayout = (LinearLayout) layout.getChildAt(1);
            stepsLayout.setPadding(sectionPadding, sectionPadding, sectionPadding, sectionPadding);

            Utils.setBackgroundAndBorder(stepsLayout, sectionBackgroundColor, sectionBorderColor);

            List<Step> steps = section.getSteps();

            for (Step step : steps) {
                StepFlexboxLayout stepFlexboxLayout = new StepFlexboxLayout(context, step);
                stepsLayout.addView(stepFlexboxLayout);
            }
        }
    }
}
