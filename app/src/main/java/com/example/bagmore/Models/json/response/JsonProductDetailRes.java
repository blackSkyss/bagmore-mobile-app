package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.google.gson.annotations.SerializedName;

public class JsonProductDetailRes {

    @SerializedName("productDetail")
    private ProductDetailViewModel data;
    @SerializedName("success")
    private boolean success;


    public ProductDetailViewModel getData() {
        return data;
    }

    public void setData(ProductDetailViewModel data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public JsonProductDetailRes(ProductDetailViewModel data, boolean success) {
        this.data = data;
        this.success = success;
    }
}
