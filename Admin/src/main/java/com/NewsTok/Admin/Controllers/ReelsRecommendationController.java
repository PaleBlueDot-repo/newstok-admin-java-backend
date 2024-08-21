package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Models.*;
import com.NewsTok.Admin.Repositories.ReelsRepository;
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

    @Autowired
    private ReelsRepository reelsRepository;

    @PostMapping("/getReelsRecommendation")
    public ResponseEntity<List<Reels>> getAllReels(@RequestBody UserInteractionRequest userInteractionRequest) {

        RecommendationResponse recommendationResponse=reelsrecommendationsService.getReelsRecommendation(userInteractionRequest);
        List<Long> recomList= recommendationResponse.getRecommendations();
        List<Reels> listOfReels=reelsRepository.findAllById(recomList);

        return ResponseEntity.ok(listOfReels);

    }



}
