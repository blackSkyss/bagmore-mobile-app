package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class ColorProductDetailViewModel {
    @SerializedName("ColorCode")
    public String ColorCode;

    public ColorProductDetailViewModel(String colorCode) {
        ColorCode = colorCode;
    }

    public String getColorCode() {
        return ColorCode;
    }

    public void setColorCode(String colorCode) {
        ColorCode = colorCode;
    }
}
