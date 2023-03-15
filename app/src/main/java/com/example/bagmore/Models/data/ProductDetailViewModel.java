package com.example.bagmore.Models.data;

import java.util.List;

public class ProductDetailViewModel {
    public String Name;
    public String Composition ;
    public String Description ;
    public List<ProductImageViewModel> ProductImages ;
    public  List<DescribeProductsViewModel> DescribeProducts;

    public ProductDetailViewModel(String name, String composition, String description, List<ProductImageViewModel> productImages, List<DescribeProductsViewModel> describeProducts) {
        Name = name;
        Composition = composition;
        Description = description;
        ProductImages = productImages;
        DescribeProducts = describeProducts;
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
