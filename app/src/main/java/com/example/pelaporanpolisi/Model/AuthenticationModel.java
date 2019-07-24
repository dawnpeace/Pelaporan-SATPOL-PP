package com.example.pelaporanpolisi.Model;

public class AuthenticationModel {
    private String access_token;
    private String refresh_token;
    private int expired_in;

    public AuthenticationModel(String access_token, String refresh_token, int expired_in) {
        this.access_token = access_token;
        this.refresh_token = refresh_token;
        this.expired_in = expired_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public int getExpired_in() {
        return expired_in;
    }
}
