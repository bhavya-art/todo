package com.example.demo.models;

public class LoginResponse {
    private String token;
    private long expiresIn;

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this; // Returning 'this' for method chaining
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this; // Returning 'this' for method chaining
    }
}
