package com.ethangraf.blast.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethangraf.blast.R;

import java.util.Arrays;

/**
 * Created by Ethan on 8/9/2015.
 */
public class ReminderFragment extends Fragment {


    public RecyclerView mReminderView;
    public ReminderAdapter mReminderAdapter;
    public RecyclerView.LayoutManager mReminderLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Nipple", "Pluto"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reminder, container, false);

        setHasOptionsMenu(true);

        mReminderView = (RecyclerView) view.findViewById(R.id.reminder_list_view);
        mReminderLayoutManager = new LinearLayoutManager(getActivity());
        mReminderView.setLayoutManager(mReminderLayoutManager);

        mReminderAdapter = new ReminderAdapter(Arrays.asList(planets));
        mReminderView.setAdapter(mReminderAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mReminderView.setHasFixedSize(true);

        return view;
    }

}
