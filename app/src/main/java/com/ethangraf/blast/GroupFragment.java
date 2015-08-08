package com.ethangraf.blast;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class GroupFragment extends Fragment {

    public RecyclerView mGroupView;
    public InboxAdapter mGroupAdapter;
    public RecyclerView.LayoutManager mGroupLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        //Enable ActionBar app icon to behave as action to toggle nav drawer
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });

        mGroupView = (RecyclerView) view.findViewById(R.id.group_list_view);
        mGroupLayoutManager = new LinearLayoutManager(getActivity());
        mGroupView.setLayoutManager(mGroupLayoutManager);

        mGroupAdapter = new InboxAdapter(Arrays.asList(planets));
        mGroupView.setAdapter(mGroupAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mGroupView.setHasFixedSize(true);

        return view;
    }
}
