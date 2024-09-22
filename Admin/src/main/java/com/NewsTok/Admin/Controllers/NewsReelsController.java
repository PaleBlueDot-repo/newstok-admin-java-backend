package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Dtos.RegenerateFieldRequest;
import com.NewsTok.Admin.Dtos.RegenerateMediaRequest;
import com.NewsTok.Admin.Exception.GeminiApiResultNotFoundException;
import com.NewsTok.Admin.Exception.ImageProcessingException;
import com.NewsTok.Admin.Models.GeminiApiResult;
import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Models.Reels;
import com.NewsTok.Admin.Repositories.ReelsRepository;
import com.NewsTok.Admin.Services.NewsReelsService;
import com.NewsTok.Admin.Services.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            Boolean exceptionFlag=false;

            singleReel.setTitle(news.getTitle());
            singleReel.setNewsId(news.getId());

            List<Reels> reelsListFromDb =reelsRepository.findByNewsId(news.getId());

            if(reelsListFromDb.isEmpty()) {

                try {

                    GeminiApiResult geminiApiResult = newsReelsService.createNewsReels(news.getArticle());
                    String base64Image = newsReelsService.createReelsImage(geminiApiResult.getImage_prompt());
                    String base64Music=newsReelsService.createReelsMusic(geminiApiResult.getMusic_prompt());
                    singleReel.setBackground_color(geminiApiResult.getBackground_color());
                    singleReel.setFont_color(geminiApiResult.getFont_color());
                    singleReel.setFont_family(geminiApiResult.getFont_family());
                    singleReel.setSummary(geminiApiResult.getSummary());
                    singleReel.setSummary(geminiApiResult.getSummary());

                    singleReel.setImage(base64Image);
                    singleReel.setMusic(base64Music);

                } catch (GeminiApiResultNotFoundException e) {
                    // Handle the case where the Gemini API result is not found or invalid
                    System.err.println("Error creating news reels: " + e.getMessage());
                    exceptionFlag=true;

                } catch (ImageProcessingException e) {
                    System.err.println("Error creating image: " + e.getMessage());
                    exceptionFlag=true;


                } catch (Exception e) {

                    System.err.println("An unexpected error occurred: " + e.getMessage());
                    exceptionFlag=true;
                }




                if(!exceptionFlag)  reelsRepository.save(singleReel);
            }
            else{

                System.out.println("This reels Created before");
                singleReel=reelsListFromDb.get(0);
            }


          if(!exceptionFlag)  createdReels.add(singleReel);

//            System.out.println(base64Image);
        }


        return ResponseEntity.ok(createdReels);

    }

    @PostMapping("/updateFields")
    public ResponseEntity<Reels> updateFields(@RequestBody  Reels reels ){

        Reels updatedReel=newsReelsService.updateReel(reels);

        return ResponseEntity.ok(updatedReel);
    }

    @PostMapping("/regenerateField")
    public ResponseEntity<Reels> regenerateField(@RequestBody RegenerateFieldRequest request) throws JsonProcessingException {

       return  ResponseEntity.ok(newsReelsService.regenerateFields(request));
    }


    @PostMapping("/regenerateMedia")
    public ResponseEntity<Reels> regenerateMedia(@RequestBody RegenerateMediaRequest request) throws IOException {

        return  ResponseEntity.ok(newsReelsService.generateImageMusic(request));

    }



}
