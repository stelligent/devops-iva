package com.singlestoneconsulting.participant;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.singlestoneconsulting.util.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class AwsParticipantRepository implements ParticipantRepository {
    private final DynamoDBMapper dynamoDB;

    @Autowired
    public AwsParticipantRepository(final Provider<AmazonDynamoDB> amazonDynamoDBProvider) {
        dynamoDB = new DynamoDBMapper(amazonDynamoDBProvider.provide());
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