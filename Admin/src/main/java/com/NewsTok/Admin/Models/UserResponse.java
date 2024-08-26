package com.NewsTok.Admin.Models;

public class UserResponse {
    private User user;
    private String token;

    // Getters and Setters
    public static class User {
        private Long userId; // Renamed from id to userId
        private String email;
        private String password;
        private String createdAt;
        private String name;
        private String interests; // Added field

        public Long getUserId() { // Renamed getter
            return userId;
        }

        public void setUserId(Long userId) { // Renamed setter
            this.userId = userId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInterests() {
            return interests;
        }

        public void setInterests(String interests) {
            this.interests = interests;
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
