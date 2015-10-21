package com.ethangraf.blast.database;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.ethangraf.blast.database.model.Group;
import com.ethangraf.blast.ui.MainActivity;

/**
 * Created by Da-Jin on 10/19/2015.
 */
public class BlastSyncAdapter extends AbstractThreadedSyncAdapter {
    public BlastSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    public BlastSyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("BlastSyncAdapter","syncing");
        CacheManager manager = new CacheManager(getContext());
        SQLiteDatabase database = manager.getWritableDatabase();
        for(String sub :MainActivity.user.getSubscriptions()){//TODO NULL POINTER HERE
            manager.groupsHelper.putGroup(database, MainActivity.mapper.load(Group.class, sub));
        }
        syncResult.hasHardError();
    }
}
