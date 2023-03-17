package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonCartRes;
import com.example.bagmore.Models.json.response.JsonDeliveryRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface DeliveryMethodService {
    @GET("user/deliverymethod/deliverymethods")
    Call<JsonDeliveryRes> getDeliveries(@Header(ApiClient.AUTH_HEADER) String accessToken);

}
