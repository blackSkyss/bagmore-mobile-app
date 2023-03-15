package com.example.bagmore.Models.data;

public class ShippingAddressViewModel {
    private int id;
    private String name;
    private String phone;
    private String address;
    private int status;
    private int imageUser;
    private boolean isChecked;

    public ShippingAddressViewModel() {
    }

    public ShippingAddressViewModel(int id, String name, String phone, String address, int status, int imageUser) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.imageUser = imageUser;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getImageUser() {
        return imageUser;
    }

    public void setImageUser(int imageUser) {
        this.imageUser = imageUser;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
