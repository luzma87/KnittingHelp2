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

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SectionCardView extends CardView {

    boolean isActive = false;
    Context context;

    public SectionCardView(Context context, Section section) {
        super(context);
        this.context = context;
        initialize(section);
    }

    public SectionCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize(null);
    }

    public SectionCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialize(null);
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private void initialize(Section section) {
        String titleText;

        if (section != null) {
            titleText = section.getTitle() + " [" + section.getOrder() + "]";

            int sectionBackgroundColor = getSectionBackgroundColor();
            int sectionBorderColor = getSectionBorderColor();

            int headerBackgroundColor = getHeaderBackgroundColor();
            int headerBorderColor = getHeaderBorderColor();
            int sectionPadding = context.getResources().getDimensionPixelSize(R.dimen.element_section_padding);

            int marginTop = context.getResources().getDimensionPixelSize(R.dimen.element_section_margin_top);
            int marginLeft = context.getResources().getDimensionPixelSize(R.dimen.element_section_margin_left);

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.view_section_titled_card, this, true);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
            params.setMargins(marginLeft, marginTop, marginLeft, marginTop);
            this.setLayoutParams(params);

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

    private int getSectionBackgroundColor() {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_section_active_background);
        }
        return ContextCompat.getColor(context, R.color.element_section_default_background);
    }

    private int getSectionBorderColor() {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_section_active_border);
        }
        return ContextCompat.getColor(context, R.color.element_section_default_border);
    }

    private int getHeaderBackgroundColor() {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_section_active_header_background);
        }
        return ContextCompat.getColor(context, R.color.element_section_default_header_background);
    }

    private int getHeaderBorderColor() {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_section_active_header_border);
        }
        return ContextCompat.getColor(context, R.color.element_section_default_header_border);
    }
}
