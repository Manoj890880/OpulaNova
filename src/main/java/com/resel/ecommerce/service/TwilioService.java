package com.resel.ecommerce.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

public class TwilioService {

    private final String accountSid;
    private final String authToken;
    private final String fromPhoneNumber;

    public TwilioService(String accountSid, String authToken, String fromPhoneNumber) {
        this.accountSid = accountSid;
        this.authToken = authToken;
        this.fromPhoneNumber = fromPhoneNumber;
        Twilio.init(accountSid, authToken);
    }

    public void sendSMS(String toPhoneNumber, String messageBody) {
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(toPhoneNumber),
                        new com.twilio.type.PhoneNumber(fromPhoneNumber),
                        messageBody)
                .create();

        System.out.println("SMS sent! SID: " + message.getSid());
    }
}
