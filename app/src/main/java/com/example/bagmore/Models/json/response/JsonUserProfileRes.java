package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.Models.data.UserProfileViewModel;
import com.google.gson.annotations.SerializedName;

public class JsonUserProfileRes {
    @SerializedName("data")
    private UserProfileViewModel data;
    @SerializedName("success")
    private boolean success;

    public JsonUserProfileRes(UserProfileViewModel data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public JsonUserProfileRes() {
    }

    public UserProfileViewModel getData() {
        return data;
    }

    public void setData(UserProfileViewModel data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
