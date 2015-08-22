package com.ethangraf.blast;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 8/8/2015.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    private List<Group> mDataSet;
    private List<String> subscriptions;

    public GroupAdapter() {
        mDataSet = new ArrayList<>();
        subscriptions = MainActivity.user.getSubscriptions();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                //DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                for (int i = 0; i < subscriptions.size(); i++) {
                    mDataSet.add(MainActivity.mapper.load(Group.class, subscriptions.get(i)));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                notifyDataSetChanged();
            }
        }.execute();
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
    public GroupAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.group_list_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(GroupAdapter.ViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        viewHolder.mFirstLine.setText(mDataSet.get(i).getDisplayName());
        viewHolder.mSecondLine.setText(mDataSet.get(i).getGroupID());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }


}
