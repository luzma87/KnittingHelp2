package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.google.android.flexbox.FlexboxLayout;
import com.lzm.knittinghelp2.Step;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Part;
import com.lzm.knittinghelp2.helpers.Utils;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.google.android.flexbox.FlexWrap.WRAP;

public class PartFlexboxLayout extends FlexboxLayout {

    boolean isActive = false;
    Context context;

    public PartFlexboxLayout(Context context, Part part) {
        super(context);
        this.context = context;
        initialize(part);
    }

    public PartFlexboxLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize(null);
    }

    public PartFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initialize(null);
    }

    public void setActive(boolean active) {
        isActive = active;
        int backgroundColor = getBackgroundColor();
        int borderColor = getBorderColor();

        Utils.setBackgroundAndBorder(this, backgroundColor, borderColor);
    }

    private void initialize(Part part) {
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
            StepTextView textView = new StepTextView(context, step);
            this.addView(textView);
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
