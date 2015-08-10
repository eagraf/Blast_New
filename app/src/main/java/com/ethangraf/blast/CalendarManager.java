package com.ethangraf.blast;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.calendar.CalendarScopes;

import java.util.Arrays;

/**
 * Created by Ethan on 8/9/2015.
 */
public class CalendarManager {
    /**
     * A Google Calendar API service object used to access the API.
     * Note: Do not confuse this class with API library's model classes, which
     * represent specific data structures.
     */
    com.google.api.services.calendar.Calendar mService;

    GoogleAccountCredential credential;
    final HttpTransport transport = AndroidHttp.newCompatibleTransport();
    final JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

    private static final String[] SCOPES = { CalendarScopes.CALENDAR_READONLY };

    public CalendarManager(Context context, String account) {
        // Initialize credentials and service object.
        credential = GoogleAccountCredential.usingOAuth2(
                context.getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff())
                .setSelectedAccountName(account);

        mService = new com.google.api.services.calendar.Calendar.Builder(
                transport, jsonFactory, credential)
                .setApplicationName("Google Calendar API Android Quickstart")
                .build();
    }
}
