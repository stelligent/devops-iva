package com.singlestoneconsulting.sms;

import com.google.common.collect.Maps;
import com.singlestoneconsulting.util.Provider;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwilioSmsServiceTest {

    TwilioSmsService twilioSmsService;

    @Mock
    Provider<TwilioRestClient> twilioRestClientProvider;

    @Mock
    TwilioRestClient twilioRestClient;

    @Mock
    Account twilioAccount;

    @Mock
    SmsFactory smsFactory;

    @Before
    public void before() {
        when(twilioRestClientProvider.provide()).thenReturn(twilioRestClient);
        when(twilioRestClient.getAccount()).thenReturn(twilioAccount);
        when(twilioAccount.getSmsFactory()).thenReturn(smsFactory);

        twilioSmsService = new TwilioSmsService(twilioRestClientProvider);
    }

    @Test
    public void testSendText() throws TwilioRestException {
        // when
        twilioSmsService.sendText("5551212", "Hello");

        // then
        Map<String,String> params = new HashMap<>();
        params.put("From", "+18046328573");
        params.put("To", "5551212");
        params.put("Body", "Hello");
        MatchesMap hasAllTheBits = new MatchesMap(params);
        verify(smsFactory, only()).create(argThat(hasAllTheBits));
    }

    @Test
    public void testGetResponse() {
        // when
        String response = twilioSmsService.getResponse("Hello");

        // then
        assertThat(response, is("<Response><Sms>Hello</Sms></Response>"));
    }

    private class MatchesMap extends ArgumentMatcher<Map<String,String>> {
        Map<String,String> targetMap;

        private MatchesMap(Map<String,String> map) {
            this.targetMap = map;
        }

        @Override
        public boolean matches(Object argument) {
            Map<String,String> map = (Map<String,String>) argument;
            return Maps.difference(targetMap, map).areEqual();
        }
    }
}
