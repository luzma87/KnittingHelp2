package com.lzm.knittinghelp2.pattern;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzm.knittinghelp2.MainActivity;
import com.lzm.knittinghelp2.Pattern;
import com.lzm.knittinghelp2.PatternInserter;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Section;
import com.lzm.knittinghelp2.helpers.Utils;
import com.lzm.knittinghelp2.pattern.components.SectionCardView;

import java.util.List;

public class PatternFragment extends Fragment {
    private static final String PATTERN_ID = "patternId";

    private Pattern pattern;
    private MainActivity context;

    public PatternFragment() {
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
            long patternId = getArguments().getLong(PATTERN_ID);
            if (patternId == 1) {
                pattern = PatternInserter.tmntPattern();
            } else if (patternId == 2) {
                pattern = PatternInserter.splinterPattern();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pattern_fragment, container, false);
        LinearLayout layout = view.findViewById(R.id.pattern_linear_layout);
        context = (MainActivity) getActivity();

        List<Section> sections = pattern.getSections();

        for (Section section : sections) {
            SectionCardView sectionCardView = new SectionCardView(context, section);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            int dp15 = Utils.dp2px(context, 15);
            int dp5 = Utils.dp2px(context, 5);

            params.setMargins(dp15, dp5, dp15, dp5);

            sectionCardView.setLayoutParams(params);

            layout.addView(sectionCardView);
        }

        return view;
    }
}
