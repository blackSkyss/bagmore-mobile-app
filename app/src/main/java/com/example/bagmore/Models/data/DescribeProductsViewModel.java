package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class DescribeProductsViewModel {
    @SerializedName("Color")
    public ColorProductDetailViewModel Color;
    @SerializedName("Size")
    public SizeProductDetailViewModel Size;
    @SerializedName("Price")
    public BigDecimal Price;

    public DescribeProductsViewModel(ColorProductDetailViewModel color, SizeProductDetailViewModel size, BigDecimal price) {
        Color = color;
        Size = size;
        Price = price;
    }

    public ColorProductDetailViewModel getColor() {
        return Color;
    }

    public void setColor(ColorProductDetailViewModel color) {
        Color = color;
    }

    public SizeProductDetailViewModel getSize() {
        return Size;
    }

    public void setSize(SizeProductDetailViewModel size) {
        Size = size;
    }

    public BigDecimal getPrice() {
        return Price;
    }

    public void setPrice(BigDecimal price) {
        Price = price;
    }
}
