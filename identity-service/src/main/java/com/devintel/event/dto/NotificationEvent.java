package com.devintel.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent {
    // third party: email, sms, zalo, ...
    String channel;
    String recipient;
    String templateCode;
    Map<String, Object> param;
    String subject;
    String body;
}
