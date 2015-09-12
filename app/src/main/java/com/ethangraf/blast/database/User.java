package com.ethangraf.blast.database;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.ethangraf.blast.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Da-Jin on 8/9/2015.
 */
@DynamoDBTable(tableName = "Users")
public class User {

    //Generic user information
    private String id;
    private String name;
    private String email;

    private List<String> subscriptions = new ArrayList<>();
    private List<String> contacts = new ArrayList<>();

    private List<String> invitations = new ArrayList<>();
    private List<String> newInvitations = new ArrayList<>();

    private List<String> endpoints;

    //Google platform information
    private String googleId;

    @DynamoDBHashKey(attributeName = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "Name-index")
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @DynamoDBAttribute(attributeName = "Contacts")
    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }

    public void addContact(String contact) {
        this.contacts.add(contact);
        new MainActivity.Save().execute(this);
    }

    public void removeContacts(String contact) {
        this.contacts.remove(contact);
        new MainActivity.Save().execute(this);
    }

    @DynamoDBAttribute(attributeName = "SNSEndpoints")
    public List<String> getEndpoints(){
        return endpoints;
    }

    @DynamoDBAttribute(attributeName = "SNSEndpoints")
    public void setEndpoints(List<String> endpoints){
        this.endpoints = endpoints;
    }

    public void addEndpoint(String endpoint) {
        this.endpoints.add(endpoint);
        new MainActivity.Save().execute(this);
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = "GoogleID-index")
    @DynamoDBAttribute(attributeName = "Google ID")
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    @DynamoDBAttribute(attributeName = "Invitations")
    public List<String> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<String> invitations) {
        this.invitations = invitations;
    }

    public void addInvitation(String group) {
        this.invitations.add(group);
        new MainActivity.Save().execute(this);
    }

    public void removeInvitation(String group) {
        this.invitations.remove(group);
        new MainActivity.Save().execute(this);
    }

    @DynamoDBAttribute(attributeName = "New Invitations")
    public List<String> getNewInvitations() {
        return newInvitations;
    }

    public void setNewInvitations(List<String> invitations) {
        this.newInvitations = invitations;
    }

    public void addNewInvitation(String group) {
        this.newInvitations.add(group);
        new MainActivity.Save().execute(this);
    }

    public void removeNewInvitation(String group) {
        this.newInvitations.remove(invitations);
        new MainActivity.Save().execute(this);
    }
}
