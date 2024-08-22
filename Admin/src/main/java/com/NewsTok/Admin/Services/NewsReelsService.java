package com.NewsTok.Admin.Services;

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


    public GeminiApiResult createNewsReels(String inputText) {
        return webClient.post()
                .uri(processApiEndpoint)
                .bodyValue(Map.of("input_text", inputText))
                .retrieve()
                .bodyToMono(GeminiApiResult.class)
                .block();
    }

    public String createReelsImage(String prompt) {
        byte[] imageBytes = webClient.post()
                .uri(imageApiEndpoint)
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

        return null;
    }




}
