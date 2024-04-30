package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

public class JsonUserProfileReq {
    @SerializedName("email")
    private String email;

    public JsonUserProfileReq(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
