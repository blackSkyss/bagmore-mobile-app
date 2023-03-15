package com.example.bagmore.Models.data;

import java.io.Serializable;

public class ItemCartViewModel implements Serializable {
    private int id;
    private String name;
    private int quantity;
    private String size;
    private String color;
    private int price;
    private int image;

    public ItemCartViewModel() {
    }

    public ItemCartViewModel(int id, String name, int quantity, String size, String color, int price, int image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.price = price;
        this.image = image;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
