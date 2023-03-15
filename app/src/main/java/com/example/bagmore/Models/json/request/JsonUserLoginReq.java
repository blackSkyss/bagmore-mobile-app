package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

public class JsonUserLoginReq {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public JsonUserLoginReq(String email, String password) {
        this.email = email;
        this.password = password;
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
}



