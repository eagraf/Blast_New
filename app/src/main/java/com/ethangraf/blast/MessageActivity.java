package com.ethangraf.blast;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class MessageActivity extends AppCompatActivity {
    private RecyclerView mMessageView;
    private RecyclerView.LayoutManager mMessageLayoutManager;
    private MessageAdapter mMessageAdapter;

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
        mToolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(MessageActivity.this);
            }
        });

        // Get the title from the intent and set it as the title for the activity.
        Intent intent = getIntent();
        final String uid = intent.getStringExtra(MainActivity.MESSAGE_VIEW_GROUP_UID);

        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {
                DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
                MessageActivity.this.group = MainActivity.mapper.load(Group.class, uid);
                MessageActivity.this.getSupportActionBar().setTitle(group.getDisplayName());
                return null;
            }
        }.execute();

        mMessageView = (RecyclerView) findViewById(R.id.message_list_view);

        mMessageLayoutManager = new LinearLayoutManager(this);
        mMessageView.setLayoutManager(mMessageLayoutManager);

        // specify an adapter (see also next example)
        mMessageAdapter = new MessageAdapter(Arrays.asList(planets));
        mMessageView.setAdapter(mMessageAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mMessageView.setHasFixedSize(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscribe, menu);
        final MenuItem switchItem = menu.findItem(R.id.subscribe_switch_item);
        final MenuItem textItem = menu.findItem(R.id.subscribe_text_item);

        //Create behaviour for the subscription switch.
        Switch subscribeSwitch = (Switch) switchItem.getActionView().findViewById(R.id.subscription_switch);
        subscribeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled.
                    textItem.setTitle("Subscribed");
                    Toast.makeText(MessageActivity.this, "Subscribed to group.", Toast.LENGTH_LONG).show();
                } else {
                    // The toggle is disabled
                    textItem.setTitle("Subscribe");
                    Toast.makeText(MessageActivity.this, "Unsubscribed from group.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return true;
    }

    public void postMessage(View view) {
        EditText subject = (EditText) findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);
        subject.setText("");
        body.setText("");

        //Post message here

        //Close the keyboard.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

