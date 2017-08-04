package com.lzm.knittinghelp2.counters;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzm.knittinghelp2.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountersFragment extends Fragment {

    @BindView(R.id.counters_text)
    TextView textView;

    int currentCount = 1;

    public CountersFragment() {
    }

    public static CountersFragment newInstance() {
        return new CountersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.counter_fragment, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.counters_increase)
    void counterIncrease() {
        currentCount += 1;
        textView.setText(String.valueOf(currentCount));
    }

    @OnClick(R.id.counters_decrease)
    void counterDecrease() {
        if (currentCount > 1) {
            currentCount -= 1;
            textView.setText(String.valueOf(currentCount));
        }
    }


}
