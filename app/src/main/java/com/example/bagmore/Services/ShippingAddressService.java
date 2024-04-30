package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.request.JsonAddShippingReq;
import com.example.bagmore.Models.json.response.JsonCartRes;
import com.example.bagmore.Models.json.response.JsonLoginRes;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonShippingRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ShippingAddressService {
    @GET("user/shippingaddress/get")
    Call<JsonShippingRes> getShippings(@Header(ApiClient.AUTH_HEADER) String accessToken);

    @POST("user/shippingaddress/create")
    Call<JsonLogoutRes> addShipping(@Header(ApiClient.AUTH_HEADER) String accessToken, @Body JsonAddShippingReq model);
}
