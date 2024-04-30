package com.example.bagmore.Models.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserProfileViewModel {
    @SerializedName("firstName")
    public String FirstName;
    @SerializedName("lastName")
    public String LastName;
    @SerializedName("birthDay")
    public String BirthDay;
    @SerializedName("phone")
    public String Phone;
    @SerializedName("firstAddress")
    public String FirstAddress;
    @SerializedName("secondAddress")
    public String SecondAddress;
    @SerializedName("avatar")
    public String Avatar;

    public UserProfileViewModel(String firstName, String lastName, String birthDay, String phone, String firstAddress, String secondAddress, String avatar) {
        FirstName = firstName;
        LastName = lastName;
        BirthDay = birthDay;
        Phone = phone;
        FirstAddress = firstAddress;
        SecondAddress = secondAddress;
        Avatar = avatar;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getFirstAddress() {
        return FirstAddress;
    }

    public void setFirstAddress(String firstAddress) {
        FirstAddress = firstAddress;
    }

    public String getSecondAddress() {
        return SecondAddress;
    }

    public void setSecondAddress(String secondAddress) {
        SecondAddress = secondAddress;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }
}
