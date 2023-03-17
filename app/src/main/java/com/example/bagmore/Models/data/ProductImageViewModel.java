package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImageViewModel implements Serializable {
    @SerializedName("source")
    public String Source;

    public ProductImageViewModel(String source) {
        Source = source;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }
}
