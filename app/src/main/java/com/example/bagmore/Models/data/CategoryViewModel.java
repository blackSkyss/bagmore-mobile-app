package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class CategoryViewModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private int status;
    private boolean isChecked;

    public CategoryViewModel() {
    }

    public CategoryViewModel(int id, String name, int status) {
        this.id = id;
        this.name = name;
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

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
