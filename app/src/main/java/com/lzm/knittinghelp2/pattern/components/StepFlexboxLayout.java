package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;
import com.lzm.knittinghelp2.Part;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Step;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.flexbox.FlexWrap.WRAP;

public class StepFlexboxLayout extends FlexboxLayout {

    public StepFlexboxLayout(Context context, Step step) {
        super(context);
        initialize(context, step);
    }

    public StepFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, null);
    }

    public StepFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, null);
    }

    private void initialize(Context context, Step step) {
        int stepBackgroundColor = ContextCompat.getColor(context, R.color.element_step_default_background);
        int stepBorderColor = ContextCompat.getColor(context, R.color.element_step_default_border);
        int stepPadding = context.getResources().getDimensionPixelSize(R.dimen.element_step_padding);
        int stepMargin = context.getResources().getDimensionPixelSize(R.dimen.element_step_margin);

        step.split();

        LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.setMargins(stepMargin, stepMargin, stepMargin, stepMargin);
        this.setLayoutParams(layoutParams);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(stepBackgroundColor);
        gd.setCornerRadius(5);
        gd.setStroke(1, stepBorderColor);
        this.setBackground(gd);

        List<Part> parts = step.getParts();

        for (Part part : parts) {
            PartTextView textView = new PartTextView(context, part);
            this.addView(textView);
        }
        this.setFlexWrap(WRAP);
        this.setPadding(stepPadding, stepPadding, stepPadding, stepPadding);
    }
}
