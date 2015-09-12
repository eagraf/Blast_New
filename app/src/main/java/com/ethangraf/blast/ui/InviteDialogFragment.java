package com.ethangraf.blast.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ethangraf.blast.R;
import com.ethangraf.blast.database.Group;

/**
 * Created by Ethan on 8/29/2015.
 */
public class InviteDialogFragment extends DialogFragment {

    public RecyclerView mInviteView;
    public InviteAdapter mInviteAdapter;
    public RecyclerView.LayoutManager mInviteLayoutManager;

    private Group group;

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface InviteDialogListener {
        public void onDialogPositiveClick(InviteDialogFragment dialog);
        public void onDialogNegativeClick(InviteDialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    InviteDialogListener mListener;

    public void setGroup(Group group) {
        this.group = group;
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (InviteDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement InviteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_invite, null);

        mInviteView = (RecyclerView) view.findViewById(R.id.group_list_view);
        mInviteLayoutManager = new LinearLayoutManager(getActivity());
        mInviteView.setLayoutManager(mInviteLayoutManager);

        mInviteAdapter = new InviteAdapter(group);
        mInviteView.setAdapter(mInviteAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mInviteView.setHasFixedSize(true);

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view);
        builder.setTitle(getResources().getString(R.string.invite)).setPositiveButton(R.string.dialog_done, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                mListener.onDialogPositiveClick(InviteDialogFragment.this);
            }
        }).setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                mListener.onDialogNegativeClick(InviteDialogFragment.this);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
