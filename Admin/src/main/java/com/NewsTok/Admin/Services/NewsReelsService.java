package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Models.GeminiApiResult;

import org.springframework.beans.factory.annotation.Autowired;

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


    private final String apiEndpoint = "http://127.0.0.1:5000/ml/process-data";

    public GeminiApiResult createNewsReels(String inputText) {
        return webClient.post()
                .uri(apiEndpoint)
                .bodyValue(Map.of("input_text", inputText))
                .retrieve()
                .bodyToMono(GeminiApiResult.class)
                .block();
    }

    public String createReelsImage(String prompt) {
        byte[] imageBytes = webClient.post()
                .uri("http://127.0.0.1:5000/ml/generate-image")
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
