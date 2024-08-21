package com.NewsTok.Admin.Models;

import java.util.List;

public class RecommendationResponse {

    private List<Long> recommendations;
    private int user_id;

    public List<Long> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Long> recommendations) {
        this.recommendations = recommendations;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    // Getters and Setters
}
