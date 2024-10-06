package com.devintel.gateway.repository;

import com.devintel.gateway.dto.request.IntrospectRequest;
import com.devintel.gateway.dto.response.ApiResponse;
import com.devintel.gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Component
public interface IdentityClient {
    @PostExchange(value = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
