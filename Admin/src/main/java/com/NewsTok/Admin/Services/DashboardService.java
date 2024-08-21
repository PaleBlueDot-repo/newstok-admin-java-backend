package com.NewsTok.Admin.Services;


import com.NewsTok.Admin.Models.DashBoard;
import com.NewsTok.Admin.Models.UserRequest;
import com.NewsTok.Admin.Models.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DashboardService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor-based dependency injection
    public DashboardService() {
        this.restTemplate = new RestTemplate(); // Initialize RestTemplate here
    }

     public String GetDashBoardData(String JwtToken) {
         String url = "http://localhost:8081/user/testToken";

         HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", "Bearer " + JwtToken);


         HttpEntity<String> requestEntity = new HttpEntity<>(headers);

         // Send GET request
         ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

         // Return the response body (assuming it's a String, but you can change the type as needed)
         return responseEntity.getBody();
     }



}
