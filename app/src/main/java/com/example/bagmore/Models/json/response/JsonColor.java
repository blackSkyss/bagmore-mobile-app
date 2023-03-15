package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ColorViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonColor {
    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private List<ColorViewModel> data;

    public JsonColor() {
    }

    public JsonColor(String success, List<ColorViewModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ColorViewModel> getData() {
        return data;
    }

    public void setData(List<ColorViewModel> data) {
        this.data = data;
    }
}
