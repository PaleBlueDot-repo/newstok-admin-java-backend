package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Models.ScrapNewsResult;
import com.NewsTok.Admin.Repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public List<News> getNewsById(Long id) {
        return newsRepository.findById(id);
    }

    @Autowired
    private WebClient webClient;

    public List<News> createNews(String name, String category) {
        String apiEndpoint;
        if (name.startsWith("Bangla")) {
            apiEndpoint = "/scraping/scrape_bangla_news";
        } else {
            apiEndpoint = "/scraping/scrape_english_news";
        }

        // Call the Python API to get the scraped news data
        List<ScrapNewsResult> scrapedResults = webClient.get()
                .uri(apiEndpoint + "?topic={topic}", category)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<ScrapNewsResult>>>() {})
                .block()
                .get("results");


        List<News> scrapedNews = scrapedResults.stream().map(result -> {
            News news = new News();
            news.setArticle(result.getArticle());
            news.setLink(result.getLink());
            news.setPublished(result.getPublished());
            news.setTitle(result.getTitle());
            news.setNewspaperName(name);
            news.setCategory(category);
            return news;

        }).collect(Collectors.toList());

        for (News news : scrapedNews) {
            List<News> existingNews = newsRepository.findByTitleAndLink(news.getTitle(), news.getLink());
            if (existingNews.isEmpty()) {
                newsRepository.save(news);
                System.out.println("Saved news: " + news);
            } else {
                System.out.println("Duplicate news found, not saving: " + news);
            }

        }
        return scrapedNews;
    }


    public List<News> getAllNews() {
        return  newsRepository.findAll();
    }

    public List<News> getNewsByIds(ReelsRequestDto ids) {


        List<News> temList = new ArrayList<>();

        // Iterate over the list of IDs
        for (Long id : ids.getIds()) {
            // Find the News by ID, returns an Optional
            List<News> singleNews = newsRepository.findById(id);

            // If the News is found, add it to the list
            temList.add(singleNews.get(0));

        }

        return temList;


    }



}
