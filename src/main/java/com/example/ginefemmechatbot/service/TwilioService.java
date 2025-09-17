package com.example.ginefemmechatbot.service;
import com.example.ginefemmechatbot.model.twilio.MessageModel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Service
public class TwilioService {
    @Value("${twilio.account.sid}")
    private String ACCOUNT_SID;
    @Value("${twilio.account.token}")
    private String AUTH_TOKEN;
    @Value("${twilio.from.number}")
    private String FROM_NUMBER;

    /**
     * MessageModel
     * for example :
     * "whatsapp:+5215661534821"
     *
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
    public String sendWhatsAppContentBuilder(
             String content_sid,
             Map<String, Object> data) {


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        try {
            Message message;
            if (data != null && !data.isEmpty()) {
                JSONObject contentVars = new JSONObject(data);
                System.out.println("data not is empty" + contentVars.toString());
                message = Message.creator(
                                new PhoneNumber("whatsapp:+521"+"5522056173"),
                                new PhoneNumber(FROM_NUMBER),
                                //                      MSG_SERVICE_SID,
                                (String) null
                        )
                        .setContentSid(content_sid)
                        .setContentVariables(contentVars.toString())
                        .create();
            } else {
                System.out.println("data is empty");
                message = Message.creator(
                                new PhoneNumber("whatsapp:" + "+5215522056173"),
                                new PhoneNumber(FROM_NUMBER),
                                //MSG_SERVICE_SID,
                                content_sid)

                        .create();
            }

            return "Mensaje enviado con SID: " + message.getSid();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar mensaje: " + e.getMessage();
        }
    }


}