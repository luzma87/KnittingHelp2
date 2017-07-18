package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.lzm.knittinghelp2.Part;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Section;
import com.lzm.knittinghelp2.Step;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.flexbox.FlexWrap.WRAP;

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

            int stepBackgroundColor = ContextCompat.getColor(context, R.color.element_step_default_background);
            int stepBorderColor = ContextCompat.getColor(context, R.color.element_step_default_border);
            int stepPadding = context.getResources().getDimensionPixelSize(R.dimen.element_step_padding);
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
            GradientDrawable gd1 = new GradientDrawable();
            gd1.setColor(sectionBackgroundColor);
            gd1.setCornerRadius(5);
            gd1.setStroke(1, sectionBorderColor);
            stepsLayout.setBackground(gd1);

            List<Step> steps = section.getSteps();

            for (Step step : steps) {
                step.split();

                FlexboxLayout stepLayout = new FlexboxLayout(context);
                stepLayout.setLayoutParams(new LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                GradientDrawable gd = new GradientDrawable();
                gd.setColor(stepBackgroundColor);
                gd.setCornerRadius(5);
                gd.setStroke(1, stepBorderColor);
                stepLayout.setBackground(gd);

                List<Part> parts = step.getParts();

                for (Part part : parts) {
                    PartTextView textView = new PartTextView(context, part);
                    stepLayout.addView(textView);
                }
                stepLayout.setFlexWrap(WRAP);
                stepLayout.setPadding(stepPadding, stepPadding, stepPadding, stepPadding);

                stepsLayout.addView(stepLayout);
            }
        }
    }
}
