package com.ethangraf.blast;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private NavigationView mDrawerView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private BlastFragment blastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                break;
            case R.id.navigation_contact_item:
                break;
            case R.id.navigation_profile_item:
                break;
            case R.id.navigation_settings_item:
                break;
            case R.id.navigation_info_item:
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
}
