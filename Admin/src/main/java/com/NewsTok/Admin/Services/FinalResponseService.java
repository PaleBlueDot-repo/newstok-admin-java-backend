package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Models.FinalRecommendationResponse;
import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Models.Reels;
import com.NewsTok.Admin.Repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FinalResponseService {
    @Autowired
    private NewsRepository newsRepository;
    public List<FinalRecommendationResponse> PrepareData(List<Reels> listOfReels){
        List<FinalRecommendationResponse> listOfFinalResponse=new ArrayList<>();
        for(Reels eachReels : listOfReels){
            List<News> listOfNews=newsRepository.findById(eachReels.getNewsId());
            News news=listOfNews.get(0);
            FinalRecommendationResponse tem=new FinalRecommendationResponse();
            tem.setNews(news);
            tem.setReels(eachReels);
            listOfFinalResponse.add(tem);
        }

        return  listOfFinalResponse;

    }
}
