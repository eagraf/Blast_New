package com.ethangraf.blast.gcmservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.ethangraf.blast.R;

/**
 * Created by Da-Jin on 8/28/2015.
 */
public class GcmNotificationRaisingBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Don't raise notification if the current user is the one who sent the message!
        String author = intent.getStringExtra("author");
        String lastLoggedIn = context.getSharedPreferences("com.ethangraf.blast",Context.MODE_PRIVATE)
                .getString("user", "");
        if(author.equals(lastLoggedIn)){
            return;
        }

        String groupname = intent.getStringExtra("groupname");
        String subject = intent.getStringExtra("subject");


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_pause_dark)
                .setContentTitle(groupname)
                .setContentText(subject)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
