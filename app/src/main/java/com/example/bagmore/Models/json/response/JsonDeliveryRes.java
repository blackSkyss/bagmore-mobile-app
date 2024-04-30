package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.DeliveryMethodViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonDeliveryRes {

    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private List<DeliveryMethodViewModel> data;

    public JsonDeliveryRes() {
    }

    public JsonDeliveryRes(String success, List<DeliveryMethodViewModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DeliveryMethodViewModel> getData() {
        return data;
    }

    public void setData(List<DeliveryMethodViewModel> data) {
        this.data = data;
    }
}
