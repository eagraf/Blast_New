package com.ethangraf.blast.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.ethangraf.blast.R;
import com.ethangraf.blast.database.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 8/8/2015.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private final Context context;
    private final String uid;
    private final RecyclerView messageView;
    private List<Message> mDataSet = new ArrayList<>();

    class Refresh extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            Message model = new Message();
            model.setGroupID(uid);
            DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression().withHashKeyValues(model);
            mDataSet = MainActivity.mapper.query(Message.class, queryExpression);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            messageView.scrollToPosition(getItemCount()-1);
            notifyDataSetChanged();
        }
    };

    public MessageAdapter(final String uid, Context context, RecyclerView messageView) {
        this.context = context;
        this.uid = uid;
        this.messageView = messageView;
        refreshFromDatabase();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mFirstLine;
        public TextView mSecondLine;
        public TextView mDate;
        public ImageView mProfileImage;
        public ViewHolder(RelativeLayout v) {
            super(v);
            mFirstLine = (TextView) v.findViewById(R.id.firstLine);
            mSecondLine = (TextView) v.findViewById(R.id.secondLine);
            mDate = (TextView) v.findViewById(R.id.date);
            mProfileImage = (ImageView) v.findViewById(R.id.icon);
        }
    }

    //Create new views (invoked by the layout manager)
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_list_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.mSecondLine.setText(mDataSet.get(i).getBody());
        viewHolder.mFirstLine.setText(mDataSet.get(i).getSubject());


        viewHolder.mDate.setText(DateUtils.formatDateTime(context, mDataSet.get(i).getDatePosted(),
                DateUtils.FORMAT_SHOW_TIME|DateUtils.FORMAT_SHOW_DATE|DateUtils.FORMAT_ABBREV_MONTH));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void refreshFromDatabase(){
        Log.i("MessageAdapter", "refreshing");
        new Refresh().execute();
    }
}
