package com.ethangraf.blast.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ethangraf.blast.R;

/**
 * Created by Ethan on 9/10/2015.
 */
public class InvitesActivity extends AppCompatActivity {

    public RecyclerView mInvitesView;
    public InvitesAdapter mInvitesAdapter;
    public RecyclerView.LayoutManager mInvitesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invites);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Sets icon to go back to previous activity.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InvitesActivity.this, MainActivity.class);

                NavUtils.navigateUpTo(InvitesActivity.this, intent);
            }
        });

        getSupportActionBar().setTitle(R.string.invites);

        mInvitesView = (RecyclerView) findViewById(R.id.invites_list_view);
        mInvitesLayoutManager = new LinearLayoutManager(this);
        mInvitesView.setLayoutManager(mInvitesLayoutManager);

        mInvitesAdapter = new InvitesAdapter();
        mInvitesView.setAdapter(mInvitesAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mInvitesView.setHasFixedSize(true);
    }
}
