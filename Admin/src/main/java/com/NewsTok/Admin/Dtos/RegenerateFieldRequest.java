package com.NewsTok.Admin.Dtos;

public class RegenerateFieldRequest {
    private Long reelsId;
    private String field;

    // Getters and setters
    public Long getReelsId() {
        return reelsId;
    }

    public void setReelsId(Long reelsId) {
        this.reelsId = reelsId;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}