package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.OrderViewModel;

import java.util.List;

public class JsonOrder {
    private int success;
    private List<OrderViewModel> orders;

    public JsonOrder() {
    }

    public JsonOrder(int success, List<OrderViewModel> orders) {
        this.success = success;
        this.orders = orders;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public List<OrderViewModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderViewModel> orders) {
        this.orders = orders;
    }
}
