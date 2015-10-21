package com.ethangraf.blast.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ethangraf.blast.database.model.Group;
import com.ethangraf.blast.database.model.Message;
import com.ethangraf.blast.database.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ethan on 9/14/2015.
 * Bunch of helper classes for managing the caching system.
 */
public class CacheManager extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Cache.db";

    public GroupsHelper groupsHelper = new GroupsHelper();
    public UsersHelper usersHelper = new UsersHelper();
    public MessagesHelper messagesHelper = new MessagesHelper();

    public CacheManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.TableGroup.CREATE_GROUPS);
        db.execSQL(DatabaseContract.TableUsers.CREATE_USERS);
        db.execSQL(DatabaseContract.TableMessages.CREATE_MESSAGES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(DatabaseContract.TableGroup.DELETE_ENTRIES);
        db.execSQL(DatabaseContract.TableUsers.DELETE_ENTRIES);
        db.execSQL(DatabaseContract.TableMessages.DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /*
     * Helper class for the Group table.
     */
    public class GroupsHelper {

        //Place a new group into the database.
        public void putGroup(SQLiteDatabase db, Group group) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TableGroup.COLUMN_GROUP_ID, group.getGroupID());
            values.put(DatabaseContract.TableGroup.COLUMN_DISPLAY_NAME, group.getDisplayName());
            values.put(DatabaseContract.TableGroup.COLUMN_OWNER, group.getOwner());
            values.put(DatabaseContract.TableGroup.COLUMN_OWNER_NAME, group.getOwnerName());
            values.put(DatabaseContract.TableGroup.COLUMN_EDITORS, convertArrayToString(group.getEditors()));
            values.put(DatabaseContract.TableGroup.COLUMN_SUBSCRIBERS, convertArrayToString(group.getSubscribers()));

            //Actually place the group in.
            long newRowId = db.insert(
                    DatabaseContract.TableGroup.TABLE_NAME,
                    null,
                    values);
        }

        //Update a Group that has been changed.
        public void updateGroup(SQLiteDatabase db, Group group) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TableGroup.COLUMN_GROUP_ID, group.getGroupID());
            values.put(DatabaseContract.TableGroup.COLUMN_DISPLAY_NAME, group.getDisplayName());
            values.put(DatabaseContract.TableGroup.COLUMN_OWNER, group.getOwner());
            values.put(DatabaseContract.TableGroup.COLUMN_OWNER_NAME, group.getOwnerName());
            values.put(DatabaseContract.TableGroup.COLUMN_EDITORS, convertArrayToString(group.getEditors()));
            values.put(DatabaseContract.TableGroup.COLUMN_SUBSCRIBERS, convertArrayToString(group.getSubscribers()));

            //Update the group with the matching group ID.
            long newRowId = db.update(
                    DatabaseContract.TableGroup.TABLE_NAME,
                    values,
                    DatabaseContract.TableGroup.COLUMN_GROUP_ID+"=?",
                    new String[] { group.getGroupID() });
        }

        //Get all of the groups in the table.
        public ArrayList<Group> getGroups(SQLiteDatabase db) {
            ArrayList<Group> groups = new ArrayList<>();

            //Empty query should return entire table.
            Cursor cursor = db.query(DatabaseContract.TableGroup.TABLE_NAME, null, null, null, null, null, null);

            //Iterate through the cursor and fill up each group object.
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++) {
                groups.add(new Group());
                groups.get(i).setGroupID(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableGroup.COLUMN_GROUP_ID)));
                groups.get(i).setDisplayName(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableGroup.COLUMN_DISPLAY_NAME)));
                groups.get(i).setOwner(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableGroup.COLUMN_OWNER)));
                groups.get(i).setOwnerName(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableGroup.COLUMN_OWNER_NAME)));
                groups.get(i).setEditors(convertStringToArray(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableGroup.COLUMN_EDITORS))));
                groups.get(i).setSubscribers(convertStringToArray(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableGroup.COLUMN_GROUP_ID))));

                cursor.moveToNext();
            }
            return groups;
        }
    }

    /*
     * Helper class for the User table.
     */
    public class UsersHelper {

        //Put a User into the database.
        public void putUser(SQLiteDatabase db, User user) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TableUsers.COLUMN_ID, user.getId());
            values.put(DatabaseContract.TableUsers.COLUMN_NAME, user.getName());
            values.put(DatabaseContract.TableUsers.COLUMN_EMAIL, user.getEmail());
            values.put(DatabaseContract.TableUsers.COLUMN_GOOGLE_ID, user.getGoogleId());
            values.put(DatabaseContract.TableUsers.COLUMN_CONTACTS, convertArrayToString(user.getContacts()));
            values.put(DatabaseContract.TableUsers.COLUMN_SUBSCRIPTIONS, convertArrayToString(user.getSubscriptions()));
            values.put(DatabaseContract.TableUsers.COLUMN_INVITATIONS, convertArrayToString(user.getInvitations()));
            values.put(DatabaseContract.TableUsers.COLUMN_NEW_INVITATIONS, convertArrayToString(user.getNewInvitations()));
            values.put(DatabaseContract.TableUsers.COLUMN_SNSENDPOINTS, convertArrayToString(user.getEndpoints()));

            long newRowId = db.insert(
                    DatabaseContract.TableUsers.TABLE_NAME,
                    null,
                    values);
        }

    }

    /*
     * Helper class for the Message table.
     */
    public class MessagesHelper {

        //Put a Message into the database.
        public void putMessage(SQLiteDatabase db, Message message) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.TableMessages.COLUMN_GROUP_ID, message.getGroupID());
            values.put(DatabaseContract.TableMessages.COLUMN_SUBJECT, message.getSubject());
            values.put(DatabaseContract.TableMessages.COLUMN_BODY, message.getBody());
            values.put(DatabaseContract.TableMessages.COLUMN_AUTHOR, message.getAuthor());
            values.put(DatabaseContract.TableMessages.COLUMN_DATE_POSTED, message.getDatePosted());

            long newRowId = db.insert(
                    DatabaseContract.TableMessages.TABLE_NAME,
                    null,
                    values);
        }

        //Retrieve all of the messages for a given group.
        public List<Message> getMessages(SQLiteDatabase db, Group group) {
            ArrayList<Message> messages = new ArrayList<>();
            Cursor cursor =  db.query(DatabaseContract.TableMessages.TABLE_NAME, null, DatabaseContract.TableMessages.COLUMN_GROUP_ID + "=?", new String[] { group.getGroupID()}, null, null, null);

            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++) {

                messages.get(i).setGroupID(group.getGroupID());
                messages.get(i).setSubject(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableMessages.COLUMN_SUBJECT)));
                messages.get(i).setBody(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableMessages.COLUMN_BODY)));
                messages.get(i).setAuthor(cursor.getString(cursor.getColumnIndex(DatabaseContract.TableMessages.COLUMN_AUTHOR)));
                messages.get(i).setDatePosted(cursor.getLong(cursor.getColumnIndex(DatabaseContract.TableMessages.COLUMN_DATE_POSTED)));

                cursor.moveToNext();
            }
            return messages;
        }
    }

    //These methods are for converting arrays into strings and back.
    public static String strSeparator = "__,__";
    public static String convertArrayToString(List<String> array){
        StringBuilder sb = new StringBuilder();
        for (String str : array){
            sb.append(str);
            sb.append(strSeparator);
        }
        return sb.toString();
    }
    public static List<String> convertStringToArray(String str){
        return Arrays.asList(str.split(strSeparator));
    }


}