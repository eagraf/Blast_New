package com.ethangraf.blast.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Da-Jin on 10/19/2015.
 */
/*
 * This is just here to satisfy SyncAdapter's requirement of having
 * a content provider. The SQLite database is manipulated directly
 * in the sync adapter implementation.
 * If, in the future Blast needs to be able to share data with other
 * apps, it may help to make this Content Provider wrap the SQLite DB
 */
public class StubContentProvider extends ContentProvider{

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
