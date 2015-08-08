package com.ethangraf.blast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class SubscriptionActivity extends AppCompatActivity {
    private RecyclerView mSubscriptionListView;
    private RecyclerView.LayoutManager mMessageListLayoutManager;
    private SubscriptionAdapter mSubscriptionAdapter;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        // Get the title from the intent and set it as the title for the activity.
        Intent intent = getIntent();
        getSupportActionBar().setTitle(R.string.find_groups);

        //Sets icon to go back to previous activity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(SubscriptionActivity.this);
            }
        });

        mSubscriptionListView = (RecyclerView) findViewById(R.id.subscription_list_view);

        mMessageListLayoutManager = new LinearLayoutManager(this);
        mSubscriptionListView.setLayoutManager(mMessageListLayoutManager);

        // specify an adapter (see also next example)
        mSubscriptionAdapter = new SubscriptionAdapter(Arrays.asList(planets));
        mSubscriptionListView.setAdapter(mSubscriptionAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mSubscriptionListView.setHasFixedSize(true);


    }

}
