package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class ProductImageViewModel {
    @SerializedName("Source")
    public byte[] Source;

    public ProductImageViewModel(byte[] source) {
        Source = source;
    }

    public byte[] getSource() {
        return Source;
    }

    public void setSource(byte[] source) {
        Source = source;
    }
}
