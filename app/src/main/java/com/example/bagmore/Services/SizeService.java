package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonSize;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface SizeService {
    @GET("user/product/sizes")
    Call<JsonSize> getSizes(@Header(ApiClient.AUTH_HEADER) String accessToken);
}
