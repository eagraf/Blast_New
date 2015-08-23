package com.ethangraf.blast;

/**
 * Created by Ethan on 8/22/2015.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by Ethan on 8/8/2015.
 */
public class OptionsActivity extends AppCompatActivity {
    private RecyclerView mOptionsView;
    private RecyclerView.LayoutManager mOptionsLayoutManager;
    private MessageAdapter mOptionsAdapter;

    private Group group;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Sets icon to go back to previous activity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_clear_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(OptionsActivity.this);
            }
        });

        // Get the title from the intent and set it as the title for the activity.
        Bundle b = getIntent().getExtras();
        group = (Group) b.getParcelable(MainActivity.MESSAGE_VIEW_GROUP);

        getSupportActionBar().setTitle(R.string.menu_people);

        mOptionsView = (RecyclerView) findViewById(R.id.options_view);

        mOptionsLayoutManager = new LinearLayoutManager(this);
        mOptionsView.setLayoutManager(mOptionsLayoutManager);

        // specify an adapter (see also next example)
        mOptionsAdapter = new MessageAdapter(group.getGroupID());
        mOptionsView.setAdapter(mOptionsAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mOptionsView.setHasFixedSize(true);
    }

}

