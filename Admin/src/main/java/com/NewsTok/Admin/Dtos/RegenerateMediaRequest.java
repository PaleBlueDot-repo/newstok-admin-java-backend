package com.NewsTok.Admin.Dtos;

public class RegenerateMediaRequest {
    private Long reelsId;
    private String type;
    private String prompt;

    // Getters and setters
    public Long getReelsId() {
        return reelsId;
    }

    public void setReelsId(Long reelsId) {
        this.reelsId = reelsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}