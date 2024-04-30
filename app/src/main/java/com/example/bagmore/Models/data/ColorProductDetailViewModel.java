package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class ColorProductDetailViewModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("colorCode")
    private String colorCode;

    @SerializedName("status")
    private int status;

    public ColorProductDetailViewModel() {
    }

    public ColorProductDetailViewModel(int id, String name, String colorCode, int status) {
        this.id = id;
        this.name = name;
        this.colorCode = colorCode;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
