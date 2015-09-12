package com.ethangraf.blast.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ethangraf.blast.R;

/**
 * Created by Ethan on 8/8/2015.
 */
public class GroupFragment extends Fragment {

    public RecyclerView mGroupView;
    public GroupAdapter mGroupAdapter;
    public RecyclerView.LayoutManager mGroupLayoutManager;

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
                Intent sIntent = new Intent(getActivity(), SubscriptionActivity.class);
                startActivity(sIntent);
                return true;
            case R.id.action_invites:
                Intent iIntent = new Intent(getActivity(), InvitesActivity.class);
                startActivity(iIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
