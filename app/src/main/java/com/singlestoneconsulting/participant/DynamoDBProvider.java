package com.singlestoneconsulting.participant;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.singlestoneconsulting.util.Provider;
import org.springframework.stereotype.Component;

@Component
public class DynamoDBProvider implements Provider<AmazonDynamoDB> {

    private final AmazonDynamoDBClient amazonDynamoDBClient;

    public DynamoDBProvider() {
        amazonDynamoDBClient = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain());
    }

    @Override
    public AmazonDynamoDB provide() {
        return amazonDynamoDBClient;
    }
}
