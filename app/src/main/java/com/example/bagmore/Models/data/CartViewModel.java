package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class CartViewModel implements Serializable {

    @SerializedName("productId")
    private int productId;
    @SerializedName("productName")
    private String productName;
    @SerializedName("price")
    private BigDecimal price;
    @SerializedName("discount")
    private float discount;
    @SerializedName("amount")
    private int amount;
    @SerializedName("colorName")
    private String colorName;
    @SerializedName("sizeName")
    private String sizeName;

    @SerializedName("productImageViewModels")
    private List<ProductImageViewModel> productImageViewModels;

    public CartViewModel() {
    }

    public CartViewModel(int productId, String productName, BigDecimal price, float discount, int amount, String colorName, String sizeName, List<ProductImageViewModel> productImageViewModels) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.discount = discount;
        this.amount = amount;
        this.colorName = colorName;
        this.sizeName = sizeName;
        this.productImageViewModels = productImageViewModels;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public List<ProductImageViewModel> getProductImageViewModels() {
        return productImageViewModels;
    }

    public void setProductImageViewModels(List<ProductImageViewModel> productImageViewModels) {
        this.productImageViewModels = productImageViewModels;
    }
}
