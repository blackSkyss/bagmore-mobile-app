package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

public class ColorViewModel {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("colorCode")
    private String codeColor;

    @SerializedName("status")
    private int status;

    private boolean isChecked;

    public ColorViewModel() {
    }

    public ColorViewModel(int id, String name, String codeColor, int status) {
        this.id = id;
        this.name = name;
        this.codeColor = codeColor;
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

    public String getCodeColor() {
        return codeColor;
    }

    public void setCodeColor(String codeColor) {
        this.codeColor = codeColor;
    }

    public int getStatus() {
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
