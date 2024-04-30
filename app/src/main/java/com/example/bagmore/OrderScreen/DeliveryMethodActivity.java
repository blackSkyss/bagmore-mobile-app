package com.example.bagmore.OrderScreen;

import android.app.Activity;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.DeliveryMethodRVAdapter;
import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemDeliveryMethod;
import com.example.bagmore.Models.data.DeliveryMethodViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonDeliveryRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.DeliveryMethodRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.DeliveryMethodService;
import com.example.bagmore.Services.UserService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryMethodActivity extends AppCompatActivity {

    //region init
    private RecyclerView.LayoutManager layoutManager;

    private DeliveryMethodRVAdapter deliveryMethodRVAdapter;

    private RecyclerView recyclerView;

    @BindView(R.id.title_bottom_order)
    TextView titleBottom;

    @BindView(R.id.swipe_rf_delivery)
    SwipeRefreshLayout rfDelivery;

    UserService userService;

    DeliveryMethodService deliveryMethodService;

    private String nameDelivery = "Haha";
    private int idDelivery = 0;
    private int priceDelivery = 0;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_method);
        ButterKnife.bind(this);

        initUI();
        initToolbar();

        getAllDeliveries();
        configBottomNavigation();
        onClickHandler();


    }

    //region set callback recycler view
    private void setCallbackDeliveryItem(List<DeliveryMethodViewModel> deliveries) {
        deliveryMethodRVAdapter = new DeliveryMethodRVAdapter(new IClickItemDeliveryMethod() {
            @Override
            public void onClickItemDelivery(DeliveryMethodViewModel viewModel) {
                priceDelivery = viewModel.getPrice();
                idDelivery = viewModel.getId();
                nameDelivery = viewModel.getName();
            }
        });
        deliveryMethodRVAdapter.setData(deliveries);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(deliveryMethodRVAdapter);
        recyclerView.setHasFixedSize(true);
    }
    //endregion

    //region init ui
    private void initUI() {
        recyclerView = findViewById(R.id.rcv_delivery);
        userService = UserRepository.getUserService();
        deliveryMethodService = DeliveryMethodRepository.getDeliveryMethodService();
    }
    //endregion

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_delivery_method);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_delivery_title);
        mTitle.setText("DELIVERY METHOD");
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

    //region config bottom navigation
    private void configBottomNavigation() {
        titleBottom.setText("DONE");
        titleBottom.setTextSize(20);
    }
    //endregion

    //region click handler
    private void onClickHandler() {
        titleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idDelivery == 0) {
                    Toast.makeText(DeliveryMethodActivity.this, "Please choose any one!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("id_delivery", idDelivery);
                intent.putExtra("price_delivery", priceDelivery);
                intent.putExtra("name_delivery", nameDelivery);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        rfDelivery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshHandler();
            }
        });

    }
    //endregion

    //region onReFreshHandler
    private void onRefreshHandler() {
        getAllDeliveries();
        deliveryMethodRVAdapter.notifyDataSetChanged();
        rfDelivery.setRefreshing(false);
    }
    //endregion

    //region call api get delivery method
    private void getAllDeliveries() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            Call<JsonDeliveryRes> callGetDeliveries = deliveryMethodService.getDeliveries("bearer " + tokenManager.getAccessToken());
            callGetDeliveries.enqueue(new Callback<JsonDeliveryRes>() {
                @Override
                public void onResponse(Call<JsonDeliveryRes> call, Response<JsonDeliveryRes> response) {
                    if (response.isSuccessful()) {
                        JsonDeliveryRes data = response.body();
                        setCallbackDeliveryItem(data.getData());
                    } else if (response.code() == 401) {
                        Toast.makeText(DeliveryMethodActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                        refreshTokenAPI();
                    } else {
                        Toast.makeText(DeliveryMethodActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonDeliveryRes> call, Throwable t) {
                    Toast.makeText(DeliveryMethodActivity.this, "Failed to all API", Toast.LENGTH_SHORT).show();

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
                        getAllDeliveries();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(DeliveryMethodActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(DeliveryMethodActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}