package com.lzm.knittinghelp2.pattern;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.lzm.knittinghelp2.MainActivity;
import com.lzm.knittinghelp2.domain.Part;
import com.lzm.knittinghelp2.domain.Pattern;
import com.lzm.knittinghelp2.PatternInserter;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.domain.Section;
import com.lzm.knittinghelp2.domain.Step;
import com.lzm.knittinghelp2.enums.KnittingFragment;
import com.lzm.knittinghelp2.domain.exceptions.PatternException;
import com.lzm.knittinghelp2.domain.exceptions.SectionException;
import com.lzm.knittinghelp2.pattern.components.SectionCardView;

import java.util.ArrayList;
import java.util.List;

public class PatternFragment extends Fragment {
    private static final String PATTERN_ID = "patternId";

    private Pattern pattern;

    private List<SectionCardView> sectionCardViews;

    private int activeSectionIndex;

    public PatternFragment() {
    }

    public static PatternFragment newInstance(long patternId) {
        PatternFragment fragment = new PatternFragment();
        Bundle args = new Bundle();
        args.putLong(PATTERN_ID, patternId);
        fragment.setArguments(args);
        return fragment;
    }

    public void onStepLongClicked(Step step) {
        try {
            Part part = step.getPart();
            Section section = part.getSection();
            pattern.setSelected(section.getOrder(), part.getOrder(), step.getOrder());
            changeActiveSection();
        } catch (PatternException e) {
            e.printStackTrace();
        }
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
        context.setActiveFragment(KnittingFragment.PATTERN);

        List<Section> sections = pattern.getSections();

        for (Section section : sections) {
            SectionCardView sectionCardView = new SectionCardView(context, this, section);
            sectionCardViews.add(sectionCardView);
            layout.addView(sectionCardView);
        }

        start();

        ImageButton prevButton = view.findViewById(R.id.buttonPrev);
        ImageButton prevSectionButton = view.findViewById(R.id.buttonPrevSection);
        ImageButton nextButton = view.findViewById(R.id.buttonNext);
        ImageButton nextSectionButton = view.findViewById(R.id.buttonNextSection);

        prevSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pattern.prevSection();
                    pattern.getActiveSection().start();
                    changeActiveSection();
                } catch (PatternException | SectionException ignored) {
                }
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pattern.prevPart();
                    changeActiveSection();
                } catch (PatternException | SectionException ignored) {
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pattern.nextPart();
                    changeActiveSection();
                } catch (PatternException | SectionException ignored) {
                }
            }
        });
        nextSectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    pattern.nextSection();
                    changeActiveSection();
                } catch (PatternException | SectionException ignored) {
                }
            }
        });

        return view;
    }

    private void changeActiveSection() throws PatternException {
        int newSectionIndex = pattern.getActiveSection().getOrder();

        if (newSectionIndex > 0 || newSectionIndex != activeSectionIndex) {
            setInactiveSection();
        }
        activeSectionIndex = newSectionIndex;
        setActiveSection();
    }

    private void start() {
        pattern.start();
        try {
            activeSectionIndex = pattern.getActiveSection().getOrder();
            setActiveSection();
        } catch (PatternException ignored) {
        }
    }

    private void setActiveSection() throws PatternException {
        SectionCardView activeSectionCardView = sectionCardViews.get(activeSectionIndex - 1);
        activeSectionCardView.setActive();
    }

    private void setInactiveSection() throws PatternException {
        SectionCardView activeSectionCardView = sectionCardViews.get(activeSectionIndex - 1);
        activeSectionCardView.setInactive();
    }

}
