package com.ethangraf.blast;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.ethangraf.blast.gcmservices.RegistrationIntentService;

import java.util.UUID;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        NameDialogFragment.NameDialogListener {

    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static Fragment currentFragment;
    private BlastFragment blastFragment;
    private EventFragment eventFragment;
    private ReminderFragment reminderFragment;
    private GroupFragment groupFragment;

    public static DynamoDBMapper mapper;

    public static User user;

    public final static String MESSAGE_VIEW_GROUP = "com.ethangraf.blast.GROUP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize some navigation drawer stuff.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navigation_layout);
        mDrawerView = (NavigationView) findViewById(R.id.navigation_view);
        mDrawerView.setNavigationItemSelectedListener(this);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        //Enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Initialize the fragment manager.
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        //Various fragments used by main activity.
        blastFragment = new BlastFragment();
        groupFragment = new GroupFragment();
        eventFragment = new EventFragment();
        reminderFragment = new ReminderFragment();
        /*
        System.out.println(savedInstanceState);
        if(savedInstanceState != null) {
            currentFragment = fragmentManager.getFragment(savedInstanceState, "fragment");
            fragmentTransaction.replace(R.id.main_content_frame, currentFragment);
            fragmentTransaction.commit();
            System.out.println(savedInstanceState);
        }
        else {
            //Start in the inbox fragment.
            fragmentTransaction.add(R.id.main_content_frame, blastFragment);
            fragmentTransaction.commit();
            currentFragment = blastFragment;
            savedInstanceState = new Bundle();
        }*/
        if(currentFragment != null) {
            fragmentTransaction.replace(R.id.main_content_frame, currentFragment);
            fragmentTransaction.commit();
        }
        else {
            fragmentTransaction.add(R.id.main_content_frame, blastFragment);
            fragmentTransaction.commit();
            currentFragment = blastFragment;
        }

        //Set the navigation drawer information.
        ((TextView) findViewById(R.id.navigation_header).findViewById(R.id.name)).setText(user.getName());
        ((TextView) findViewById(R.id.navigation_header).findViewById(R.id.email)).setText(user.getIdentityID());

        // Start IntentService to register this application with GCM.
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        System.out.println(outState);
        fragmentManager.putFragment(outState, "fragment", currentFragment);
        System.out.println(outState);
        super.onSaveInstanceState(outState);
        System.out.println(outState);
    }

   /* @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        System.out.println("ho");
        if(savedInstanceState != null) {
            currentFragment = fragmentManager.getFragment(savedInstanceState, "fragment");
            fragmentTransaction.replace(R.id.main_content_frame, currentFragment);
            fragmentTransaction.commit();
        }
        else {
            //Start in the inbox fragment.
            fragmentTransaction.add(R.id.main_content_frame, blastFragment);
            fragmentTransaction.commit();
            currentFragment = blastFragment;
        }
        super.onRestoreInstanceState(savedInstanceState);
    }*/

    @Override
    //Handle click events on navigation drawer items.
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
        fragmentTransaction = getFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.navigation_inbox_item:
                //Open the inbox fragment.
                fragmentTransaction.replace(R.id.main_content_frame, blastFragment);
                fragmentTransaction.commit();
                currentFragment = blastFragment;
                break;
            case R.id.navigation_event_item:
                fragmentTransaction.replace(R.id.main_content_frame, eventFragment);
                fragmentTransaction.commit();
                currentFragment = eventFragment;
                break;
            case R.id.navigation_reminder_item:
                fragmentTransaction.replace(R.id.main_content_frame, reminderFragment);
                fragmentTransaction.commit();
                currentFragment = reminderFragment;
                break;
            case R.id.navigation_group_item:
                fragmentTransaction.replace(R.id.main_content_frame, groupFragment);
                fragmentTransaction.commit();
                currentFragment = groupFragment;
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

        return true;
    }

    public DrawerLayout getDrawerLayout() {
        return mDrawerLayout;
    }

    //Create a new group
    public void newGroup(View v) {
        // Create an instance of the new group dialog fragment and show it
        NameDialogFragment dialog = new NameDialogFragment();
        dialog.setTitle(getResources().getString(R.string.dialog_new_group));
        dialog.setDefaultText(getResources().getString(R.string.dialog_new_group));
        dialog.show(getSupportFragmentManager(), "NewGroupDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(NameDialogFragment dialog, String groupName) {
        Group group = new Group();

        group.setGroupID(UUID.randomUUID().toString());
        group.setDisplayName(groupName);
        group.setOwner(user.getIdentityID());
        group.setOwnerName(user.getName());

        group.addSubscriber(user.getIdentityID());
        user.addSubscription(group.getGroupID());

        group.addEditor(user.getIdentityID());

        new Save().execute(group);
    }

    @Override
    //Negative click on New Group Dialog
    public void onDialogNegativeClick(NameDialogFragment dialog) {
        // User touched the dialog's negative button
        // Nothing happens
    }

    public static class Save extends AsyncTask<Object,Void,Void>{
        @Override
        protected Void doInBackground(Object[] params) {
            for(Object o:params){
                mapper.save(o);
            }
            return null;
        }
    }

    public void openMessageActivity(View view) {
        new AsyncTask<View,Void,Group>(){

            @Override
            protected Group doInBackground(View... params) {
                //Get the group to be loaded into the new activity.
                Group group = mapper.load(Group.class, ((TextView) params[0].findViewById(R.id.groupId)).getText().toString());
                return group;
            }

            @Override
            protected void onPostExecute(Group group) {
                // Pass the parcelable group into the new activity.
                Intent intent = new Intent(MainActivity.this, MessageActivity.class);
                intent.putExtra(MESSAGE_VIEW_GROUP, group);
                startActivity(intent);
            }
        }.execute(view);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
