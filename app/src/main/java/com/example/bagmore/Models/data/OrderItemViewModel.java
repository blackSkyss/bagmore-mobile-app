package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class OrderItemViewModel implements Serializable {

    @SerializedName("productId")
    private int productId;

    @SerializedName("productName")
    private String productName;

    @SerializedName("productSize")
    private String sizeName;

    @SerializedName("productColor")
    private String colorName;

    @SerializedName("price")
    private BigDecimal price;

    @SerializedName("amout")
    private int amount;

    @SerializedName("productImages")
    private List<ProductImageViewModel> productsImage;

    public OrderItemViewModel() {
    }

    public OrderItemViewModel(int productId, String productName, String sizeName, String colorName, BigDecimal price, int amount, List<ProductImageViewModel> productsImage) {
        this.productId = productId;
        this.productName = productName;
        this.sizeName = sizeName;
        this.colorName = colorName;
        this.price = price;
        this.amount = amount;
        this.productsImage = productsImage;
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

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<ProductImageViewModel> getProductsImage() {
        return productsImage;
    }

    public void setProductsImage(List<ProductImageViewModel> productsImage) {
        this.productsImage = productsImage;
    }
}
