package com.ethangraf.blast;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ethangraf.blast.database.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 8/29/2015.
 */
public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {

    private List<String> contactIds;
    private List<User> contacts = new ArrayList<>();

    public InviteAdapter() {

        this.contactIds = MainActivity.user.getContacts();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... v) {
                for(int i = 0; i < contactIds.size(); i++) {
                    contacts.add(MainActivity.mapper.load(User.class, contactIds.get(i)));
                }
                return null;
            }
        }.execute();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mName;
        public ImageView mProfileImage;

        public ViewHolder(RelativeLayout v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.profile_name);
            mProfileImage = (ImageView) v.findViewById(R.id.profile_icon);
        }
    }

    //Create new views (invoked by the layout manager)
    @Override
    public InviteAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invitation_person_item, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(InviteAdapter.ViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        viewHolder.mName.setText(contacts.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }


}
