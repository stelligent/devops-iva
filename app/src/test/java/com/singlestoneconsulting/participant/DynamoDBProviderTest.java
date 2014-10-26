package com.singlestoneconsulting.participant;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

public class DynamoDBProviderTest {

    @Before
    public void before() {
        System.setProperty("aws.accessKeyId", "FAKE");
        System.setProperty("aws.secretKey", "FAKE");
    }

    @After
    public void after() {
        System.setProperty("aws.accessKeyId", "");
        System.setProperty("aws.secretKey", "");
    }

    @Test
    public void test() {
        assertThat(new DynamoDBProvider().provide(), isA(AmazonDynamoDB.class));
    }
}
