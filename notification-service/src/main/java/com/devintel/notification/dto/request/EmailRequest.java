package com.devintel.notification.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailRequest {
    SenderRequest sender;
    List<RecipientRequest> to;
    String subject;
    String htmlContent;
}
