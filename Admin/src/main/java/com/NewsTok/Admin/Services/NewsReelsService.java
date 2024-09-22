package com.NewsTok.Admin.Services;

import com.NewsTok.Admin.Dtos.RegenerateFieldRequest;
import com.NewsTok.Admin.Dtos.RegenerateMediaRequest;
import com.NewsTok.Admin.Exception.GeminiApiResultNotFoundException;
import com.NewsTok.Admin.Models.GeminiApiResult;

import com.NewsTok.Admin.Models.News;
import com.NewsTok.Admin.Repositories.NewsRepository;
import com.NewsTok.Admin.Repositories.ReelsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.springframework.web.reactive.function.client.ClientResponse;
import com.NewsTok.Admin.Models.Reels;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Service
public class NewsReelsService {
    @Autowired
    private WebClient webClient;

    @Autowired
    private ReelsRepository reelsRepository;

    @Autowired
    private NewsRepository newsRepository;


    @Value("${newsreels.api.process.url}")
    private String processApiEndpoint;

    @Value("${newsreels.api.image.url}")
    private String imageApiEndpoint;

    @Value("${newsreels.api.music.url}")
    private String musicApiEndpoint;

    @Value("${newsreels.api.single.process.url}")
    private String processSingleApiEndpoint;

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

    public String createReelsMusic(String prompt) throws IOException {
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
            String jsonResponse = new String(musicBytes, "UTF-8");

            // Parse JSON response
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Extract the 'music' field
            String musicBase64 = rootNode.path("music").asText();
            return musicBase64;
        } else {

            return null;
        }

    }

    public Reels updateReel(Reels reel){

       Reels temreels= reelsRepository.findByReelsId(reel.getReelsId());
       temreels.setBackground_color(reel.getBackground_color());
       temreels.setFont_family(reel.getFont_family());
       temreels.setSummary(reel.getSummary());
       temreels.setFont_color(reel.getFont_color());

       try{
           reelsRepository.save(temreels);

       }
       catch (Exception e){
           System.out.println(e.getMessage());
       }

        return  temreels;

    }

    public Reels generateImageMusic(RegenerateMediaRequest regenerateMediaRequest) throws IOException {
        Reels reel=reelsRepository.findByReelsId(regenerateMediaRequest.getReelsId());

        if( regenerateMediaRequest.getType().equals("music")){
            String music=this.createReelsMusic(regenerateMediaRequest.getPrompt());
            reel.setMusic(music);


        }
        else{
            String image=this.createReelsImage(regenerateMediaRequest.getPrompt());
            reel.setImage(image);

        }

        try{
            reelsRepository.save(reel);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        return  reel;

    }

    public String generatePromptForField(String fieldName, String existingValue) {
        switch (fieldName) {
            case "summary":
                return "Generate a concise summary  for the content: " + existingValue;
            case "background_color":
                return "Generate a background color (only color code in english) that matches the theme: " + existingValue;
            case "font_color":
                return "Suggest a suitable font color (only color code in english) that complements the theme: " + existingValue;
            case "font_family":
                return "Suggest an appropriate font family (only family name in english) that works well with the theme: " + existingValue;
            default:
                return "Generate a prompt based on the field " + fieldName;
        }
    }




    public Reels regenerateFields(RegenerateFieldRequest request) throws JsonProcessingException {
        Optional<Reels> reels=reelsRepository.findById(request.getReelsId());
        Reels reel=reels.get();
        Optional<News> newsS=newsRepository.findById(reel.getNewsId());
        News news=newsS.get();


        String inputText= generatePromptForField(request.getField(), news.getArticle());

        String AiApiResult = "";

        try {
            AiApiResult = webClient.post()
                    .uri(processSingleApiEndpoint)
                    .header("x-api-key", apiKey)
                    .bodyValue(Map.of("input_text", inputText))  // Send the generated prompt
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(AiApiResult);


        String extractedText = rootNode.get("text").asText();


        System.out.println("Extracted Text: " + extractedText);

       System.out.println(request.getField());

        if(request.getField().equals("summary")) {
            reel.setSummary(extractedText);

        }
        if(request.getField().equals("background_color")) {
            reel.setBackground_color(extractedText);

        }
        if(request.getField().equals("font_color")) {
            reel.setFont_color(extractedText);

        }
        if(request.getField().equals("font_family")) {
            reel.setFont_family(extractedText);

        }


          try{
              reelsRepository.save(reel);
          }
          catch (Exception e){
              System.out.println(e.getMessage());
          }

         return  reel;


    }







    }
