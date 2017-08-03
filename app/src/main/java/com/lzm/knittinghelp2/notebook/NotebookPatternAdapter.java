package com.lzm.knittinghelp2.notebook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lzm.knittinghelp2.MainActivity;
import com.lzm.knittinghelp2.domain.Pattern;
import com.lzm.knittinghelp2.R;

import java.util.ArrayList;
import java.util.List;

public class NotebookPatternAdapter extends RecyclerView.Adapter<NotebookPatternAdapter.ViewHolder> implements SectionIndexer {

    private ArrayList<Integer> mSectionPositions;
    private List<Pattern> patterns;
    private MainActivity context;

    public NotebookPatternAdapter(MainActivity context, List<Pattern> patterns) {
        this.patterns = patterns;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtName;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtName = v.findViewById(R.id.pattern_row_name);
        }
    }

    public void add(int position, Pattern item) {
        patterns.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        patterns.remove(position);
        notifyItemRemoved(position);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotebookPatternAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notebook_row, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Pattern pattern = patterns.get(position);
        holder.txtName.setText(pattern.getName());
//        holder.txtName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                remove(position);
//            }
//        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return patterns.size();
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        List<String> sections = new ArrayList<>(26);
        mSectionPositions = new ArrayList<>(26);
        for (int i = 0, size = patterns.size(); i < size; i++) {
            String section = String.valueOf(patterns.get(i).getName().charAt(0)).toUpperCase();
            if (!sections.contains(section)) {
                sections.add(section);
                mSectionPositions.add(i);
            }
        }
        return sections.toArray(new String[0]);
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return mSectionPositions.get(sectionIndex);
    }
}