package com.NewsTok.Admin.Models;

public class GeminiApiResult {
    private String background_color;
    private String font_color;
    private String font_family;
    private String image_prompt;
    private String music_prompt;
    private String summary;

    // Getters and Setters
    public String getBackground_color() {
        return background_color;
    }

    public void setBackground_color(String background_color) {
        this.background_color = background_color;
    }

    public String getFont_color() {
        return font_color;
    }

    public void setFont_color(String font_color) {
        this.font_color = font_color;
    }

    public String getFont_family() {
        return font_family;
    }

    public void setFont_family(String font_family) {
        this.font_family = font_family;
    }

    public String getImage_prompt() {
        return image_prompt;
    }

    public void setImage_prompt(String image_prompt) {
        this.image_prompt = image_prompt;
    }

    public String getMusic_prompt() {
        return music_prompt;
    }

    public void setMusic_prompt(String music_prompt) {
        this.music_prompt = music_prompt;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
