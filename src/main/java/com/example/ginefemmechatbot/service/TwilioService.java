package com.example.ginefemmechatbot.service;
import com.example.ginefemmechatbot.model.twilio.MessageModel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class TwilioService {
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.account.token}")
    private String AUTH_TOKEN ;
    @Value("${twilio.from.number}")
    private String FROM_NUMBER;

    /**
     *  MessageModel
     * for example :
     * "whatsapp:+5215661534821"
     * @return
     */
    public String sendEmailTwilio(MessageModel messageModel) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber(String.format("whatsapp:+521%s", messageModel.getToNumber())),
                        new com.twilio.type.PhoneNumber(FROM_NUMBER),
                        messageModel.getMessage()
                )
                .create();

        System.out.println(message.getSid());
        return message.getSid();
    }

}
