package com.singlestoneconsulting.sms;

public interface SmsService {

    void sendText(String to, String message);

    String getResponse(String message);
}
