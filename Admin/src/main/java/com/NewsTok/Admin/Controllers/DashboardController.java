package com.NewsTok.Admin.Controllers;


import com.NewsTok.Admin.Dtos.newsreelsDto;
import com.NewsTok.Admin.Models.DashBoard;
import com.NewsTok.Admin.Models.UserRequest;
import com.NewsTok.Admin.Models.UserResponse;
import com.NewsTok.Admin.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class DashboardController {

    @Autowired
    private UserLoginService userLoginService;
    @Value("${user.email}")
    private String email;

    @Value("${user.password}")
    private String password;

    @GetMapping("getDashboard")
    public ResponseEntity<DashBoard> getFullDashboardData(){

        newsreelsDto reel1 = new newsreelsDto();
        reel1.setReelsId("23");
        reel1.setLikes("10");
        reel1.setStatus("1");
        reel1.setViews("20");

        List<newsreelsDto> reelsList = new ArrayList<>();
        reelsList.add(reel1);

        DashBoard dashboard = new DashBoard();
        dashboard.setNewsReel_Views("60.5k");
        dashboard.setWatchtime("320");
        dashboard.setPublished("70");
        dashboard.setLikes("150");
        dashboard.setReelsList(reelsList);
        UserRequest userRe= new UserRequest();
        userRe.setEmail(this.email);
        userRe.setPassword(this.password);
        UserResponse userResponse =userLoginService.loginUser(userRe);
        System.out.println(userResponse.getUser().getUserId());
        System.out.println(email+password);


        return ResponseEntity.ok(dashboard);

    }

}
