package com.devintel.notification.controller;

import com.devintel.event.dto.NotificationEvent;
import com.devintel.notification.dto.request.RecipientRequest;
import com.devintel.notification.dto.request.SendEmailRequest;
import com.devintel.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {
    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotification(NotificationEvent message) {
        log.info("Received message: " + message);
        SendEmailRequest request = SendEmailRequest.builder()
                .to(RecipientRequest.builder()
                        .email(message.getRecipient())
                        .build())
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build();

        emailService.sendEmail(request);
    }
}
