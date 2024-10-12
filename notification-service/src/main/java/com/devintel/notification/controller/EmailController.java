package com.devintel.notification.controller;

import com.devintel.notification.dto.request.SendEmailRequest;
import com.devintel.notification.dto.response.ApiResponse;
import com.devintel.notification.dto.response.EmailResponse;
import com.devintel.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    public ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .build();
    }

    @KafkaListener(topics = "onboard-successful")
    public void listen(String message) {
        log.info("Received message: " + message);
    }
}
