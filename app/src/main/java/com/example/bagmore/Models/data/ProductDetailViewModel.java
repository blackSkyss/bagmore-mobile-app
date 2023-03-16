package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetailViewModel {
    @SerializedName("Name")
    public String Name;
    @SerializedName("Composition")
    public String Composition ;
    @SerializedName("Description")
    public String Description ;
    @SerializedName("ProductImages")
    public List<ProductImageViewModel> ProductImages ;
    @SerializedName("DescribeProducts")
    public  List<DescribeProductsViewModel> DescribeProducts;

    public ProductDetailViewModel(String name, String composition, String description, List<ProductImageViewModel> productImages, List<DescribeProductsViewModel> describeProducts) {
        Name = name;
        Composition = composition;
        Description = description;
        ProductImages = productImages;
        DescribeProducts = describeProducts;
    }

    public ProductDetailViewModel() {

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
