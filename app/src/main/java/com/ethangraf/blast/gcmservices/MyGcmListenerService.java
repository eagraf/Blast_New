package com.ethangraf.blast.gcmservices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ethangraf.blast.GoogleOAuthActivity;
import com.ethangraf.blast.R;
import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Da-Jin on 8/21/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    public static final String MESSAGE_UPDATE = "message_updated";

    /**
     * Called when message is received.
     *
     * @param from SenderID of the sender.
     * @param data Data bundle containing message data as key/value pairs.
     *             For Set of keys use data.keySet().
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(String from, Bundle data) {
        Log.d(TAG,data.toString());

        try {
            JSONObject json = new JSONObject(data.getString("json"));
            long dateposted = json.getJSONObject("DatePosted").getLong("N");
            String subject = json.getJSONObject("Subject").getString("S");
            String body = json.getJSONObject("Body").getString("S");
            String groupid = json.getJSONObject("Group ID").getString("S");
            String groupName = json.getJSONObject("DisplayName").getString("S");

            Log.d(TAG, "From: " + from);
            Log.d(TAG, "Message: " + subject);
            Log.d(TAG, "Date Time: " + dateposted);


            if (from.startsWith("/topics/")) {
                // message received from some topic.
            } else {
                // normal downstream message.
            }

            Intent intent = new Intent(MESSAGE_UPDATE);
            intent.putExtra("groupid",groupid);
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            sendNotification(groupName, subject);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String title, String message) {
        Intent intent = new Intent(this, GoogleOAuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_pause_dark)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}

