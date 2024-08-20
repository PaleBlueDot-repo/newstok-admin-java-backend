package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Models.GeminiApiResult;
import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Models.Reels;
import com.NewsTok.Admin.Repositories.ReelsRepository;
import com.NewsTok.Admin.Services.NewsReelsService;
import com.NewsTok.Admin.Services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class NewsReelsController {
    @Autowired
    private NewsReelsService newsReelsService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private ReelsRepository reelsRepository;


//    @GetMapping("/getAllReels")
//    public ResponseEntity<GeminiApiResult> getAllReels(@RequestParam String content) {
//        System.out.println("hello");
//        GeminiApiResult ReelsList = newsReelsService.createNewsReels(content);
//        return ResponseEntity.ok(ReelsList);
//    }

    @PostMapping("/getAllReels")
    public  ResponseEntity< List<Reels> >  getAllReels(@RequestBody ReelsRequestDto requestDto) {
        System.out.println("Received IDs: " + requestDto.getIds());
        List<News> listOfNews=newsService.getNewsByIds(requestDto);
        List<Reels> createdReels= new ArrayList<>();
        for (News news : listOfNews) {

            Reels singleReel=new Reels();


            singleReel.setTitle(news.getTitle());
            singleReel.setNewsId(news.getId());

            List<Reels> reelsListFromDb =reelsRepository.findByNewsId(news.getId());

            if(reelsListFromDb.isEmpty()) {
                GeminiApiResult geminiApiResult = newsReelsService.createNewsReels(news.getArticle());
                singleReel.setBackground_color(geminiApiResult.getBackground_color());
                singleReel.setFont_color(geminiApiResult.getFont_color());
                singleReel.setFont_family(geminiApiResult.getFont_family());
                singleReel.setSummary(geminiApiResult.getSummary());
                singleReel.setSummary(geminiApiResult.getSummary());

                String base64Image=newsReelsService.createReelsImage(geminiApiResult.getImage_prompt());
                singleReel.setImage(base64Image);


                reelsRepository.save(singleReel);
            }
            else{

                System.out.println("This reels Created before");
                singleReel=reelsListFromDb.get(0);
            }

            createdReels.add(singleReel);

//            System.out.println(base64Image);
        }


        return ResponseEntity.ok(createdReels);

    }




}
