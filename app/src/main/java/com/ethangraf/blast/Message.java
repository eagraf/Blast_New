package com.ethangraf.blast;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by Da-Jin on 8/9/2015.
 */
@DynamoDBTable(tableName = "Messages")
public class Message {

    String groupID;
    String datePosted;
    String subject;
    String body;


    @DynamoDBAttribute(attributeName = "Body")
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    @DynamoDBAttribute(attributeName = "Subject")
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }



    @DynamoDBHashKey(attributeName = "Group ID")
    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    @DynamoDBRangeKey(attributeName = "DatePosted")
    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }



}
