package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.CategoryViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonCategory {

    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private List<CategoryViewModel> data;

    public JsonCategory() {
    }

    public JsonCategory(String success, List<CategoryViewModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<CategoryViewModel> getData() {
        return data;
    }

    public void setData(List<CategoryViewModel> data) {
        this.data = data;
    }
}
