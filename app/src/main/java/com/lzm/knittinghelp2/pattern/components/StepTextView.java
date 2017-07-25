package com.lzm.knittinghelp2.pattern.components;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.lzm.knittinghelp2.Step;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.helpers.Utils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class StepTextView extends android.support.v7.widget.AppCompatTextView {

    boolean isActive = false;
    Context context;
    Step step;

    public StepTextView(Context context, Step step) {
        super(context);
        initialize(context, step);
    }

    public StepTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, null);
    }

    public StepTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, null);
    }

    public void setActive(boolean active) {
        isActive = active;
        int backgroundColor = getBackgroundColor(context);
        int borderColor = getBorderColor(context);

        Utils.setBackgroundAndBorder(this, backgroundColor, borderColor);
    }

    private void initialize(Context context, Step step) {
        this.context = context;
        this.step = step;

        String contentText = "CONTENT";

        int backgroundColor = getBackgroundColor(context);
        int borderColor = getBorderColor(context);
        int padding = context.getResources().getDimensionPixelSize(R.dimen.element_step_padding);
        int margin = context.getResources().getDimensionPixelSize(R.dimen.element_step_margin);

        if (step != null) {
            contentText = step.getContent();
        }

        this.setText(contentText);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        layoutParams.setMargins(margin, margin, margin, margin);

        this.setLayoutParams(layoutParams);
        this.setPadding(padding, padding, padding, padding);

        Utils.setBackgroundAndBorder(this, backgroundColor, borderColor);

        ViewGroup.LayoutParams lp = this.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }

        this.setLayoutParams(lp);
    }

    private int getBorderColor(Context context) {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_step_active_border);
        }
        return ContextCompat.getColor(context, R.color.element_step_default_border);
    }

    private int getBackgroundColor(Context context) {
        if (isActive) {
            return ContextCompat.getColor(context, R.color.element_step_active_background);
        }
        return ContextCompat.getColor(context, R.color.element_step_default_background);
    }
}
