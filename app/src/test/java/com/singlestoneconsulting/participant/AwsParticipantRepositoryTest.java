package com.singlestoneconsulting.participant;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.util.DateUtils;
import com.singlestoneconsulting.util.Provider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;


@RunWith(MockitoJUnitRunner.class)
public class AwsParticipantRepositoryTest {

    private static final String PHONE = "1234";
    private static final String NAME = "Foo";
    private static final Date REGISTERED = new Date();

    @Mock
    Provider<AmazonDynamoDB> amazonDynamoDBProvider;

    @Mock
    AmazonDynamoDB amazonDynamoDB;

    AwsParticipantRepository repository;

    DateUtils dateUtils = new DateUtils();

    @Before
    public void before() {
        when(amazonDynamoDBProvider.provide()).thenReturn(amazonDynamoDB);
        repository = new AwsParticipantRepository(amazonDynamoDBProvider);
    }

    @Test
    public void testGet() {
        // given
        when(amazonDynamoDB.getItem(any(GetItemRequest.class))).thenReturn(toItem(PHONE, NAME, REGISTERED));

        // when
        Participant p = repository.get(PHONE);

        // then
        assertThat(p.getPhoneNumber(), is(PHONE));
        assertThat(p.getName(), is(NAME));
        assertThat(p.getRegistered(), is(REGISTERED));
    }

    @Test
    public void testSave() {
        // given
        Participant p = new Participant(PHONE);
        p.setName(NAME);
        p.setRegistered(REGISTERED);

        // when
        repository.save(p);

        // then
        verify(amazonDynamoDB, atLeastOnce()).updateItem(any(UpdateItemRequest.class));
    }

    @Test
    public void testAll() {
        // given
        ScanResult result = new ScanResult();
        result.setItems(Arrays.asList(
                toItem(PHONE, NAME, REGISTERED).getItem()
        ));
        when(amazonDynamoDB.scan(any(ScanRequest.class))).thenReturn(result);

        // when
        Set<Participant> participants = repository.all();

        // then
        assertThat(participants.size(), is(1));
        Participant p = participants.iterator().next();
        assertThat(p.getPhoneNumber(), is(PHONE));
        assertThat(p.getName(), is(NAME));
        assertThat(p.getRegistered(), is(REGISTERED));
    }

    private GetItemResult toItem(String phone, String name, Date d) {
        return new GetItemResult()
                .addItemEntry("PhoneNumber", new AttributeValue(phone))
                .addItemEntry("Name", new AttributeValue(name))
                .addItemEntry("Registered", new AttributeValue(dateUtils.formatIso8601Date(d)));
    }
}
