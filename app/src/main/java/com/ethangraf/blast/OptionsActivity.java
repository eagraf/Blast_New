package com.ethangraf.blast;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.ethangraf.blast.database.Group;
import com.ethangraf.blast.database.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ethan on 8/22/2015.
 */
public class OptionsActivity extends AppCompatActivity implements NameDialogFragment.NameDialogListener {
    private RecyclerView mOptionsView;
    private RecyclerView.LayoutManager mOptionsLayoutManager;
    private OptionsAdapter mOptionsAdapter;

    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Sets icon to go back to previous activity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_clear_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsActivity.this, MessageActivity.class);
                intent.putExtra(MainActivity.MESSAGE_VIEW_GROUP, group);

                NavUtils.navigateUpTo(OptionsActivity.this, intent);
            }
        });

        // Get the title from the intent and set it as the title for the activity.
        Bundle b = getIntent().getExtras();
        group = (Group) b.getParcelable(MessageActivity.OPTIONS_VIEW_GROUP);

        getSupportActionBar().setTitle(R.string.menu_people);

        mOptionsView = (RecyclerView) findViewById(R.id.options_view);

        mOptionsLayoutManager = new LinearLayoutManager(this);
        mOptionsView.setLayoutManager(mOptionsLayoutManager);



        new AsyncTask<Void, Void, Void>() {
            List<User> users = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... params) {
                //DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                for (int i = 0; i < group.getSubscribers().size(); i++) {
                    users.add(MainActivity.mapper.load(User.class, group.getSubscribers().get(i)));
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                // specify an adapter (see also next example)
                mOptionsAdapter = new OptionsAdapter(group, users);
                mOptionsView.setAdapter(mOptionsAdapter);

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                 mOptionsView.setHasFixedSize(true);
            }
        }.execute();
    }

    //Change the name of the group
    public void changeName(View v) {
        // Create an instance of the new group dialog fragment and show it
        NameDialogFragment dialog = new NameDialogFragment();
        dialog.setTitle(getResources().getString(R.string.dialog_rename_group));
        dialog.setEditText(group.getDisplayName());
        dialog.setDefaultText(group.getDisplayName());
        dialog.show(getSupportFragmentManager(), "NewGroupDialogFragment");

        ((TextView) v.findViewById(R.id.name)).setText(group.getDisplayName());
    }

    @Override
    public void onDialogPositiveClick(NameDialogFragment dialog, String groupName) {
        group.setDisplayName(groupName);

        new MainActivity.Save().execute(group);
    }

    @Override
    //Negative click on New Group Dialog
    public void onDialogNegativeClick(NameDialogFragment dialog) {
        // User touched the dialog's negative button
        // Nothing happens
    }

}

