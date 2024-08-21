package com.NewsTok.Admin.Models;

import java.util.List;

public class RecommendationResponse {

    private List<Integer> recommendations;
    private int user_id;

    public List<Integer> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Integer> recommendations) {
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
