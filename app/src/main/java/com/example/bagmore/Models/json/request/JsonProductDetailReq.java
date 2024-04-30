package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

public class JsonProductDetailReq {
    @SerializedName("Id")
    private int Id;

    public JsonProductDetailReq(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
