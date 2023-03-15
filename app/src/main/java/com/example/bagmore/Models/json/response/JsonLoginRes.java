package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.TokenViewModel;
import com.google.gson.annotations.SerializedName;

public class JsonLoginRes {
    @SerializedName("success")
    private String success;
    @SerializedName("message")
    private String message;
    @SerializedName("email")
    private String email;
    @SerializedName("role")
    private String role;
    @SerializedName("data")
    private TokenViewModel data;

    public JsonLoginRes(String success, String message, String email, String role, TokenViewModel data) {
        this.success = success;
        this.message = message;
        this.email = email;
        this.role = role;
        this.data = data;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public TokenViewModel getData() {
        return data;
    }

    public void setData(TokenViewModel data) {
        this.data = data;
    }
}
