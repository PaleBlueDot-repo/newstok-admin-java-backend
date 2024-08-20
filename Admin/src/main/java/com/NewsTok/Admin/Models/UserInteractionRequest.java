package com.NewsTok.Admin.Models;

import java.util.List;

public class UserInteractionRequest {

    private int user_id;
    private List<Interaction> interactions;

    private  String interest;

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
    // Getters and Setters

    public static class Interaction {
        private int user_id;
        private int reels_id;
        private double score;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getReels_id() {
            return reels_id;
        }

        public void setReels_id(int reels_id) {
            this.reels_id = reels_id;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
        // Getters and Setters
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }
}
