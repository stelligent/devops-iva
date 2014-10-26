package com.singlestoneconsulting.participant;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Date;

@DynamoDBTable(tableName="InnovateParticipants")
public class Participant {
    @DynamoDBHashKey(attributeName="PhoneNumber")
    private String phoneNumber;

    @DynamoDBAttribute(attributeName="Name")
    private String name;

    @DynamoDBAttribute(attributeName="Registered")
    private Date registered;

    @DynamoDBAttribute(attributeName="SelfieUrl")
    private String selfieUrl;

    public Participant() {
    }

    public Participant(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.registered = new Date();
    }

    public Participant(String phoneNumber, String name) {
        this(phoneNumber);
        this.setName(name);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSelfieUrl() {
        return selfieUrl;
    }

    public void setSelfieUrl(String selfieUrl) {
        this.selfieUrl = selfieUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        return phoneNumber.equals(that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return phoneNumber.hashCode();
    }
}
