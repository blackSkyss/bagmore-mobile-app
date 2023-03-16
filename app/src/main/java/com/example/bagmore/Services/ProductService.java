package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonProductDetailRes;
import com.example.bagmore.Models.json.response.JsonProductViewModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ProductService {
    @GET("user/product/products")
    Call<JsonProductViewModel> getProducts(@Header(ApiClient.AUTH_HEADER) String accessToken);

    @GET("user/Product/Get/{productId}")
    Call<JsonProductDetailRes> getProductDetailById(@Header(ApiClient.AUTH_HEADER) String accessToken, @Path("productId") int id);
}
