package com.ethangraf.blast;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class GroupFragment extends Fragment {

    public RecyclerView mGroupView;
    public GroupAdapter mGroupAdapter;
    public RecyclerView.LayoutManager mGroupLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        setHasOptionsMenu(true);

        mGroupView = (RecyclerView) view.findViewById(R.id.group_list_view);
        mGroupLayoutManager = new LinearLayoutManager(getActivity());
        mGroupView.setLayoutManager(mGroupLayoutManager);

        mGroupAdapter = new GroupAdapter();
        mGroupView.setAdapter(mGroupAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mGroupView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Auto-generated method stub
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_group_view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.action_add_group:
                Intent intent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
