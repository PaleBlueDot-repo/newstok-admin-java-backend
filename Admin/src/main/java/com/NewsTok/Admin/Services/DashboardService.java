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

    @Value("${AdminToUserAuthentication.email}")
    private String email;

    @Value("${AdminToUserAuthentication.password}")
    private String password;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${dashboard.api.url}")
    private String dashboardApiUrl;  // Injecting the URL from properties

    public DashboardService() {

        this.restTemplate = new RestTemplate();
    }

     public DashBoard GetDashBoardData() {
         UserRequest userRe= new UserRequest();
         userRe.setEmail(this.email);
         userRe.setPassword(this.password);
         UserResponse userResponse =userLoginService.loginUser(userRe);
         String JwtToken=userResponse.getToken();

         String url = dashboardApiUrl;

         HttpHeaders headers = new HttpHeaders();
         headers.set("Authorization", "Bearer " + JwtToken);


         HttpEntity<DashBoard> requestEntity = new HttpEntity<>(headers);



         ResponseEntity<DashBoard> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, DashBoard.class);

         return responseEntity.getBody();
     }



}
