package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Dtos.weightDto;
import com.NewsTok.Admin.Models.*;
import com.NewsTok.Admin.Repositories.NewsRepository;
import com.NewsTok.Admin.Repositories.ReelsRepository;
import com.NewsTok.Admin.Services.FinalResponseService;
import com.NewsTok.Admin.Services.ReelsRecommendationOnInterest;
import com.NewsTok.Admin.Services.ReelsrecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/admin")
public class ReelsRecommendationController {

    @Autowired
    private ReelsrecommendationsService reelsrecommendationsService;

    @Autowired
    private ReelsRecommendationOnInterest reelsRecommendationOnInterest;

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FinalResponseService finalResponseService;
    @Value("${MaxNumberOfReels.n}")
    private int n;
    @PostMapping("/getReelsRecommendation")
    public ResponseEntity<List<FinalRecommendationResponse>> getAllReels(@RequestBody UserInteractionRequest userInteractionRequest) {

        List<Reels> finalListOfReels=new ArrayList<>();

//        RecommendationResponse recommendationResponse=reelsrecommendationsService.getReelsRecommendation(userInteractionRequest);
//        List<Long> recommendationList= recommendationResponse.getRecommendations();
//temList will be replace with recommendationList need to uncomment above 2 line
        List<Long> temList=new ArrayList<>();
        temList.add(1L);
        temList.add(2L);

        List<Reels> listOfReelsByMlRecommended=reelsRepository.findAllById(temList);
        List<Reels> reelsBasedOnInterest=reelsRecommendationOnInterest.getReelsRecommendationBasedOnInterest(userInteractionRequest.getInterest());
        Set<weightDto> reelsSet = new HashSet<>();

        Map<Long, weightDto> reelsMap = new HashMap<>();
         int weightCount=0;

        for(Reels eachReels : listOfReelsByMlRecommended){
            weightCount+=1;

            weightDto temDto=new weightDto();
            temDto.setReels(eachReels);
            temDto.setWeight(weightCount);

            reelsSet.add(temDto);

            if(!reelsMap.containsKey(eachReels.getReelsId())){
                reelsMap.put(eachReels.getReelsId(),temDto);
            }
        }


        for(Reels eachReels : reelsBasedOnInterest){
            weightCount+=1;
            weightDto temDto=new weightDto();
            temDto.setReels(eachReels);
            temDto.setWeight(weightCount);
            reelsSet.add(temDto);
            if(!reelsMap.containsKey(eachReels.getReelsId())){
                reelsMap.put(eachReels.getReelsId(),temDto);
            }
         }




        int cntOfRecom=finalListOfReels.size();
        int rem=0;

        if(cntOfRecom<this.n){
            rem=this.n-cntOfRecom;
        }

        List<Reels> extraReels=reelsRepository.findAll();
        Collections.shuffle(extraReels);

        for(Reels eachReels : extraReels){
            if(reelsSet.size()>rem){
                break;
            }
            weightCount+=1;
            weightDto temDto=new weightDto();
            temDto.setReels(eachReels);
            temDto.setWeight(weightCount);
            reelsSet.add(temDto);

            if(!reelsMap.containsKey(eachReels.getReelsId())){
                reelsMap.put(eachReels.getReelsId(),temDto);
            }

        }

        List<weightDto> weightList=new ArrayList<>();

//        for(weightDto eachSetData : reelsSet){
//            weightList.add(eachSetData);
//        }

        for (Map.Entry<Long, weightDto> entry : reelsMap.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue().getName());
            weightList.add(entry.getValue());
        }

        Collections.sort(weightList, new Comparator<weightDto>() {
            @Override
            public int compare(weightDto w1, weightDto w2) {
                return Integer.compare(w1.getWeight(), w2.getWeight());
            }
        });


        for(weightDto eachSetData : weightList){
            finalListOfReels.add(eachSetData.getReels());
        }

        return ResponseEntity.ok(finalResponseService.PrepareData(finalListOfReels));


    }



}
