package com.example.bagmore.Models.data;

public class ColorProductDetailViewModel {
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
