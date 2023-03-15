package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.google.gson.annotations.SerializedName;

public class JsonRefreshTokenRes {
    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private TokenRefreshViewModel data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public TokenRefreshViewModel getData() {
        return data;
    }

    public void setData(TokenRefreshViewModel data) {
        this.data = data;
    }

    public JsonRefreshTokenRes(String success, TokenRefreshViewModel data) {
        this.success = success;
        this.data = data;
    }


}
