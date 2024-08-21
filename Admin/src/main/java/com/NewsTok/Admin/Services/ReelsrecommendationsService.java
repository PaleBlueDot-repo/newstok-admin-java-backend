package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Models.RecommendationResponse;
import com.NewsTok.Admin.Models.UserInteractionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReelsrecommendationsService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String apiEndpoint = "http://localhost:5000/ml/recommendReels";

    public RecommendationResponse getReelsRecommendation(UserInteractionRequest userInteractionRequest) {
        // Build the WebClient instance with a default configuration
        WebClient webClient = webClientBuilder.build();
        System.out.println(userInteractionRequest.getInterest());
        return webClient.post()
                .uri(apiEndpoint)
                .bodyValue(userInteractionRequest) // Use the proper request object
                .retrieve()
                .bodyToMono(RecommendationResponse.class) // Use the correct response DTO
                .block();
    }
}
