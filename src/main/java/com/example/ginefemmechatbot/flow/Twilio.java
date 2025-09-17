package com.example.ginefemmechatbot.flow;

import com.example.ginefemmechatbot.service.TwilioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class Twilio {
    private final TwilioService twilioService;

    public Twilio(TwilioService twilioService) {
        this.twilioService = twilioService;
    }
    public Map<String, String> recievAnswerTwilio(Map<String, String> answersTwilioWebhook) {
        String[] fields = {"SmsMessageSid", "NumMedia", "ProfileName", "MessageType", "SmsSid",
                "WaId", "SmsStatus", "Body", "To", "MessagingServiceSid", "NumSegments",
                "ReferralNumMedia", "MessageSid", "AccountSid", "From", "OriginalRepliedMessageSid", "ApiVersion"
        };

        Map<String, String> responseJson = new HashMap<>();
        for (String field : fields) {
            responseJson.put(field, answersTwilioWebhook.getOrDefault(field, ""));
        }
        for (Map.Entry<String, String> entry : responseJson.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        try {
            String responseAll = new ObjectMapper().writeValueAsString(responseJson);
            byte[] responseBlob = responseAll.getBytes(StandardCharsets.UTF_8);


        } catch (Exception e) {
            return Map.of("error", "processing failed");
        }

        return responseJson;

    }

    public Map<String, String> flowAnswerTwilio(Map<String, String> answersTwilioWebhook) {
        Map<String, String> responseJson = recievAnswerTwilio(answersTwilioWebhook);

        if (responseJson == null || !responseJson.containsKey("Body")) {
            return Collections.emptyMap();
        }

        String body = responseJson.get("Body").trim();

        // Map de acciones según opción
        Map<String, Runnable> actions = Map.of(
                "1", this::accionOpcion1,
                "2", this::accionOpcion2,
                "3", this::accionOpcion3,
                "4", this::accionOpcion4
        );

        Runnable action = actions.get(body);
        if (action != null) {
            action.run(); // Ejecuta el método correspondiente
            return Collections.singletonMap("message", "Opción " + body + " ejecutada correctamente");
        } else {
            return Collections.singletonMap("message", "Opción no válida. Intenta de nuevo.");
        }
    }

    // Métodos para cada opción
    private void accionOpcion1() {
        System.out.println("Ejecutando acción para opción 1");
    }

    private void accionOpcion2() {
        System.out.println("Ejecutando acción para opción 2");
    }

    private void accionOpcion3() {
        String content_sid = "HXee6e1453c52577f17847dd5601d2f1d3";
        Map<String, Object> data = new HashMap<>();
        data.put("Sid", content_sid);
        twilioService.sendWhatsAppContentBuilder(content_sid, data);

    }

    private void accionOpcion4() {
        System.out.println("Ejecutando acción para opción 4");
    }


}
