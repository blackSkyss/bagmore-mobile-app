package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailViewModel {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String Name;
    @SerializedName("composition")
    public String Composition;
    @SerializedName("description")
    public String Description;
    @SerializedName("productImages")
    public List<ProductImageViewModel> ProductImages;
    @SerializedName("describeProducts")
    public List<DescribeProductsViewModel> DescribeProducts;

    public ProductDetailViewModel() {

    }

    public ProductDetailViewModel(int id, String name, String composition, String description, List<ProductImageViewModel> productImages, List<DescribeProductsViewModel> describeProducts) {
        this.id = id;
        Name = name;
        Composition = composition;
        Description = description;
        ProductImages = productImages;
        DescribeProducts = describeProducts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getComposition() {
        return Composition;
    }

    public void setComposition(String composition) {
        Composition = composition;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public List<ProductImageViewModel> getProductImages() {
        return ProductImages;
    }

    public void setProductImages(List<ProductImageViewModel> productImages) {
        ProductImages = productImages;
    }

    public List<DescribeProductsViewModel> getDescribeProducts() {
        return DescribeProducts;
    }

    public void setDescribeProducts(List<DescribeProductsViewModel> describeProducts) {
        DescribeProducts = describeProducts;
    }
}
