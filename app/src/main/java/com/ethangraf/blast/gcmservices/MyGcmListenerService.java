package com.ethangraf.blast.gcmservices;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Da-Jin on 8/21/2015.
 */
public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    public static final String MESSAGE_UPDATE = "com.ethangraf.blast.message_updated";

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
            intent.putExtra("groupname",groupName);
            intent.putExtra("subject",subject);
            this.sendOrderedBroadcast(intent,null);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}

