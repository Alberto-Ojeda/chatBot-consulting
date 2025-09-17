package com.example.ginefemmechatbot.restController;

import com.example.ginefemmechatbot.model.twilio.MessageModel;
import com.example.ginefemmechatbot.service.TwilioService;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.ginefemmechatbot.flow.Twilio;
import java.util.Map;

@RestController
public class TwilioController {

    private final TwilioService twilioService;
    private final Twilio twilio;
    public TwilioController(TwilioService twilioService, Twilio twilio) {
        this.twilioService = twilioService;
        this.twilio = twilio;
    }


    @PostMapping("sendMail")
    public String sendMessage(@RequestBody MessageModel messageModel ) {
        twilioService.sendEmailTwilio(messageModel);
    return  "Hello World";
    }

        @PostMapping("/send")
        public String sendWhatsAppMessage(
                @RequestParam String content_sid,
                @RequestBody(required = false) Map<String, Object> data) {

          return twilioService.sendWhatsAppContentBuilder(content_sid, data);

        }
    @PostMapping("/ginecologia/webhook")
    public ResponseEntity<Map<String, String>> answerTwilio(@RequestParam Map<String, String> requestParams) {
        try {
            return ResponseEntity.ok(twilio.flowAnswerTwilio(requestParams));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }


}
