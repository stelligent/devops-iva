package com.singlestoneconsulting.participant;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class AwsParticipantRepository implements ParticipantRepository {
    private final DynamoDBMapper dynamoDB;

    public AwsParticipantRepository() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain());
        dynamoDB = new DynamoDBMapper(client);
    }

    @Override
    public Participant get(String phoneNumber) {
        return dynamoDB.load(Participant.class, phoneNumber);
    }

    @Override
    public void save(Participant participant) {
        dynamoDB.save(participant);
    }

    @Override
    public Set<Participant> all() {
        return new HashSet<>(dynamoDB.scan(Participant.class, new DynamoDBScanExpression()));
    }
}
