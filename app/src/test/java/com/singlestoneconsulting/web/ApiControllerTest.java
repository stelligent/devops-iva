package com.singlestoneconsulting.web;

import com.singlestoneconsulting.participant.Participant;
import com.singlestoneconsulting.participant.ParticipantRepository;
import com.singlestoneconsulting.sms.SmsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ApiControllerTest {

    @Mock
    ParticipantRepository participantRepository;

    @Mock
    SmsService smsService;

    @InjectMocks
    ApiController controller;

    @Test
    public void participants() {
        // given
        Set<Participant> expected = makeParticipants("1", "2");
        when(participantRepository.all()).thenReturn(expected);

        // when
        Set<Participant> actual = controller.participants();

        // then
        assertThat(actual, is(expected));
    }

    @Test
    public void broadcast() {
        // given
        String message = "hi";
        ResponseEntity<String> ok = new ResponseEntity<>("ok", HttpStatus.OK);
        Set<Participant> expected = makeParticipants("1");
        when(participantRepository.all()).thenReturn(expected);

        // when
        assertThat(controller.broadcast(message), is(ok));

        // then
        verify(smsService, times(1)).sendText("1", message);
    }

    @Test
    public void postConstruct() {
        // given
        Set<Participant> expected = makeParticipants("1");
        when(participantRepository.all()).thenReturn(expected);

        // when
        controller.postConstruct();

        // then
        verify(smsService, times(1)).sendText("1", "Hey 1, send us a selfie! Check out the new version: http://singlestonedemo.com:8080");
    }

    @Test
    public void sms_newUser() {
        // given
        String from = "5551212";
        String body = "Jay";
        String selfUrl = "http://foo.com";
        String reply = "REPLY";

        when(smsService.getResponse(anyString())).thenReturn(reply);

        // when
        ResponseEntity<String> response = controller.sms(from, body, selfUrl);

        // then
        assertThat(response.getBody(), is(reply));
        verify(participantRepository, times(1)).save(new Participant(from));
    }

    @Test
    public void sms_existingUser() {
        // given
        String from = "5551212";
        String body = "Jay";
        String reply = "REPLY";

        when(participantRepository.get(from)).thenReturn(new Participant(from, "Jay"));
        when(smsService.getResponse(anyString())).thenReturn(reply);

        // when
        ResponseEntity<String> response = controller.sms(from, body, null);

        // then
        assertThat(response.getBody(), is(reply));
        verify(participantRepository, times(1)).save(new Participant(from));
    }

    private Set<Participant> makeParticipants(String... numbers) {
        Set<Participant> participants = new HashSet<>();
        for(String number : numbers) {
            participants.add(new Participant(number, number));
        }
        return participants;
    }
}
