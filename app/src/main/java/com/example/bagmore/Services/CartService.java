package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.request.JsonAddToCartReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CartService {
    @POST("user/cart/add-product")
    Call<JsonLogoutRes> addToCart(@Header(ApiClient.AUTH_HEADER) String accessToken, @Body JsonAddToCartReq model);

}
