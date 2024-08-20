package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Models.*;
import com.NewsTok.Admin.Services.ReelsrecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ReelsRecommendationController {

    @Autowired
    private ReelsrecommendationsService reelsrecommendationsService;

    @PostMapping("/getReelsRecommendation")
    public ResponseEntity<RecommendationResponse> getAllReels(@RequestBody UserInteractionRequest userInteractionRequest) {
        return ResponseEntity.ok(reelsrecommendationsService.getReelsRecommendation(userInteractionRequest));

    }



}
