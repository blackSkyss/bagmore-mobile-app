package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.CartViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonCartRes {
    @SerializedName("success")
    private String success;
    @SerializedName("data")
    private List<CartViewModel> data;

    public JsonCartRes() {
    }

    public JsonCartRes(String success, List<CartViewModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CartViewModel> getData() {
        return data;
    }

    public void setData(List<CartViewModel> data) {
        this.data = data;
    }
}
