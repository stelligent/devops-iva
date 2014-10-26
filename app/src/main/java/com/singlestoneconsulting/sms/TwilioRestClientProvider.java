package com.singlestoneconsulting.sms;

import com.singlestoneconsulting.util.Provider;
import com.twilio.sdk.TwilioRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TwilioRestClientProvider implements Provider<TwilioRestClient> {

    private final TwilioRestClient twilioRestClient;

    @Autowired
    public TwilioRestClientProvider(TwilioCredentials twilioCredentials) {
        this.twilioRestClient = new TwilioRestClient(twilioCredentials.getSid(), twilioCredentials.getAuthToken());
    }

    @Override
    public TwilioRestClient provide() {
        return twilioRestClient;
    }
}
