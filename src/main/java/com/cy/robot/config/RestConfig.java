package com.cy.robot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(jacksonSupportsMoreTypes());
        return restTemplate;
    }

    private HttpMessageConverter jacksonSupportsMoreTypes() {//eg. Gitlab returns JSON as plain text
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.parseMediaType("text/plain;charset=utf-8"), MediaType.APPLICATION_OCTET_STREAM));
        return converter;
    }
}
