package com.ethangraf.blast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Ethan on 8/22/2015.
**/
public class OptionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Group group;
    private List<User> users;

    class ViewHolderChangeName extends RecyclerView.ViewHolder {
        public TextView changeName;

        public ViewHolderChangeName(RelativeLayout v) {
            super(v);
            changeName = (TextView) v.findViewById(R.id.name);
        }
    }

    class ViewHolderNotifications extends RecyclerView.ViewHolder {

        public ViewHolderNotifications(RelativeLayout v) {
            super(v);
        }
    }

    class ViewHolderScope extends RecyclerView.ViewHolder {
        public ViewHolderScope(RelativeLayout v) {
            super(v);
        }
    }

    class ViewHolderPerson extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView editor;
        public CheckBox checkBox;

        public ViewHolderPerson(RelativeLayout v) {
            super(v);
            name = (TextView) v.findViewById(R.id.profile_name);
            editor = (TextView) v.findViewById(R.id.editor);
            checkBox = (CheckBox) v.findViewById(R.id.checkBox_editor);
        }

    }

    public OptionsAdapter(Group group, List<User> users) {
        this.group = group;
        this.users = users;
    }

    @Override
    public int getItemViewType(int position) {
        // Return the type of item depending on the position
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case 0:
                View v0 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.options_change_name, viewGroup, false);
                return new ViewHolderChangeName((RelativeLayout) v0);
            case 1:
                View v1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.options_notifications, viewGroup, false);
                return new ViewHolderNotifications((RelativeLayout) v1);
            case 2:
                View v2 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.options_scope, viewGroup, false);
                return new ViewHolderScope((RelativeLayout) v2);
            default:
                View v3 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.options_person_item, viewGroup, false);
                return new ViewHolderPerson((RelativeLayout) v3);
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        switch(i) {
            case 0:
                ((ViewHolderChangeName) viewHolder).changeName.setText(group.getDisplayName());
                break;
            case 1:
                break;
            case 2:
                break;
            default:
                //Set text to user name
                ((ViewHolderPerson) viewHolder).name.setText(users.get(i - 3).getName());

                //If the user is the owner, they are able to see checkboxes to let people edit
                if(group.getOwner().equals(MainActivity.user.getIdentityID())) {
                    if(group.getEditors().contains(users.get(i - 3).getIdentityID())) {
                        ((ViewHolderPerson) viewHolder).checkBox.setChecked(true);
                    }
                    ((ViewHolderPerson) viewHolder).checkBox.setVisibility(View.VISIBLE);
                    ((ViewHolderPerson) viewHolder).editor.setVisibility(View.VISIBLE);
                }

                //Logic for setting editors
                ((ViewHolderPerson) viewHolder).checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean checked) {
                        if (checked){
                            group.addEditor(MainActivity.user.getIdentityID());
                        }
                        else {
                            group.removeEditor(MainActivity.user.getIdentityID());
                        }
                    }
                });
                break;
        }

    }

    @Override
    public int getItemCount() {
        return users.size() + 3;
    }
}