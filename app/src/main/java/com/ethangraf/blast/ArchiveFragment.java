package com.ethangraf.blast;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

/**
 * Created by Ethan on 8/8/2015.
 */
public class ArchiveFragment extends Fragment {

    public RecyclerView mArchiveView;
    public InboxAdapter mArchiveAdapter;
    public RecyclerView.LayoutManager mArchiveLayoutManager;

    private static final String planets[] = new String[] {"Sun", "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_archive, container, false);

        mArchiveView = (RecyclerView) view.findViewById(R.id.archive_list_view);
        mArchiveLayoutManager = new LinearLayoutManager(getActivity());
        mArchiveView.setLayoutManager(mArchiveLayoutManager);

        mArchiveAdapter = new InboxAdapter(Arrays.asList(planets));
        mArchiveView.setAdapter(mArchiveAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mArchiveView.setHasFixedSize(true);
        return view;
    }
}
