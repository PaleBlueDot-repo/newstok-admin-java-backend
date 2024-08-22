package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Models.UserRequest;
import com.NewsTok.Admin.Models.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserLoginService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${user.login.api.url}")
    private String userLoginApiUrl;

    // Constructor-based dependency injection
    public UserLoginService() {
        this.restTemplate = new RestTemplate(); // Initialize RestTemplate here
    }

    public UserResponse loginUser(UserRequest userRequest) {
        String url = userLoginApiUrl;

        // Convert UserRequest to JSON
        String userRequestJson = convertUserRequestToJson(userRequest);

        // Set the Content-Type header
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create an HttpEntity with the request body and headers
        HttpEntity<String> requestEntity = new HttpEntity<>(userRequestJson, headers);

        // Send POST request
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String responseJson = responseEntity.getBody();
//        System.out.println("Received JSON Response: " + responseJson);

        try {
            return objectMapper.readValue(responseJson, UserResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error as needed
            return null;
        }
    }

    private String convertUserRequestToJson(UserRequest userRequest) {
        try {
            return objectMapper.writeValueAsString(userRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}"; // Return empty JSON object in case of error
        }
    }
}
