package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class SizeProductDetailViewModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String Name;

    @SerializedName("status")
    private int status;

    public SizeProductDetailViewModel() {

    }

    public SizeProductDetailViewModel(int id, String name, int status) {
        this.id = id;
        Name = name;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
