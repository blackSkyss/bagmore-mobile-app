package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.OrderViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonOrder {

    @SerializedName("success")
    private String success;

    @SerializedName("data")
    private List<OrderViewModel> orders;

    public JsonOrder() {
    }

    public JsonOrder(String success, List<OrderViewModel> orders) {
        this.success = success;
        this.orders = orders;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<OrderViewModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderViewModel> orders) {
        this.orders = orders;
    }
}
