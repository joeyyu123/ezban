package com.ezban.member.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {

    @Value("${google.recaptcha.secret}")
    private String recaptchaSecret;

    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    private final RestTemplate restTemplate;

    public CaptchaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean verifyCaptcha(String recaptchaResponse) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = new HashMap<>();
        body.put("secret", recaptchaSecret);
        body.put("response", recaptchaResponse);

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                RECAPTCHA_VERIFY_URL,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            Map<String, Object> response = responseEntity.getBody();

            if (response != null && response.containsKey("success")) {
                Object successObject = response.get("success");
                if (successObject instanceof Boolean) {
                    return (Boolean) successObject;
                }
            }

            // Log failure and return false if verification failed
            System.err.println("Captcha verification failed: " + response);
            return false;
        } catch (Exception e) {
            // Log exception details
            System.err.println("Exception during captcha verification: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
