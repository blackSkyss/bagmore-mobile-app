package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonProductDetailRes;
import com.example.bagmore.Models.json.response.JsonProductViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductService {
    @GET("user/product/products")
    Call<JsonProductViewModel> getProducts(@Header(ApiClient.AUTH_HEADER) String accessToken,
                                           @Query("sortby") String keySort,
                                           @Query("filterbycategory") List<Integer> categoryList,
                                           @Query("filterbycolor") List<Integer> colorList,
                                           @Query("filterbysize") List<Integer> sizeList);

    @GET("user/Product/Get/{productId}")
    Call<JsonProductDetailRes> getProductDetailById(@Header(ApiClient.AUTH_HEADER) String accessToken, @Path("productId") int id);
}
