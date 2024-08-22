package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Models.Reels;
import com.NewsTok.Admin.Repositories.NewsRepository;
import com.NewsTok.Admin.Repositories.ReelsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReelsRecommendationOnInterest {
    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private NewsRepository newsRepository;

    public List<Reels> getReelsRecommendationBasedOnInterest(String Interest){
        List<Reels> RecommendedReelsList=new ArrayList<>();
        String[] interestsArray = Interest.split(",");

        List<Reels> listOfReels=reelsRepository.findAll();
        for (Reels eachReels : listOfReels) {
           Long NewsId=eachReels.getNewsId();
           List<News> newslist=newsRepository.findById(NewsId);
           if(!newslist.isEmpty()) {
               News news = newslist.get(0);
               for (String eachInterest : interestsArray) {
                   System.out.println(eachInterest);
                   if (news.getCategory().equals(eachInterest)) {
                       RecommendedReelsList.add(eachReels);
                       System.out.println(eachReels.getReelsId());
                       break;

                   }
               }

           }


        }

        return RecommendedReelsList;

    }
}
