package com.example.bagmore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bagmore.Adapters.TabViewAdapters.DeliveryTVAdapter;
import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.OrderViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonOrder;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.Repository.OrderRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.OrderService;
import com.example.bagmore.Services.UserService;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity {

    //region init
    @BindView(R.id.tl_delivery)
    TabLayout tlDelivery;

    @BindView(R.id.vp_delivery)
    ViewPager vpDelivery;

    private DeliveryTVAdapter deliveryTVAdapter;

    private UserService userService;

    private OrderService orderService;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);
        userService = UserRepository.getUserService();
        orderService = OrderRepository.getOrderService();

        ButterKnife.bind(this);

        initToolbar();
        getAllOrderAPI();
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_delivery);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_delivery);
        mTitle.setText("DELIVERY");
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

    //region refresh activity
    public void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
    //endregion

    //region config tab view
    private void configTabView(List<OrderViewModel> orders) {
        deliveryTVAdapter = new DeliveryTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, orders);
        vpDelivery.setAdapter(deliveryTVAdapter);
        tlDelivery.setupWithViewPager(vpDelivery);
    }
    //endregion

    //region call api get all order
    private void getAllOrderAPI() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            Call<JsonOrder> callGetProducts = orderService.getAllOrder("bearer " + tokenManager.getAccessToken());
            callGetProducts.enqueue(new Callback<JsonOrder>() {
                @Override
                public void onResponse(Call<JsonOrder> call, Response<JsonOrder> response) {
                    if (response.isSuccessful()) {
                        JsonOrder jsonModel = response.body();
                        configTabView(jsonModel.getOrders());
                    } else if (response.code() == 401) {
                        Toast.makeText(DeliveryActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                        refreshTokenAPI();
                    } else {
                        Toast.makeText(DeliveryActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonOrder> call, Throwable t) {
                    Toast.makeText(DeliveryActivity.this, "Failed to all API", Toast.LENGTH_SHORT).show();

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
                        getAllOrderAPI();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(DeliveryActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(DeliveryActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion

}