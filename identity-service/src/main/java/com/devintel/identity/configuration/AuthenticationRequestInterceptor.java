package com.devintel.identity.configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class AuthenticationRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();

        // Get bearer token in request header
        String requestHeader = "";
        if(requestAttributes != null) {
            requestHeader = requestAttributes.getRequest().getHeader("Authorization");
        }
        log.info("Request header: " + requestHeader);

        if(StringUtils.hasText(requestHeader)) {
            requestTemplate.header("Authorization", requestHeader);
        }
    }
}
