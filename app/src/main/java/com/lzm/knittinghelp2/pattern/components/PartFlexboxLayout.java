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

    private void initialize(Context context, Part part) {
        int backgroundColor = ContextCompat.getColor(context, R.color.element_part_default_background);
        int borderColor = ContextCompat.getColor(context, R.color.element_part_default_border);
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
}
