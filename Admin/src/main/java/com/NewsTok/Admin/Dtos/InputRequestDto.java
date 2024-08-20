package com.NewsTok.Admin.Dtos;

public class InputRequestDto {

    private String input_text;

    public InputRequestDto(String inputText) {
        this.input_text = inputText;
    }

    public String getInputText() {
        return input_text;
    }

    public void setInputText(String inputText) {
        this.input_text = inputText;
    }
}
