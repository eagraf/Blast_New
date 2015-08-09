package com.ethangraf.blast;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.UUID;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        NewGroupDialogFragment.NewGroupDialogListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private BlastFragment blastFragment;
    private GroupFragment groupFragment;

    public static DynamoDBMapper mapper;

    public final static String MESSAGE_VIEW_GROUP_UID = "com.ethangraf.blast.MESSAGE_VIEW_GROUP_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-1:f08cf8f2-5a11-4756-a62a-97d65306a831", // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient(credentialsProvider);
        mapper = new DynamoDBMapper(ddbClient);


        //Initialize some navigation drawer stuff.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_layout);
        mDrawerView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerView.setNavigationItemSelectedListener(this);

        //Set the navigation drawer information.
        ((TextView) findViewById(R.id.navigation_header).findViewById(R.id.name)).setText("Name");
        ((TextView) findViewById(R.id.navigation_header).findViewById(R.id.email)).setText("Email");

        //Initialize the fragment manager.
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        //Various fragments used by main activity.
        blastFragment = new BlastFragment();
        groupFragment = new GroupFragment();

        //Start in the inbox fragment.
        fragmentTransaction.add(R.id.main_content_frame, blastFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    //Handle click events on navigation drawer items.
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.navigation_inbox_item:
                //Open the inbox fragment.
                fragmentTransaction.replace(R.id.main_content_frame, blastFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_event_item:
                break;
            case R.id.navigation_reminder_item:
                break;
            case R.id.navigation_group_item:
                fragmentTransaction.replace(R.id.main_content_frame, groupFragment);
                fragmentTransaction.commit();
                break;
            case R.id.navigation_contact_item:
                break;
            case R.id.navigation_settings_item:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.navigation_help_item:
                break;
            default:
                return true;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    //Create a new group
    public void newGroup(View v) {
        // Create an instance of the new group dialog fragment and show it
        NewGroupDialogFragment dialog = new NewGroupDialogFragment();
        dialog.setTitle(getResources().getString(R.string.dialog_new_group));
        dialog.show(getSupportFragmentManager(), "NewGroupDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(NewGroupDialogFragment dialog, String groupName) {
        Group group = new Group();

        group.setGroupID(UUID.randomUUID().toString());
        group.setDisplayName(groupName);

        new Save().execute(group);
    }

    @Override
    //Negative click on New Group Dialog
    public void onDialogNegativeClick(NewGroupDialogFragment dialog) {
        // User touched the dialog's negative button
        // Nothing happens
    }

    private class Save extends AsyncTask<Object,Void,Void>{
        @Override
        protected Void doInBackground(Object[] params) {
            for(Object o:params){
                mapper.save(o);
            }
            return null;
        }
    }

    public void openMessageActivity(View view) {
        Intent intent = new Intent(this, MessageActivity.class);
        intent.putExtra(this.MESSAGE_VIEW_GROUP_UID, ((TextView) view.findViewById(R.id.secondLine)).getText().toString());
        startActivity(intent);
    }
}
