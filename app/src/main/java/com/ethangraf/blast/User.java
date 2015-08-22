package com.ethangraf.blast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

/**
 * Created by Da-Jin on 8/9/2015.
 */
@DynamoDBTable(tableName = "Users")
public class User {
    String identityID;
    String name;
    List<String> subscriptions;

    @DynamoDBHashKey(attributeName = "Identity ID")
    public String getIdentityID() {
        return identityID;
    }

    public void setIdentityID(String identityID) {
        this.identityID = identityID;
    }

    @DynamoDBIndexHashKey(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Subscriptions")
    public List<String> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<String> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public void addSubscription(String subscription) {
        this.subscriptions.add(subscription);
        new MainActivity.Save().execute(this);
    }

    public void removeSubscription(String subscription) {
        this.subscriptions.remove(subscription);
        new MainActivity.Save().execute(this);
    }
}
