package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class SizeProductDetailViewModel {
    @SerializedName("name")
    public String Name;

    public SizeProductDetailViewModel(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
