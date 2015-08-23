package com.ethangraf.blast;

import android.os.Parcel;
import android.os.Parcelable;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Da-Jin on 8/8/2015.
 */

@DynamoDBTable(tableName = "Groups")
public class Group implements Parcelable {
    private String groupID;
    private String displayName;
    private String owner;
    private String ownerName;

    private List<String> subscribers = new ArrayList<>();
    private List<String> editors = new ArrayList<>();

    public Group() {}

    @DynamoDBHashKey(attributeName = "Group ID")
    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    @DynamoDBIndexHashKey(attributeName = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @DynamoDBAttribute(attributeName = "Subscribers")
    public List<String> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }

    @DynamoDBAttribute(attributeName = "Owner")
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @DynamoDBIndexHashKey(attributeName = "OwnerName")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @DynamoDBAttribute(attributeName = "Editors")
    public List<String> getEditors() {
        return editors;
    }

    public void setEditors(List<String> editors) {
        this.editors = editors;
    }

    public void addSubscriber(String subscriber) {
        this.subscribers.add(subscriber);
        new MainActivity.Save().execute(this);
    }

    public void removeSubscriber(String subscriber) {
        this.subscribers.remove(subscriber);
        new MainActivity.Save().execute(this);
    }

    public void addEditor(String editor) {
        this.editors.add(editor);
        new MainActivity.Save().execute(this);
    }

    public void removeEditor(String editor) {
        this.editors.remove(editor);
        new MainActivity.Save().execute(this);
    }

    /* everything below here is for implementing Parcelable */

    // 99.9% of the time you can just ignore this
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(groupID);
        out.writeString(displayName);
        out.writeString(owner);
        out.writeString(ownerName);

        out.writeList(subscribers);
        out.writeList(editors);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Group> CREATOR = new Parcelable.Creator<Group>() {
        public Group createFromParcel(Parcel in) {
            return new Group(in);
        }

        public Group[] newArray(int size) {
            return new Group[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Group(Parcel in) {
        groupID = in.readString();
        displayName = in.readString();
        owner = in.readString();
        ownerName = in.readString();

        subscribers = new ArrayList<>();
        editors = new ArrayList<>();
        in.readList(subscribers, null);
        in.readList(editors, null);
    }
}
