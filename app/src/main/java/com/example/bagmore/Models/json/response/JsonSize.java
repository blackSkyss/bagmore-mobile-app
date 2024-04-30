package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.SizeViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonSize {

    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private List<SizeViewModel> data;

    public JsonSize() {
    }

    public JsonSize(String success, List<SizeViewModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<SizeViewModel> getData() {
        return data;
    }

    public void setData(List<SizeViewModel> data) {
        this.data = data;
    }
}
