package com.ethangraf.blast;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class InboxFragment extends Fragment {

    public RecyclerView mInboxView;
    public InboxAdapter mInboxAdapter;
    public RecyclerView.LayoutManager mInboxLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        mInboxView = (RecyclerView) view.findViewById(R.id.inbox_list_view);
        mInboxLayoutManager = new LinearLayoutManager(getActivity());
        mInboxView.setLayoutManager(mInboxLayoutManager);

        mInboxAdapter = new InboxAdapter(Arrays.asList(planets));
        mInboxView.setAdapter(mInboxAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mInboxView.setHasFixedSize(true);
        return view;
    }
}
