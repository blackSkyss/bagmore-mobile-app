package com.example.bagmore.Models.json.response;

import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.Models.data.SizeViewModel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonProductDetailRes {

    @SerializedName("data")
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
