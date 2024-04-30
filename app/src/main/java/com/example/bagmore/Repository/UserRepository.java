package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.UserService;

public class UserRepository {
    public static UserService getUserService() {
        return ApiClient.getClient().create(UserService.class);
    }
}
