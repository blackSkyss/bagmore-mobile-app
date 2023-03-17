package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

public class JsonAddToCartReq {
    @SerializedName("productId")
    private int productId;

    @SerializedName("color")
    private String colorName;

    @SerializedName("amount")
    private int amount;

    @SerializedName("size")
    private String sizeName;

    public JsonAddToCartReq() {
    }

    public JsonAddToCartReq(int productId, String colorName, int amount, String sizeName) {
        this.productId = productId;
        this.colorName = colorName;
        this.amount = amount;
        this.sizeName = sizeName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }
}
