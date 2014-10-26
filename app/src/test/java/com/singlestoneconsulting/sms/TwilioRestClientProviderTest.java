package com.singlestoneconsulting.sms;

import com.twilio.sdk.TwilioRestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwilioRestClientProviderTest {

    @Mock
    TwilioCredentials credentials;

    @Test
    public void test() {
        // given
        when(credentials.getSid()).thenReturn("AC11111111111111111111111111111111");
        when(credentials.getAuthToken()).thenReturn("AUTH1111111111111111111111111111");

        // when
        TwilioRestClientProvider provider = new TwilioRestClientProvider(credentials);

        // then
        assertThat(provider.provide(), isA(TwilioRestClient.class));
    }
}
