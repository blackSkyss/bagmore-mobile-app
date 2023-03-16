package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.Models.data.SizeViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonProductDetailRes {
    @SerializedName("success")
    private boolean success;

    @SerializedName("productDetail")
    private ProductDetailViewModel productDetail;

    public JsonProductDetailRes(boolean success, ProductDetailViewModel productDetail) {
        this.success = success;
        this.productDetail = productDetail;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ProductDetailViewModel getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetailViewModel productDetail) {
        this.productDetail = productDetail;
    }
}
