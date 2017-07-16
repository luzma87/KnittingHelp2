package com.lzm.knittinghelp2.pattern;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.knittinghelp2.R;

public class PatternFragment extends Fragment {
    private static final String PATTERN_ID = "patternId";

    private long patternId;

    public PatternFragment() {
        // Required empty public constructor
    }

    public static PatternFragment newInstance(long patternId) {
        PatternFragment fragment = new PatternFragment();
        Bundle args = new Bundle();
        args.putLong(PATTERN_ID, patternId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patternId = getArguments().getLong(PATTERN_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pattern_fragment, container, false);
    }
}
