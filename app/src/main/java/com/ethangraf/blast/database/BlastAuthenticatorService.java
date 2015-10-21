package com.ethangraf.blast.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Da-Jin on 10/19/2015.
 */
public class BlastAuthenticatorService extends Service {

    private BlastAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new BlastAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
