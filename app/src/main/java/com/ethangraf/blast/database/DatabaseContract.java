package com.ethangraf.blast.database;

import android.provider.BaseColumns;

/**
 * Created by Ethan on 9/24/2015.
 */
public class DatabaseContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public DatabaseContract() {}

    /* Inner class that defines the table contents */
    public static abstract class TableGroup implements BaseColumns {
        public static final String TABLE_NAME = "Groups";
        public static final String COLUMN_GROUP_ID = "GroupID";
        public static final String COLUMN_DISPLAY_NAME = "DisplayName";
        public static final String COLUMN_EDITORS = "Editors";
        public static final String COLUMN_OWNER = "Owner";
        public static final String COLUMN_OWNER_NAME = "OwnerName";
        public static final String COLUMN_SUBSCRIBERS = "Subscribers";

        public static final String CREATE_GROUPS =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_GROUP_ID + " STRING PRIMARY KEY,"
                        + COLUMN_DISPLAY_NAME + " TEXT,"
                        + COLUMN_EDITORS + " TEXT,"
                        + COLUMN_OWNER + " TEXT,"
                        + COLUMN_OWNER_NAME + " TEXT,"
                        + COLUMN_SUBSCRIBERS + " TEXT"
                        +" )";

        public static final String DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TableUsers implements BaseColumns {
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_CONTACTS = "Contacts";
        public static final String COLUMN_EMAIL = "Email";
        public static final String COLUMN_GOOGLE_ID = "Google ID";
        public static final String COLUMN_INVITATIONS = "Invitations";
        public static final String COLUMN_NAME = "Name";
        public static final String COLUMN_NEW_INVITATIONS = "New Invitations";
        public static final String COLUMN_SNSENDPOINTS = "SNSEndpoints";
        public static final String COLUMN_SUBSCRIPTIONS = "Subscriptions";

        public static final String CREATE_USERS =
                "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " STRING PRIMARY KEY,"
                + COLUMN_CONTACTS + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_GOOGLE_ID + " TEXT,"
                + COLUMN_INVITATIONS + " TEXT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_NEW_INVITATIONS + " TEXT,"
                + COLUMN_SNSENDPOINTS + " TEXT"
                + COLUMN_SUBSCRIPTIONS + " TEXT"
                +" )";

        public static final String DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class TableMessages implements BaseColumns {
        public static final String TABLE_NAME = "Messages";
        public static final String COLUMN_GROUP_ID = "GroupID";
        public static final String COLUMN_DATE_POSTED = "DatePosted";
        public static final String COLUMN_AUTHOR = "Author";
        public static final String COLUMN_BODY = "Body";
        public static final String COLUMN_SUBJECT = "Subject";
        public static final String COLUMN_ATTACHMENTS = "Attachments";

        public static final String CREATE_MESSAGES =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_GROUP_ID + " STRING PRIMARY KEY,"
                        + COLUMN_DATE_POSTED + " LONG,"
                        + COLUMN_AUTHOR + " TEXT,"
                        + COLUMN_BODY + " TEXT,"
                        + COLUMN_SUBJECT + " TEXT,"
                        + COLUMN_ATTACHMENTS + " TEXT"
                        +" )";

        public static final String DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
