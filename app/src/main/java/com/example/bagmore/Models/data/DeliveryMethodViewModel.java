package com.example.bagmore.Models.data;

public class DeliveryMethodViewModel {
    private int id;
    private String name;
    private int price;
    private String description;
    private int status;
    private boolean isChecked;

    public DeliveryMethodViewModel() {
    }

    public DeliveryMethodViewModel(int id, String name, int price, String description, int status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
