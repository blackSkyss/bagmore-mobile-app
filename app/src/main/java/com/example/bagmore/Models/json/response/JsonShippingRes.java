package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ShippingAddressViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonShippingRes {

    @SerializedName("success")
    private String success;

    @SerializedName("result")
    private List<ShippingAddressViewModel> data;

    public JsonShippingRes() {
    }

    public JsonShippingRes(String success, List<ShippingAddressViewModel> data) {
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<ShippingAddressViewModel> getData() {
        return data;
    }

    public void setData(List<ShippingAddressViewModel> data) {
        this.data = data;
    }
}
