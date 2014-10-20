package com.singlestoneconsulting.sms;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.verbs.Sms;
import com.twilio.sdk.verbs.TwiMLException;
import com.twilio.sdk.verbs.TwiMLResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TwilioSmsService implements SmsService {
    private static final String SID = "AC203eee847735dbdcd1a70461ef323052";
    private static final String AUTH_TOKEN = "631e0b6354889bea892f9376255b824e";
    private static final String FROM = "+18046328573";

    private final TwilioRestClient twilio;

    public TwilioSmsService() {
        this.twilio = new TwilioRestClient(SID, AUTH_TOKEN);
    }

    @Override
    public void sendText(String to, String message) {
        HashMap<String, String> params = new HashMap<>();
        params.put("From", FROM);
        params.put("To", to);
        params.put("Body", message);

        try {
            twilio.getAccount().getSmsFactory().create(params);
        } catch (TwilioRestException e) {
            // TODO Log exception
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getResponse(String message) {
        TwiMLResponse twiml = new TwiMLResponse();
        try {
            twiml.append(new Sms(message));
        } catch (TwiMLException e) {
            // TODO Log exception
            throw new IllegalStateException(e);
        }
        return twiml.toXML();
    }
}
