package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class JsonUserSignupReq {

    @SerializedName("Email")
    private String email;
    @SerializedName("Password")
    private String password;
    @SerializedName("Gender")
    private boolean gender;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("BirthDay")
    private Date birthDay;
    @SerializedName("Phone")
    private String phone;
    @SerializedName("FirstAddress")
    private String firstAddress;
    @SerializedName("SecondAddress")
    private String secondAddress;
    @SerializedName("Image")
    private byte[] image;

    public JsonUserSignupReq(String email, String password, boolean gender, String firstName, String lastName, Date birthDay, String phone, String firstAddress, String secondAddress, byte[] image) {
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
        this.phone = phone;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        this.firstAddress = firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        this.secondAddress = secondAddress;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
