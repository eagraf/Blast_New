package com.ethangraf.blast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Da-Jin on 8/9/2015.
 */
@DynamoDBTable(tableName = "Users")
public class User {
    String identityID;
    String name;

    @DynamoDBHashKey(attributeName = "Identity ID")
    public String getIdentityID() {
        return identityID;
    }

    public void setIdentityID(String identityID) {
        this.identityID = identityID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
