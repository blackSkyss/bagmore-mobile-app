package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.request.JsonAddToCartReq;
import com.example.bagmore.Models.json.response.JsonCartRes;
import com.example.bagmore.Models.json.response.JsonLogoutRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CartService {
    @POST("user/cart/add-product")
    Call<JsonLogoutRes> addToCart(@Header(ApiClient.AUTH_HEADER) String accessToken, @Body JsonAddToCartReq model);

    @GET("user/cart/products")
    Call<JsonCartRes> getCartList(@Header(ApiClient.AUTH_HEADER) String accessToken);

    @DELETE("user/cart/remove-product")
    Call<JsonLogoutRes> removeItemCart(@Header(ApiClient.AUTH_HEADER) String accessToken, @Query("ProductId") int productId, @Query("Color") String colorName, @Query("Amount") int amount, @Query("Size") String sizeName);

}
