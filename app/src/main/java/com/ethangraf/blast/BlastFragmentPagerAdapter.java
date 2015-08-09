package com.ethangraf.blast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Ethan on 8/8/2015.
 */
public class BlastFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Inbox", "Sent", "Archived" };
    private Context context;

    private InboxFragment inboxFragment;
    private SentFragment sentFragment;
    private ArchiveFragment archiveFragment;

    public BlastFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        inboxFragment = new InboxFragment();
        sentFragment = new SentFragment();
        archiveFragment = new ArchiveFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return inboxFragment;
            case 1:
                return sentFragment;
            case 2:
                return archiveFragment;
            default:
                return inboxFragment;
        }

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
