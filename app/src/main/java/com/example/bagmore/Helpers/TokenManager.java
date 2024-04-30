package com.example.bagmore.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "TOKEN_PREFS";
    private static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public TokenManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    // save token
    public void saveToken(String accessToken, String refreshToken) {
        editor.putString(ACCESS_TOKEN, accessToken).apply();
        editor.putString(REFRESH_TOKEN, refreshToken).apply();
    }

    // get access token
    public  String getAccessToken() {
        return prefs.getString(ACCESS_TOKEN, null);
    }

    // get refresh token
    public String getRefreshToken() {
        return prefs.getString(REFRESH_TOKEN, null);
    }

    // clear token
    public void clearToken() {
        editor.remove(ACCESS_TOKEN).apply();
        editor.remove(REFRESH_TOKEN).apply();
    }

}
