package com.devintel.notification.service;

import com.devintel.notification.dto.request.EmailRequest;
import com.devintel.notification.dto.request.SendEmailRequest;
import com.devintel.notification.dto.request.SenderRequest;
import com.devintel.notification.dto.response.EmailResponse;
import com.devintel.notification.exception.AppException;
import com.devintel.notification.exception.ErrorCode;
import com.devintel.notification.repository.httpclient.EmailClient;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${brevo.apiKey}")
    String apiKey;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(SenderRequest.builder()
                        .name("Devintel")
                        .email("manhnmhe171616@fpt.edu.vn")
                        .build())
                .to(Collections.singletonList(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (Exception e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
