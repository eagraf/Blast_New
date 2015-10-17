package com.ethangraf.blast.ui;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ethangraf.blast.R;
import com.ethangraf.blast.database.model.Group;
import com.ethangraf.blast.database.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 8/29/2015.
 */
public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.ViewHolder> {

    private Group group;

    private List<String> contactIds;
    private List<User> contacts = new ArrayList<>();

    private List<String> invited = new ArrayList<>();

    public InviteAdapter(Group group) {
        this.group = group;
        this.contactIds = MainActivity.user.getContacts();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... v) {
                for(int i = 0; i < contactIds.size(); i++) {
                    if(!InviteAdapter.this.group.getSubscribers().contains(contactIds.get(i))) {
                        contacts.add(MainActivity.mapper.load(User.class, contactIds.get(i)));
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void v) {
                InviteAdapter.this.notifyDataSetChanged();
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
        public CheckBox mCheckBox;

        public ViewHolder(RelativeLayout v) {
            super(v);
            mName = (TextView) v.findViewById(R.id.profile_name);
            mProfileImage = (ImageView) v.findViewById(R.id.profile_icon);
            mCheckBox = (CheckBox) v.findViewById(R.id.checkBox);
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

        class InviteOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

            private int index;

            public InviteOnCheckedChangeListener(int i) {
                index = i;
            }

            @Override
            public void onCheckedChanged(CompoundButton button, boolean checked) {
                if (checked) {
                    InviteAdapter.this.invited.add(contacts.get(index).getId());
                } else {
                    InviteAdapter.this.invited.remove(contacts.get(index).getId());
                }
            }
        }

        ((ViewHolder) viewHolder).mCheckBox.setOnCheckedChangeListener(new InviteOnCheckedChangeListener(i));
    }

    public List<String> getInvited() {
        return invited;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

}

