package com.ethangraf.blast.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ethangraf.blast.R;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class SentFragment extends Fragment {

    public RecyclerView mSentView;
    public InboxAdapter mSentAdapter;
    public RecyclerView.LayoutManager mSentLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent, container, false);

        mSentView = (RecyclerView) view.findViewById(R.id.sent_list_view);
        mSentLayoutManager = new LinearLayoutManager(getActivity());
        mSentView.setLayoutManager(mSentLayoutManager);

        mSentAdapter = new InboxAdapter(Arrays.asList(planets));
        mSentView.setAdapter(mSentAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mSentView.setHasFixedSize(true);
        return view;
    }
}