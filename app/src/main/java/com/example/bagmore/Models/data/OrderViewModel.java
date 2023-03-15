package com.example.bagmore.Models.data;

import java.util.List;

public class OrderViewModel {
    private int id;
    private int deliveryStatus;
    private String orderDay;
    private String shipAddress;
    private int status;
    private String deliveryMethod;
    private List<ItemCartViewModel> productOrders;

    public OrderViewModel() {
    }

    public OrderViewModel(int id, int deliveryStatus, String orderDay, String shipAddress, int status, String deliveryMethod, List<ItemCartViewModel> productOrders) {
        this.id = id;
        this.deliveryStatus = deliveryStatus;
        this.orderDay = orderDay;
        this.shipAddress = shipAddress;
        this.status = status;
        this.deliveryMethod = deliveryMethod;
        this.productOrders = productOrders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(int deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getOrderDay() {
        return orderDay;
    }

    public void setOrderDay(String orderDay) {
        this.orderDay = orderDay;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public List<ItemCartViewModel> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<ItemCartViewModel> productOrders) {
        this.productOrders = productOrders;
    }
}
