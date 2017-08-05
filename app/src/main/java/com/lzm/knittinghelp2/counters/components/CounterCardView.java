package com.lzm.knittinghelp2.counters.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzm.knittinghelp2.R;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CounterCardView extends CardView {

    String titleHint = "Counter name";
    int titleBackground;
    int titleColor;

    @BindView(R.id.counter_text)
    TextView counterCount;

    @BindView(R.id.counter_title)
    EditText counterTitle;

    @BindView(R.id.counter_header)
    LinearLayout counterHeader;

    @BindString(R.string.counter_name_placeholder)
    String counterName;

    @BindString(R.string.counter_text)
    String counterText;

    @BindString(R.string.counter_infinity)
    String counterInfinity;

    int currentCount = 0;
    int counterMax = 0;

    Context context;

    public CounterCardView(Context context) {
        super(context);
        initialize(context, null);
    }

    public CounterCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    public CounterCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.counter_card, this, true);
        ButterKnife.bind(this, view);

        initializeAttributes(context, attrs);

        if (titleHint == null || titleHint.equals("")) {
            titleHint = counterName;
        }

        counterHeader.setBackgroundColor(titleBackground);
        counterTitle.setHint(titleHint);
        counterTitle.setTextColor(titleColor);
        counterTitle.setHintTextColor(titleColor);

        counterCount.setText(String.valueOf(currentCount));
    }

    private void initializeAttributes(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CounterCardView, 0, 0);
        try {
            titleHint = a.getString(R.styleable.CounterCardView_titleHint);
            titleBackground = a.getColor(R.styleable.CounterCardView_titleBackground,
                    ContextCompat.getColor(context, R.color.counter_header));
            titleColor = a.getColor(R.styleable.CounterCardView_titleColor,
                    ContextCompat.getColor(context, R.color.text_primary));
        } finally {
            a.recycle();
        }
    }

    @OnClick(R.id.counter_increase)
    void counterIncrease() {
        currentCount += 1;
        counterCount.setText(String.valueOf(currentCount));
    }

    @OnClick(R.id.counter_decrease)
    void counterDecrease() {
        if (currentCount > 0) {
            currentCount -= 1;
            counterCount.setText(String.valueOf(currentCount));
        }
    }
}
