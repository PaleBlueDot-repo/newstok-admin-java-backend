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

        Set<weightDto> reelsSet = new HashSet<>();
//       reelsMap will only send unique reels
        Map<Long, weightDto> reelsMap = new HashMap<>();

        int weightCount = 0;
//       weightCount will help to sort the reels from best recommended reels to worst


//       reelsrecommendationsService will get  recommendations from ML recommender
        if(!userInteractionRequest.getInteractions().isEmpty()) {


            RecommendationResponse recommendationResponse = reelsrecommendationsService.getReelsRecommendation(userInteractionRequest);
            List<Long> recommendationList = recommendationResponse.getRecommendations();


            List<Reels> listOfReelsByMlRecommended = reelsRepository.findAllById(recommendationList);

//   reelsRecommendationOnInterest  will recommend reels based on  user Interest


            //      saving unique reels only
            for (Reels eachReels : listOfReelsByMlRecommended) {
                weightCount += 1;
                weightDto temDto = new weightDto();
                temDto.setReels(eachReels);
                temDto.setWeight(weightCount);
                if (!reelsMap.containsKey(eachReels.getReelsId())) {
                    reelsMap.put(eachReels.getReelsId(), temDto);
                }
            }

        }


//      saving unique reels only
        List<Reels> reelsBasedOnInterest=reelsRecommendationOnInterest.getReelsRecommendationBasedOnInterest(userInteractionRequest.getInterest());

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



//      we will select best n reels to send to user feed
        int cntOfRecom=reelsMap.size();
        int rem=0;

        if(cntOfRecom<this.n){
            rem=this.n-cntOfRecom;
        }

        List<Reels> extraReels=reelsRepository.findAll();

        Collections.shuffle(extraReels);
//lastly check on whole reels database and added to reelsMap  for remaining reels to fill max reels number(n)
        for(Reels eachReels : extraReels){
            if(reelsMap.size()>rem){
                break;
            }

            weightCount+=1;
            weightDto temDto=new weightDto();
            temDto.setReels(eachReels);
            temDto.setWeight(weightCount);


            if(!reelsMap.containsKey(eachReels.getReelsId())){
                reelsMap.put(eachReels.getReelsId(),temDto);
            }

        }

        List<weightDto> weightList=new ArrayList<>();

        for (Map.Entry<Long, weightDto> entry : reelsMap.entrySet()) {
            weightList.add(entry.getValue());
        }


//      sorting based on best weight score (low score is best)
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
