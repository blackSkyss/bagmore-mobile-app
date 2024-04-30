package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonColor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ColorService {
    @GET("user/product/colors")
    Call<JsonColor> getColors(@Header(ApiClient.AUTH_HEADER) String accessToken);
}
