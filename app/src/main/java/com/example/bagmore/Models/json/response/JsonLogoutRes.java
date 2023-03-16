package com.example.bagmore.Models.json.response;

import com.google.gson.annotations.SerializedName;

public class JsonLogoutRes {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public JsonLogoutRes(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public JsonLogoutRes() {
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
