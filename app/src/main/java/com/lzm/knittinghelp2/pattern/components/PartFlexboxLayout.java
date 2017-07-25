package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;
import com.lzm.knittinghelp2.Step;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Part;
import com.lzm.knittinghelp2.exceptions.PartException;
import com.lzm.knittinghelp2.helpers.Utils;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.flexbox.FlexWrap.WRAP;

public class PartFlexboxLayout extends FlexboxLayout {

    private boolean isActive = false;
    private Context context;
    private Part part;
    private List<StepTextView> stepTextViews;

    public PartFlexboxLayout(Context context, Part part) {
        super(context);
        initialize(context, part);
    }

    public PartFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, null);
    }

    public PartFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, null);
    }

    public void setActive(boolean active) {
        isActive = active;
        int backgroundColor = getBackgroundColor();
        int borderColor = getBorderColor();

        Utils.setBackgroundAndBorder(this, backgroundColor, borderColor);
        for (StepTextView stepTextView : stepTextViews) {
            stepTextView.setActive(false);
        }
        try {
            setActiveStep();
        } catch (PartException e) {
            e.printStackTrace();
        }
    }

    private void setActiveStep() throws PartException {
        for (StepTextView stepTextView : stepTextViews) {
            stepTextView.setActive(false);
        }

        Step activeStep = part.getActiveStep();
        int activeIndex = activeStep.getOrder() - 1;
        StepTextView stepTextView = stepTextViews.get(activeIndex);
        stepTextView.setActive(true);
    }

    private void initialize(Context context, Part part) {
        this.context = context;
        this.part = part;
        stepTextViews = new ArrayList<>();

        int backgroundColor = getBackgroundColor();
        int borderColor = getBorderColor();
        int padding = context.getResources().getDimensionPixelSize(R.dimen.element_part_padding);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.element_part_margin);

        part.split();

        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);
        this.setLayoutParams(layoutParams);
        Utils.setBackgroundAndBorder(this, backgroundColor, borderColor);

        List<Step> steps = part.getSteps();

        for (Step step : steps) {
            StepTextView stepTextView = new StepTextView(context, step);
            stepTextViews.add(stepTextView);
            this.addView(stepTextView);
        }
        this.setFlexWrap(WRAP);
        this.setPadding(padding, padding, padding, padding);
    }

    private int getBackgroundColor() {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_part_active_background);
        }
        return ContextCompat.getColor(context, R.color.element_part_default_background);
    }

    private int getBorderColor() {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_part_active_border);
        }
        return ContextCompat.getColor(context, R.color.element_part_default_border);
    }
}
