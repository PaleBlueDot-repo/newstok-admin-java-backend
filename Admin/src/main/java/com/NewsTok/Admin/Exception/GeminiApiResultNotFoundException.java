package com.NewsTok.Admin.Exception;

public class GeminiApiResultNotFoundException extends RuntimeException  {
    public GeminiApiResultNotFoundException(String message) {
        super(message);
    }

    public GeminiApiResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
