package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;
import com.lzm.knittinghelp2.domain.Step;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.domain.Part;
import com.lzm.knittinghelp2.domain.exceptions.PartException;
import com.lzm.knittinghelp2.helpers.Utils;
import com.lzm.knittinghelp2.pattern.PatternFragment;

import java.util.ArrayList;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.flexbox.FlexWrap.WRAP;

public class PartFlexboxLayout extends FlexboxLayout {

    private Context context;
    private Part part;
    private List<StepTextView> stepTextViews;
    private int activeBackgroundColor;
    private int defaultBackgroundColor;
    private int activeBorderColor;
    private int defaultBorderColor;

    private PatternFragment patternFragment;

    public PartFlexboxLayout(Context context, PatternFragment patternFragment, Part part) {
        super(context);
        this.patternFragment = patternFragment;
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

    public void setActive() {
        Utils.setBackgroundAndBorder(this, activeBackgroundColor, activeBorderColor);
        try {
            Step activeStep = part.getActiveStep();
            int activeIndex = activeStep.getOrder() - 1;
            stepTextViews.get(activeIndex).setActive();

            if (activeIndex > 0) {
                stepTextViews.get(activeIndex - 1).setInactive();
            }
            if (activeIndex < part.getSteps().size() - 1) {
                stepTextViews.get(activeIndex + 1).setInactive();
            }
        } catch (PartException ignored) {
        }
    }

    public void setInactive() {
        Utils.setBackgroundAndBorder(this, defaultBackgroundColor, defaultBorderColor);
        for (StepTextView stepTextView : stepTextViews) {
            stepTextView.setInactive();
        }
    }

    private void initialize(Context context, Part part) {
        this.context = context;
        this.part = part;

        initializeColors();

        stepTextViews = new ArrayList<>();

        int padding = context.getResources().getDimensionPixelSize(R.dimen.element_part_padding);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.element_part_margin);

        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);
        this.setLayoutParams(layoutParams);
        Utils.setBackgroundAndBorder(this, defaultBackgroundColor, defaultBorderColor);

        List<Step> steps = part.getSteps();

        for (Step step : steps) {
            StepTextView stepTextView = new StepTextView(context, patternFragment, step);
            stepTextViews.add(stepTextView);
            this.addView(stepTextView);
        }
        this.setFlexWrap(WRAP);
        this.setPadding(padding, padding, padding, padding);
    }

    private void initializeColors() {
        activeBackgroundColor = ContextCompat.getColor(context, R.color.element_part_active_background);
        activeBorderColor = ContextCompat.getColor(context, R.color.element_part_active_border);
        defaultBackgroundColor = ContextCompat.getColor(context, R.color.element_part_default_background);
        defaultBorderColor = ContextCompat.getColor(context, R.color.element_part_default_border);
    }
}
