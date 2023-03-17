package com.example.bagmore.Models.json.request;

import com.google.gson.annotations.SerializedName;

public class JsonCheckout {
    @SerializedName("deliveryMethodId")
    private int deliveryId;

    @SerializedName("shippingAddressId")
    private int shippingId;

    public JsonCheckout() {
    }

    public JsonCheckout(int deliveryId, int shippingId) {
        this.deliveryId = deliveryId;
        this.shippingId = shippingId;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getShippingId() {
        return shippingId;
    }

    public void setShippingId(int shippingId) {
        this.shippingId = shippingId;
    }
}
