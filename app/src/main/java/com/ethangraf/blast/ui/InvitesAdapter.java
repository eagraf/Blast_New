package com.ethangraf.blast.ui;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.ethangraf.blast.R;
import com.ethangraf.blast.database.Group;
import com.ethangraf.blast.database.Message;
import com.ethangraf.blast.database.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 9/10/2015.
 */
public class InvitesAdapter extends RecyclerView.Adapter<InvitesAdapter.ViewHolder> {

    private List<String> invites = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public InvitesAdapter() {
        invites = MainActivity.user.getInvitations();

        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                for(int i = 0; i < invites.size(); i++) {
                    groups.add(MainActivity.mapper.load(Group.class, invites.get(i)));
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
        public TextView group;
        public TextView inviter;
        public ImageView icon;
        public Button acceptButton;
        public Button cancelButton;

        public ViewHolder(RelativeLayout v) {
            super(v);
            group = (TextView) v.findViewById(R.id.group);
            inviter = (TextView) v.findViewById(R.id.inviter);
            icon = (ImageView) v.findViewById(R.id.icon);
            acceptButton = (Button) v.findViewById(R.id.button_accept);
            cancelButton = (Button) v.findViewById(R.id.button_cancel);
        }
    }

    //Create new views (invoked by the layout manager)
    @Override
    public InvitesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invitation_group_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(InvitesAdapter.ViewHolder viewHolder, int i) {

        class CancelOnClickListener implements View.OnClickListener {
            private Group group;

            public CancelOnClickListener(Group group) {
                this.group = group;
            }

            @Override
            public void onClick(View v) {
                MainActivity.user.removeInvitation((group.getGroupID()));
                invites.remove(group.getGroupID());
                notifyDataSetChanged();
            }
        }

        class AcceptOnClickListener implements View.OnClickListener {
            private Group group;

            public AcceptOnClickListener(Group group) {
                this.group = group;
            }

            @Override
            public void onClick(View v) {
                MainActivity.user.addInvitation((group.getGroupID()));
                invites.remove(group.getGroupID());
                notifyDataSetChanged();
            }
        }

        viewHolder.group.setText(groups.get(i).getDisplayName());
        viewHolder.inviter.setText("Invited by " + groups.get(i).getOwnerName());

        viewHolder.acceptButton.setOnClickListener(new AcceptOnClickListener(groups.get(i)));
        viewHolder.cancelButton.setOnClickListener(new CancelOnClickListener(groups.get(i)));
    }

    @Override
    public int getItemCount() {
        return groups.size();
    }
}
