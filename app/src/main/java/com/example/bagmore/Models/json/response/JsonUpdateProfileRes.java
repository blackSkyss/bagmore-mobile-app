package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.UserProfileViewModel;
import com.google.gson.annotations.SerializedName;

public class JsonUpdateProfileRes {
    @SerializedName("message")
    private String message;
    @SerializedName("success")
    private boolean success;

    public JsonUpdateProfileRes(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JsonUpdateProfileRes() {
    }
}
