package com.example.ginefemmechatbot.restController;

import com.example.ginefemmechatbot.model.twilio.MessageModel;
import com.example.ginefemmechatbot.service.TwilioService;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class TwilioController {

    private final TwilioService twilioService;

    public TwilioController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }


    @PostMapping("sendMail")
    public String sendMessage(@RequestBody MessageModel messageModel ) {
        twilioService.sendEmailTwilio(messageModel);


    return  "Hello World";
    }
}
