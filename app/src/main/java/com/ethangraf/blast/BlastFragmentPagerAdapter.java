package com.ethangraf.blast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ethan on 8/8/2015.
 */
public class BlastFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 1;
    private String tabTitles[] = new String[] { "Inbox" };
    private Context context;

    private InboxFragment inboxFragment;

    public BlastFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        inboxFragment = new InboxFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
        }
        return inboxFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
