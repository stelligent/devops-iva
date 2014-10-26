package com.singlestoneconsulting.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwilioCredentials {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioCredentials.class);

    private final String sid;
    private final String authToken;

    public TwilioCredentials() {
        String sid = System.getenv("TWILIO_SID");
        if (sid == null) {
            sid = System.getProperty("twilio.sid");
        }
        this.sid = sid;

        String authToken = System.getenv("TWILIO_AUTH_TOKEN");
        if (authToken == null) {
            authToken = System.getProperty("twilio.authToken");
        }
        this.authToken = authToken;

        if (this.sid == null || this.authToken == null) {
            LOGGER.error("Twilio secrets not found");
            throw new IllegalStateException("Twilio secrets not found.");
        }
    }

    public String getSid() {
        return sid;
    }

    public String getAuthToken() {
        return authToken;
    }
}