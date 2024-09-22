package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Dtos.ReelsRequestDto;
import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Models.ScrapNewsResult;
import com.NewsTok.Admin.Repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public News getNewsById(Long id) {
        Optional<News> optionalNews = newsRepository.findById(id);
        return optionalNews.get();
    }

    @Autowired
    private WebClient webClient;

    @Value("${news.api.bangla.url}")
    private String banglaNewsApiUrl;

    @Value("${news.api.english.url}")
    private String englishNewsApiUrl;
    @Value("${api.key}")
    private String apiKey;

    public List<News> createNews(String name, String category) {
        String apiEndpoint;
        if (name.startsWith("Bangla")) {
            apiEndpoint = banglaNewsApiUrl;
        } else {
            apiEndpoint = englishNewsApiUrl;
        }

        // Call the Python API to get the scraped news data
        List<ScrapNewsResult> scrapedResults = webClient.get()
                .uri(apiEndpoint + "?topic={topic}", category)
                .header("x-api-key", apiKey)
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
        List<News> newsList = new ArrayList<>();

        // Iterate over the list of IDs
        for (Long id : ids.getIds()) {
            // Find the News by ID, returns an Optional
            Optional<News> optionalNews = newsRepository.findById(id);

            // If the News is found, add it to the list
            optionalNews.ifPresent(newsList::add);
        }

        return newsList;
    }


    public News updateNews(Long id, News updatedNews) {
        // Retrieve the existing news item
        Optional<News> optionalExistingNews = newsRepository.findById(id);

        // Throw an exception if the news item is not found
        if (optionalExistingNews.isEmpty()) {
            throw new RuntimeException("News not found with id " + id);
        }

        // Get the existing news item
        News existingNews = optionalExistingNews.get();

        // Update the fields
        try {
            existingNews.setTitle(updatedNews.getTitle());
            existingNews.setNewspaperName(updatedNews.getNewspaperName());
            existingNews.setPublished(updatedNews.getPublished());
            existingNews.setArticle(updatedNews.getArticle());

            // Save and return the updated news item
            return newsRepository.save(existingNews);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update news: " + e.getMessage());
        }
    }


    public void deleteNews(Long id) {
        // Check if the news item exists before attempting to delete
        if (newsRepository.findById(id).isEmpty()) {
            throw new RuntimeException("News not found with id " + id);
        }

        try {

            newsRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println("im in the exception");
            throw new RuntimeException("Failed to delete news with id " + id + ": " + e.getMessage());
        }
    }




}
