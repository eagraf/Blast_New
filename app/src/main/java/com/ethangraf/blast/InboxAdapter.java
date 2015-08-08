package com.ethangraf.blast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ethan on 8/8/2015.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    private List<String> mDataSet;

    public InboxAdapter(List<String> data) {
        this.mDataSet = data;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mFirstLine;
        public TextView mSecondLine;
        public ImageView mProfileImage;
        public ViewHolder(RelativeLayout v) {
            super(v);
            mFirstLine = (TextView) v.findViewById(R.id.firstLine);
            mSecondLine = (TextView) v.findViewById(R.id.secondLine);
            mProfileImage = (ImageView) v.findViewById(R.id.icon);
        }
    }

    //Create new views (invoked by the layout manager)
    @Override
    public InboxAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inbox_list_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(InboxAdapter.ViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.mSecondLine.setText(mDataSet.get(i));
        viewHolder.mFirstLine.setText(mDataSet.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
