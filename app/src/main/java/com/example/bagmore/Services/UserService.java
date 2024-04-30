package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.request.JsonUpdateProfileReq;
import com.example.bagmore.Models.json.request.JsonUserLoginReq;
import com.example.bagmore.Models.json.request.JsonUserProfileReq;
import com.example.bagmore.Models.json.response.JsonLoginRes;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.Models.json.response.JsonUpdateProfileRes;
import com.example.bagmore.Models.json.response.JsonUserProfileRes;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @POST("authentication/user-login")
    Call<JsonLoginRes> userLogin(@Body JsonUserLoginReq user);


    @Multipart
    @POST("authentication/register")
    Call<JsonLogoutRes> userRegister(@Part MultipartBody.Part image,
                                     @Part("Email") RequestBody email,
                                     @Part("Password") RequestBody password,
                                     @Part("Gender") RequestBody gender,
                                     @Part("FirstName") RequestBody firstName,
                                     @Part("LastName") RequestBody lastName,
                                     @Part("BirthDay") RequestBody birthDay,
                                     @Part("Phone") RequestBody phone,
                                     @Part("FirstAddress") RequestBody firstAddress,
                                     @Part("SecondAddress") RequestBody secondAddress
    );

    @POST("authentication/logout")
    Call<JsonLogoutRes> userLogout(@Header(ApiClient.AUTH_HEADER) String accessToken);

    @POST("authentication/refresh-token")
    Call<JsonRefreshTokenRes> userRefreshToken(@Body JsonRefreshTokenReq token);


    @PUT("user/User/Update/{userEmail}")
    Call<JsonUpdateProfileRes> updateUserProfile(@Header(ApiClient.AUTH_HEADER) String accessToken,  @Path("userEmail") String email,
                                                 @Body JsonUpdateProfileReq json);

    @GET("user/User/Get/{email}")
    Call<JsonUserProfileRes> getUserByEmail(@Path("email") String email);


}
