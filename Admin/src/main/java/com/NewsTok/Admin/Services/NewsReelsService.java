package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Exception.GeminiApiResultNotFoundException;
import com.NewsTok.Admin.Models.GeminiApiResult;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.reactive.function.client.ClientResponse;

import java.util.Base64;
import java.util.Map;

@Service
public class NewsReelsService {
    @Autowired
    private WebClient webClient;


    @Value("${newsreels.api.process.url}")
    private String processApiEndpoint;

    @Value("${newsreels.api.image.url}")
    private String imageApiEndpoint;

    @Value("${newsreels.api.music.url}")
    private String musicApiEndpoint;

    @Value("${api.key}")
    private String apiKey;

    public GeminiApiResult createNewsReels(String inputText) {
        GeminiApiResult geminiApiResult = null;

        try {
            geminiApiResult = webClient.post()
                    .uri(processApiEndpoint)
                    .header("x-api-key", apiKey)
                    .bodyValue(Map.of("input_text", inputText))
                    .retrieve()
                    .bodyToMono(GeminiApiResult.class)
                    .block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new GeminiApiResultNotFoundException("Reels not created. Internal Server Error from ML server");
        }

        // Null check for the response
        if (geminiApiResult == null) {
            System.out.println("geminiApiResult is null");
            throw new GeminiApiResultNotFoundException("Reels not created ");
        }


        if (geminiApiResult.getSummary() == null || geminiApiResult.getSummary().length() <= 30) {
            geminiApiResult.setSummary("Summary not created");

            System.out.println("Summary is too small");
        }


        if (geminiApiResult.getImage_prompt() == null || geminiApiResult.getImage_prompt().length() <= 10) {
            geminiApiResult.setImage_prompt("Generate a striking image that captures the essence of a breaking news story. Include key elements like people, places, or objects related to the event, using colors and tone to match the mood of the news (e.g., serious, celebratory). Make the image visually engaging for a newsreel.");
            System.out.println("Image prompt is too small");
        }

        if (geminiApiResult.getFont_color() == null || geminiApiResult.getFont_color().length() >= 15) {
            geminiApiResult.setFont_color("#000000");
            System.out.println("Default Font color");
        }

        if (geminiApiResult.getFont_family() == null || geminiApiResult.getFont_family().length()>=15) {
            geminiApiResult.setFont_family("Arial");
            System.out.println("Default Font family");
        }
        if (geminiApiResult.getMusic_prompt() == null || geminiApiResult.getMusic_prompt().length() <= 10) {
            geminiApiResult.setMusic_prompt("Compose a short music track that complements a breaking news story. The music should reflect the tone of the news, whether serious, urgent, or uplifting, and be suitable for use as background music in a newsreel.");
            System.out.println("Default music prompt");
        }


        return geminiApiResult;
    }


    public String createReelsImage(String prompt) {
        byte[] imageBytes = webClient.post()
                .uri(imageApiEndpoint)
                .header("x-api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("prompt", prompt))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        ClientResponse::createException)
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray)
                .block();

        if (imageBytes != null) {
            // Encode the byte array as a Base64 string
            return Base64.getEncoder().encodeToString(imageBytes);
        }
        else{

            return null;
        }
    }

    public String createReelsMusic(String prompt) {
        byte[] musicBytes = webClient.post()
                .uri(musicApiEndpoint)
                .header("x-api-key", apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("text", prompt))
                .retrieve()
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        ClientResponse::createException)
                .bodyToMono(ByteArrayResource.class)
                .map(ByteArrayResource::getByteArray)
                .block();

        if (musicBytes != null) {
            // Encode the byte array as a Base64 string
            return Base64.getEncoder().encodeToString(musicBytes);
        } else {

            return null;
        }

    }





    }
