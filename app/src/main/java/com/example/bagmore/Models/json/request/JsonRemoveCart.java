package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

public class JsonRemoveCart {

    @SerializedName("productId")
    private int productId;

    @SerializedName("color")
    private String color;

    @SerializedName("amount")
    private int amount;

    @SerializedName("size")
    private String size;

    public JsonRemoveCart() {
    }

    public JsonRemoveCart(int productId, String color, int amount, String size) {
        this.productId = productId;
        this.color = color;
        this.amount = amount;
        this.size = size;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
