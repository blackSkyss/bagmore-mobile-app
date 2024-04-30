package com.example.bagmore.ProfileScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.DeliveryActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.R;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.UserService;
import com.example.bagmore.ShopAddressActivity;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileOverallActivity extends AppCompatActivity {

    //region init
    @BindView(R.id.ln_information)
    View lnInformation;

    @BindView(R.id.ln_orders)
    View lnOrders;

    @BindView(R.id.ln_shop_address)
    View lnShopAddress;

    @BindView(R.id.ln_logout)
    View lnLogout;

    @BindView(R.id.btn_home)
    MaterialButton btnHome;

    @BindView(R.id.btn_map)
    MaterialButton btnNotification;

    @BindView(R.id.btn_discovery)
    MaterialButton btnDiscovery;

    @BindView(R.id.btn_shop)
    MaterialButton btnShop;

    @BindView(R.id.btn_account)
    MaterialButton btnAccount;

    private UserService userService;
    String email;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_overall);
        userService = UserRepository.getUserService();

        ButterKnife.bind(this);


        onClickHandler();

        initToolbar();

        Intent intent = getIntent();
         email = (String) intent.getSerializableExtra("email");
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_profile);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_profile_title);
        mTitle.setText("PROFILE");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            // visible back button
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //endregion

    //region on click handler
    private void onClickHandler() {
        lnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileOverallActivity.this, ProfileInformationActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);

            }
        });

        lnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileOverallActivity.this, DeliveryActivity.class);
                startActivity(intent);
            }
        });

        lnShopAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileOverallActivity.this, ShopAddressActivity.class);
                startActivity(intent);
            }
        });

        lnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutAPI();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProfileOverallActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        btnDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(ProfileOverallActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

    }
    //endregion

    //region logout API
    private void logoutAPI() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            Call<JsonLogoutRes> result = userService.userLogout("bearer " + tokenManager.getAccessToken());
            result.enqueue(new Callback<JsonLogoutRes>() {
                @Override
                public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                    tokenManager.clearToken();
                    navigation();
                }

                @Override
                public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                    Toast.makeText(ProfileOverallActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region refresh token
    private void refreshTokenAPI() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            JsonRefreshTokenReq json = new JsonRefreshTokenReq(tokenManager.getAccessToken(), tokenManager.getRefreshToken());
            Call<JsonRefreshTokenRes> result = userService.userRefreshToken(json);
            result.enqueue(new Callback<JsonRefreshTokenRes>() {
                @Override
                public void onResponse(Call<JsonRefreshTokenRes> call, Response<JsonRefreshTokenRes> response) {
                    if (response.isSuccessful()) {
                        JsonRefreshTokenRes jsonRefreshTokenResponse = response.body();
                        TokenRefreshViewModel tokenRefresh = jsonRefreshTokenResponse.getData();
                        tokenManager.clearToken();
                        tokenManager.saveToken(tokenRefresh.getAccessToken(), tokenRefresh.getRefreshToken());
                        // call function call to here one time
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(ProfileOverallActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(ProfileOverallActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}