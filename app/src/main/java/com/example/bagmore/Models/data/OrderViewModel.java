package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderViewModel implements Serializable {

    @SerializedName("id")
    private int id;
    @SerializedName("deliveryStatus")
    private int deliveryStatus;
    @SerializedName("orderedDate")
    private String orderDay;
    @SerializedName("shippingAddress")
    private String shipAddress;
    @SerializedName("status")
    private int status;
    @SerializedName("deliveryMethod")
    private String deliveryMethod;
    @SerializedName("products")
    private List<OrderItemViewModel> productOrders;

    public OrderViewModel() {
    }

    public OrderViewModel(int id, int deliveryStatus, String orderDay, String shipAddress, int status, String deliveryMethod, List<OrderItemViewModel> productOrders) {
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

    public List<OrderItemViewModel> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(List<OrderItemViewModel> productOrders) {
        this.productOrders = productOrders;
    }
}
