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

    private Context context;
    private int activeBorderColor;
    private int defaultBorderColor;
    private int activeBackgroundColor;
    private int defaultBackgroundColor;

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

    public void setActive() {
        Utils.setBackgroundAndBorder(this, activeBackgroundColor, activeBorderColor);
    }

    public void setInactive() {
        Utils.setBackgroundAndBorder(this, defaultBackgroundColor, defaultBorderColor);
    }

    private void initialize(Context context, Step step) {
        this.context = context;

        initializeColors();

        String contentText = "CONTENT";

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

        Utils.setBackgroundAndBorder(this, defaultBackgroundColor, defaultBorderColor);

        ViewGroup.LayoutParams lp = this.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(1.0f);
        }

        this.setLayoutParams(lp);
    }

    private void initializeColors() {
        activeBorderColor = ContextCompat.getColor(context, R.color.element_step_active_border);
        activeBackgroundColor = ContextCompat.getColor(context, R.color.element_step_active_background);
        defaultBorderColor = ContextCompat.getColor(context, R.color.element_step_default_border);
        defaultBackgroundColor = ContextCompat.getColor(context, R.color.element_step_default_background);
    }
}
