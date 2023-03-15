package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.request.JsonUserLoginReq;
import com.example.bagmore.Models.json.response.JsonLoginRes;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @POST("authentication/user-login")
    Call<JsonLoginRes> userLogin(@Body JsonUserLoginReq user);

    @POST("authentication/register")
    Call<JsonLoginRes> userRegister(@Body RequestBody requestBody);

    @POST("authentication/logout")
    Call<JsonLogoutRes> userLogout(@Header(ApiClient.AUTH_HEADER) String accessToken);

    @POST("authentication/refresh-token")
    Call<JsonRefreshTokenRes> userRefreshToken(@Body JsonRefreshTokenReq token);


}
