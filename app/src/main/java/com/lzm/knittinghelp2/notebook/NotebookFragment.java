package com.lzm.knittinghelp2.notebook;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzm.knittinghelp2.MainActivity;
import com.lzm.knittinghelp2.db.StarterPatterns;
import com.lzm.knittinghelp2.domain.Pattern;
import com.lzm.knittinghelp2.R;
import com.lzm.knittinghelp2.enums.KnittingFragment;

import java.util.ArrayList;
import java.util.List;


public class NotebookFragment extends Fragment {

    List<Pattern> patterns;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    MainActivity context;

    private OnPatternClickedListener listener;

    public NotebookFragment() {
    }

    public static NotebookFragment newInstance() {
        return new NotebookFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.notebook_fragment, container, false);
        patterns = new ArrayList<>();

        recyclerView = view.findViewById(R.id.listView);

        context = (MainActivity) getActivity();
        context.setActiveFragment(KnittingFragment.NOTEBOOK);

        loadData();

        return view;
    }

    private void loadData() {
        initPatterns();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NotebookPatternAdapter(context, patterns);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(
                new PatternClickListener(context, new PatternClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Pattern clickedPattern = patterns.get(position);
                        if (listener != null) {
                            listener.onPatternClicked(clickedPattern);
                        }
                    }
                }));
        recyclerView.setVisibility((patterns.size() == 0) ? View.GONE : View.VISIBLE);
    }

    private void initPatterns() {
        Pattern tmntPattern = StarterPatterns.tmntPattern();
        patterns.add(tmntPattern);
        Pattern splinterPattern = StarterPatterns.splinterPattern();
        patterns.add(splinterPattern);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachAction(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachAction(activity);
        }
    }

    private void onAttachAction(Context context) {
        if (context instanceof OnPatternClickedListener) {
            listener = (OnPatternClickedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPatternClickedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface OnPatternClickedListener {
        void onPatternClicked(Pattern pattern);
    }
}
