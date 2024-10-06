package com.devintel.gateway.cofiguration;

import com.devintel.gateway.dto.response.ApiResponse;
import com.devintel.gateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

// All requests passing through the Spring Cloud Gateway
@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    ObjectMapper objectMapper;

    @NonFinal
    String[] publicEndpoints = {
            "/identity/auth/.*",
            "/identity/users/registration",
    };

    @NonFinal
    @Value("${app.api-prefix}")
    String apiPrefix;

    /**
     * This method is called by the filter chain.
     * @param exchange: Information about the incoming request is available in the ServerWebExchange (exchange variable).
     * @param chain: This information has to passed to the filter chain.
     * @return This method returns a Mono<Void>.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Authentication filter is working.");

        if (isPublicEndpoint(exchange.getRequest())) {
           return chain.filter(exchange);
        }

        // Get token from authorization header.
        List<String> authHeaders = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if(CollectionUtils.isEmpty(authHeaders)) {
            return unAuthenticated(exchange.getResponse());
        }

        String token = authHeaders.getFirst().replace("Bearer", "");
        log.info("Token: " + token);
        // Verify token.
        // Delegate to identity service to verify token.
        // If token is valid, continue filter chain else return error 401.

        return identityService.introspect(token).flatMap(introspectResponse -> {
            if(introspectResponse.getResult().isValid()) {
                // It forwards the request to the next filter in the chain or the destination service.
                return chain.filter(exchange);
            } else {
                return unAuthenticated(exchange.getResponse());
            }
        }).onErrorResume(throwable -> unAuthenticated(exchange.getResponse()));

    }

    public boolean isPublicEndpoint(ServerHttpRequest request) {
        return Arrays.stream(publicEndpoints)
                .anyMatch(endpoint ->
                        request.getURI().getPath()
                                .matches(apiPrefix + endpoint)
                );
    }

    // Method that sets the filter’s priority.
    // The return value -1 indicates that this filter should be applied early in the filter chain.
    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unAuthenticated(ServerHttpResponse response) {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .code(1401)
                .message("UNAUTHENTICATED")
                .build();

        String body = null;
        try {
            body = objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

        return response.writeWith(Mono.just(response.bufferFactory().wrap((body.getBytes()))));
    }
}
