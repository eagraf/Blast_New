package com.ethangraf.blast.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Created by Da-Jin on 10/19/2015.
 */
public class BlastSyncService extends Service{
    private static BlastSyncAdapter sSyncAdapter = null;
    //Object for thread safe lock
    private static final Object sSyncAdapterLock = new Object();

    private static final String TAG = "BlastSyncService";

    @Override
    public void onCreate() {
        Log.i(TAG, "hi");

        synchronized (sSyncAdapterLock){
            if(sSyncAdapter==null){
                sSyncAdapter = new BlastSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"bind");

        return sSyncAdapter.getSyncAdapterBinder();
    }
}
