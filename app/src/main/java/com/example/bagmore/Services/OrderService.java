package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.request.JsonCheckout;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonOrder;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {
    @POST("user/cart/checkout")
    Call<JsonLogoutRes> checkOut(@Header(ApiClient.AUTH_HEADER) String accessToken, @Body JsonCheckout model);

    @GET("user/order/orders")
    Call<JsonOrder> getAllOrder(@Header(ApiClient.AUTH_HEADER) String accessToken);
}
