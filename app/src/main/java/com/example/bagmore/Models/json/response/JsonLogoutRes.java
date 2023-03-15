package com.example.bagmore.Models.json.response;

import com.google.gson.annotations.SerializedName;

public class JsonLogoutRes {
    @SerializedName("success")
    private String success;

    @SerializedName("message")
    private String message;

    public JsonLogoutRes(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
