package com.lzm.knittinghelp2.pattern;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lzm.knittinghelp2.MainActivity;
import com.lzm.knittinghelp2.Pattern;
import com.lzm.knittinghelp2.PatternInserter;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.Section;
import com.lzm.knittinghelp2.exceptions.PatternException;
import com.lzm.knittinghelp2.exceptions.SectionException;
import com.lzm.knittinghelp2.pattern.components.SectionCardView;

import java.util.ArrayList;
import java.util.List;

public class PatternFragment extends Fragment {
    private static final String PATTERN_ID = "patternId";

    private Pattern pattern;

    private List<SectionCardView> sectionCardViews;

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
            sectionCardViews = new ArrayList<>();
            if (patternId == 1) {
                pattern = PatternInserter.tmntPattern();
            } else if (patternId == 2) {
                pattern = PatternInserter.splinterPattern();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pattern_fragment, container, false);
        LinearLayout layout = view.findViewById(R.id.pattern_linear_layout);
        MainActivity context = (MainActivity) getActivity();

        List<Section> sections = pattern.getSections();

        for (Section section : sections) {
            SectionCardView sectionCardView = new SectionCardView(context, section);
            sectionCardViews.add(sectionCardView);
            layout.addView(sectionCardView);
        }

        start();

        Button prevButton = view.findViewById(R.id.buttonPrev);
        Button nextButton = view.findViewById(R.id.buttonNext);

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pattern.prevPart();
                    setActiveSection();
                } catch (PatternException | SectionException e) {
                    e.printStackTrace();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pattern.nextPart();
                    setActiveSection();
                } catch (PatternException | SectionException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    private void start() {
        pattern.start();
        try {
            setActiveSection();
        } catch (PatternException e) {
            e.printStackTrace();
        }
    }

    private void setActiveSection() throws PatternException {
        for (SectionCardView sectionCardView : sectionCardViews) {
            sectionCardView.setActive(false);
        }

        Section activeSection = pattern.getActiveSection();
        int activeIndex = activeSection.getOrder() - 1;
        SectionCardView activeSectionCardView = sectionCardViews.get(activeIndex);
        activeSectionCardView.setActive(true);
    }

}
