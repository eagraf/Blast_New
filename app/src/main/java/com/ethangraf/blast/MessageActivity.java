package com.ethangraf.blast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.ethangraf.blast.gcmservices.MyGcmListenerService;

import java.util.List;

/**
 * Created by Ethan on 8/8/2015.
 */
public class MessageActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private RecyclerView mMessageView;
    private RecyclerView.LayoutManager mMessageLayoutManager;
    private MessageAdapter mMessageAdapter;

    private  ProgressBar spinner;

    private Group group;

    private ImageButton overflow;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    public final static String OPTIONS_VIEW_GROUP = "com.ethangraf.blast.OPTIONSGROUP";
    private BroadcastReceiver mNotificationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //create receiver for notifications to update ui
        mNotificationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("MessageActivity", "received broadcast");
                if(intent.getStringExtra("groupid").equals(group.getGroupID())){
                    mMessageAdapter.refreshFromDatabase();
                }
            }
        };

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

        Intent intent = getIntent();
        group = (Group) intent.getParcelableExtra(MainActivity.MESSAGE_VIEW_GROUP);

        //Show post view if editor or owner
        if(group.getEditors().contains(MainActivity.user.getIdentityID())) {
            findViewById(R.id.post_view).setVisibility(View.VISIBLE);
        }

        getSupportActionBar().setTitle(group.getDisplayName());

        mMessageView = (RecyclerView) findViewById(R.id.message_list_view);

        mMessageLayoutManager = new LinearLayoutManager(this);
        mMessageView.setLayoutManager(mMessageLayoutManager);

        // specify an adapter (see also next example)
        mMessageAdapter = new MessageAdapter(group.getGroupID(),this,mMessageView);
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
            case R.id.options:
                showPopup(findViewById(R.id.options));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_subscribe, menu);
        final MenuItem switchItem = menu.findItem(R.id.subscribe_switch_item);
        final MenuItem textItem = menu.findItem(R.id.subscribe_text_item);

        //Set switch to on if already subscribed
        Switch subscribeSwitch = (Switch) switchItem.getActionView().findViewById(R.id.subscription_switch);
        if(MainActivity.user.getSubscriptions().contains(group.getGroupID())) {
            subscribeSwitch.setChecked(true);
            textItem.setTitle("Subscribed");
        }

        subscribeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            //Create behaviour for the subscription switch.
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    // The toggle is enabled.
                    // Remove the subscription from database
                    group.removeSubscriber(MainActivity.user.getIdentityID());
                    MainActivity.user.removeSubscription(group.getGroupID());

                    textItem.setTitle("Subscribe");
                    Toast.makeText(MessageActivity.this, "Unsubscribed from group.", Toast.LENGTH_LONG).show();
                } else {
                    // The toggle is disabled
                    // Add the subscription from database
                    group.addSubscriber(MainActivity.user.getIdentityID());
                    MainActivity.user.addSubscription(group.getGroupID());

                    textItem.setTitle("Subscribed");
                    Toast.makeText(MessageActivity.this, "Subscribed to group.", Toast.LENGTH_LONG).show();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void postMessage(View view) {
        EditText subject = (EditText) findViewById(R.id.subject);
        EditText body = (EditText) findViewById(R.id.body);

        //Get time and convert to string for DatePosted
        Long datePosted = System.currentTimeMillis();

        //Post message to AWS here
        Message msg = new Message();
        msg.setGroupID(group.getGroupID());
        msg.setSubject(subject.getText().toString());
        msg.setBody(body.getText().toString());
        msg.setDatePosted(datePosted);

        new MainActivity.Save().execute(msg);

        //Clear text views
        subject.setText("");
        body.setText("");

        //Close the keyboard.
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //Show the overflow menu options
    public void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.menu_group, popup.getMenu());
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_group_people:
                Intent intent = new Intent(this, OptionsActivity.class);
                intent.putExtra(OPTIONS_VIEW_GROUP, group);
                startActivity(intent);
                return true;
            case R.id.menu_group_archive:
                return true;
            case R.id.menu_group_delete:
                new AsyncTask<Void,Void,Void>(){
                    @Override
                    protected Void doInBackground(Void... params) {
                        //Delete subscription from each user
                        for(int i = 0; i < group.getSubscribers().size(); i++) {
                            MainActivity.mapper.load(User.class, group.getSubscribers().get(i)).removeSubscription(group.getGroupID());
                        }

                        //Delete messages
                        Message model = new Message();
                        model.setGroupID(group.getGroupID());
                        DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression().withHashKeyValues(model);
                        List<Message> messages = MainActivity.mapper.query(Message.class, queryExpression);
                        MainActivity.mapper.batchDelete(messages);

                        MainActivity.mapper.delete(group);
                        return null;
                    }
                }.execute();
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mNotificationBroadcastReceiver,
                new IntentFilter(MyGcmListenerService.MESSAGE_UPDATE));
    }
    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNotificationBroadcastReceiver);
        super.onPause();
    }
}

