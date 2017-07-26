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
import com.lzm.knittinghelp2.exceptions.SectionException;
import com.lzm.knittinghelp2.helpers.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SectionCardView extends CardView {

    private Context context;
    private LinearLayout stepsLayout;
    private TextView title;
    private Section section;
    private List<PartFlexboxLayout> partFlexboxLayouts;

    private int defaultSectionBackgroundColor;
    private int activeSectionBackgroundColor;
    private int defaultSectionBorderColor;
    private int activeSectionBorderColor;

    private int defaultHeaderBackgroundColor;
    private int activeHeaderBackgroundColor;
    private int defaultHeaderBorderColor;
    private int activeHeaderBorderColor;

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

    public void setActive() {
        Utils.setBackgroundAndBorder(stepsLayout, activeSectionBackgroundColor, activeSectionBorderColor);
        title.setTextColor(activeHeaderBorderColor);
        Utils.setBackgroundAndBorder(title, activeHeaderBackgroundColor, activeHeaderBorderColor);

        try {
            Part activePart = section.getActivePart();
            int activeIndex = activePart.getOrder() - 1;
            partFlexboxLayouts.get(activeIndex).setActive();

            if (activeIndex > 0) {
                partFlexboxLayouts.get(activeIndex - 1).setInactive();
            }
            if (activeIndex < section.getParts().size() - 1) {
                partFlexboxLayouts.get(activeIndex + 1).setInactive();
            }
        } catch (SectionException e) {
            e.printStackTrace();
        }
    }

    public void setInactive() {
        Utils.setBackgroundAndBorder(stepsLayout, defaultSectionBackgroundColor, defaultSectionBorderColor);
        title.setTextColor(defaultHeaderBorderColor);
        Utils.setBackgroundAndBorder(title, defaultHeaderBackgroundColor, defaultHeaderBorderColor);

        partFlexboxLayouts.get(0).setInactive();
        partFlexboxLayouts.get(section.getParts().size() - 1).setInactive();
    }

    private void initialize(Context context, Section section) {
        this.context = context;
        this.section = section;

        initializeColors();

        partFlexboxLayouts = new ArrayList<>();

        String titleText;

        titleText = "[" + section.getOrder() + "] " + section.getTitle();

        int sectionPadding = context.getResources().getDimensionPixelSize(R.dimen.element_section_padding);

        int marginTop = context.getResources().getDimensionPixelSize(R.dimen.element_section_margin_top);
        int marginLeft = context.getResources().getDimensionPixelSize(R.dimen.element_section_margin_left);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_section_titled_card, this, true);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        params.setMargins(marginLeft, marginTop, marginLeft, marginTop);
        this.setLayoutParams(params);

        LinearLayout layout = (LinearLayout) getChildAt(0);

        title = (TextView) layout.getChildAt(0);
        title.setText(titleText);
        title.setTextColor(defaultHeaderBorderColor);
        Utils.setBackgroundAndBorder(title, defaultHeaderBackgroundColor, defaultHeaderBorderColor);

        stepsLayout = (LinearLayout) layout.getChildAt(1);
        stepsLayout.setPadding(sectionPadding, sectionPadding, sectionPadding, sectionPadding);

        Utils.setBackgroundAndBorder(stepsLayout, defaultSectionBackgroundColor, defaultSectionBorderColor);

        List<Part> parts = section.getParts();

        for (Part part : parts) {
            PartFlexboxLayout partFlexboxLayout = new PartFlexboxLayout(context, part);
            partFlexboxLayouts.add(partFlexboxLayout);
            stepsLayout.addView(partFlexboxLayout);
        }
    }

    private void initializeColors() {
        activeSectionBackgroundColor = ContextCompat.getColor(context, R.color.element_section_active_background);
        defaultSectionBackgroundColor = ContextCompat.getColor(context, R.color.element_section_default_background);
        activeSectionBorderColor = ContextCompat.getColor(context, R.color.element_section_active_border);
        defaultSectionBorderColor = ContextCompat.getColor(context, R.color.element_section_default_border);

        activeHeaderBackgroundColor = ContextCompat.getColor(context, R.color.element_section_active_header_background);
        defaultHeaderBackgroundColor = ContextCompat.getColor(context, R.color.element_section_default_header_background);
        activeHeaderBorderColor = ContextCompat.getColor(context, R.color.element_section_active_header_border);
        defaultHeaderBorderColor = ContextCompat.getColor(context, R.color.element_section_default_header_border);
    }
}
