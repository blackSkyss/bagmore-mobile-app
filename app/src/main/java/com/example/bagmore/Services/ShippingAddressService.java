package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonCartRes;
import com.example.bagmore.Models.json.response.JsonShippingRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ShippingAddressService {
    @GET("user/shippingaddress/get")
    Call<JsonShippingRes> getShippings(@Header(ApiClient.AUTH_HEADER) String accessToken);
}
