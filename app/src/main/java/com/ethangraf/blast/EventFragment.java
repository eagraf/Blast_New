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
 * Created by Ethan on 8/9/2015.
 */
public class EventFragment extends Fragment {

    public RecyclerView mEventView;
    public EventAdapter mEventAdapter;
    public RecyclerView.LayoutManager mEventLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Nipple", "Pluto"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        //Set up the toolbar
        Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.events);

        //Enable ActionBar app icon to behave as action to toggle nav drawer
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getDrawerLayout().openDrawer(GravityCompat.START);
            }
        });

        setHasOptionsMenu(true);

        mEventView = (RecyclerView) view.findViewById(R.id.event_list_view);
        mEventLayoutManager = new LinearLayoutManager(getActivity());
        mEventView.setLayoutManager(mEventLayoutManager);

        mEventAdapter = new EventAdapter(Arrays.asList(planets));
        mEventView.setAdapter(mEventAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mEventView.setHasFixedSize(true);

        return view;
    }

}
