package com.NewsTok.Admin.Services;


import com.NewsTok.Admin.Models.DashBoard;
import com.NewsTok.Admin.Models.UserRequest;
import com.NewsTok.Admin.Models.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DashboardService {

    @Autowired
    private UserLoginService userLoginService;

    @Value("${user.email}")
    private String email;

    @Value("${user.password}")
    private String password;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Constructor-based dependency injection
    public DashboardService() {
        this.restTemplate = new RestTemplate(); // Initialize RestTemplate here
    }

     public DashBoard GetDashBoardData() {
         UserRequest userRe= new UserRequest();
         userRe.setEmail(this.email);
         userRe.setPassword(this.password);
         UserResponse userResponse =userLoginService.loginUser(userRe);
         String JwtToken=userResponse.getToken();

         String url = "http://localhost:8081/user/getDashboard";

         HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", "Bearer " + JwtToken);


         HttpEntity<DashBoard> requestEntity = new HttpEntity<>(headers);

         // Send GET request
//         here from user response will in Dashboard model format

         ResponseEntity<DashBoard> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, DashBoard.class);
//         ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

         // Return the response body (assuming it's a String, but you can change the type as needed)
         return responseEntity.getBody();
     }



}
