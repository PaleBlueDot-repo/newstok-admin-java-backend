package com.NewsTok.Admin.Controllers;

import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class NewsController {
    @Autowired
    private NewsService newsService;


    @GetMapping("/testToken")
    public String testToken(){
        return "Hello I'm Testing Token";
    }

    @GetMapping("/newscontroller")
    public String getNewscontroller(){
        return "Hello I'm in the newscontroller";
    }


    @PostMapping("/getNews")
    public ResponseEntity<List<News> > createNews(@RequestParam String name, @RequestParam String category) {

        List<News> NewsList = newsService.createNews(name, category);

        return ResponseEntity.status(HttpStatus.CREATED).body(NewsList);

    }

    @GetMapping("/{id}")
    public ResponseEntity< List<News>> getNewsById(@PathVariable Long id) {
        List<News>  newsList =  newsService.getNewsById(id);
        return ResponseEntity.ok(newsList);
    }
}
