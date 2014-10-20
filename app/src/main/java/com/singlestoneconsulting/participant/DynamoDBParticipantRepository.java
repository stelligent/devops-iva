package com.singlestoneconsulting.participant;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class DynamoDBParticipantRepository implements ParticipantRepository {
    private static final String ACCESS_KEY = "AKIAIZXP42ONWF4U7LXA";
    private static final String SECRET_KEY = "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";

    private final DynamoDBMapper dynamoDB;

    public DynamoDBParticipantRepository() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY));
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
