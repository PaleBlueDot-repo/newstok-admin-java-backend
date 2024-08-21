package com.NewsTok.Admin.Controllers;


import com.NewsTok.Admin.Dtos.newsreelsDto;
import com.NewsTok.Admin.Models.DashBoard;
import com.NewsTok.Admin.Models.Reels;
import com.NewsTok.Admin.Models.UserRequest;
import com.NewsTok.Admin.Models.UserResponse;
import com.NewsTok.Admin.Repositories.ReelsRepository;
import com.NewsTok.Admin.Services.DashboardService;
import com.NewsTok.Admin.Services.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class DashboardController {



    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private DashboardService dashboardService;


    @GetMapping("getDashboard")
    public ResponseEntity<DashBoard> getFullDashboardData(){

        return ResponseEntity.ok(dashboardService.GetDashBoardData());

    }


    @GetMapping("/getReelDetails/{reelsId}")
    public ResponseEntity<Reels> getReelDetails(@PathVariable Long reelsId) {

        Reels rlist=reelsRepository.findByReelsId(reelsId);
        System.out.println(rlist);
        return ResponseEntity.ok(rlist);
    }



}
