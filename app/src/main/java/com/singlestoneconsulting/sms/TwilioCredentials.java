package com.singlestoneconsulting.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public final class TwilioCredentials implements SmsCredentials {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioCredentials.class);

    private final String sid;
    private final String authToken;

    public TwilioCredentials() {
        String twilioSid = System.getenv("TWILIO_SID");
        if (twilioSid == null) {
            twilioSid = System.getProperty("twilio.sid");
        }
        this.sid = twilioSid;

        String twilioAuthToken = System.getenv("TWILIO_AUTH_TOKEN");
        if (twilioAuthToken == null) {
            twilioAuthToken = System.getProperty("twilio.authToken");
        }
        this.authToken = twilioAuthToken;

        if (this.sid == null || this.authToken == null) {
            LOGGER.error("Twilio secrets not found");
            throw new IllegalStateException("Twilio secrets not found.");
        }
    }

    @Override
    public String getSid() {
        return sid;
    }

    @Override
    public String getAuthToken() {
        return authToken;
    }
}
