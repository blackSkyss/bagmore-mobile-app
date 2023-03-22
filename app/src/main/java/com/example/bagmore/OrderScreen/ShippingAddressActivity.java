package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ShippingAddressRVAdapter;
import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemShippingAddress;
import com.example.bagmore.Models.data.ShippingAddressViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.Models.json.response.JsonShippingRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.ShippingAddressRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.ShippingAddressService;
import com.example.bagmore.Services.UserService;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShippingAddressActivity extends AppCompatActivity {

    //region init
    private static final int CODE = 10;
    private MaterialButton mtAddShipping;

    private RecyclerView.LayoutManager layoutManager;

    private ShippingAddressRVAdapter shippingAddressRVAdapter;

    private RecyclerView recyclerView;

    @BindView(R.id.title_bottom_order)
    TextView titleBottom;

    @BindView(R.id.swipe_rf_shipping)
    SwipeRefreshLayout rfShipping;

    UserService userService;

    ShippingAddressService shippingAddressService;

    private String nameShipping = "HAHA";
    private int idShipping = 0;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);

        initUI();
        initToolbar();

        getAllShippings();

        configBottomNavigation();
        handlerClick();
    }

    //region set callback recycler view
    private void setCallbackShippingItem(List<ShippingAddressViewModel> shippings) {
        shippingAddressRVAdapter = new ShippingAddressRVAdapter(new IClickItemShippingAddress() {
            @Override
            public void onClickItemShipping(ShippingAddressViewModel viewModel) {
                idShipping = viewModel.getId();
                nameShipping = viewModel.getAddress();
            }
        });
        shippingAddressRVAdapter.setData(shippings);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(shippingAddressRVAdapter);
        recyclerView.setHasFixedSize(true);
    }
    //endregion

    //region init UI
    private void initUI() {
        mtAddShipping = findViewById(R.id.btn_create_address);
        recyclerView = findViewById(R.id.rcv_shipping);
        userService = UserRepository.getUserService();
        shippingAddressService = ShippingAddressRepository.getShippingAddressService();
    }
    //endregion

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_shipping_address);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_shipping_title);
        mTitle.setText("SHIPPING ADDRESS");
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

    //region handler click event
    private void handlerClick() {
        mtAddShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShippingAddressActivity.this, CreateAddressActivity.class);
                startActivityForResult(intent, CODE);
            }
        });

        titleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idShipping == 0) {
                    Toast.makeText(ShippingAddressActivity.this, "Please choose any one", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("id_shipping", idShipping);
                intent.putExtra("name_shipping", nameShipping);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        rfShipping.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshHandler();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            rfShipping.setRefreshing(true);
            onRefreshHandler();
            Toast.makeText(this, "Add successfully", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region onRefresh handler
    private void onRefreshHandler() {
        getAllShippings();
        shippingAddressRVAdapter.notifyDataSetChanged();
        rfShipping.setRefreshing(false);
    }
    //endregion

    //region dummy data || call api here
    private void getAllShippings() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            Call<JsonShippingRes> callGetShippings = shippingAddressService.getShippings("bearer " + tokenManager.getAccessToken());
            callGetShippings.enqueue(new Callback<JsonShippingRes>() {
                @Override
                public void onResponse(Call<JsonShippingRes> call, Response<JsonShippingRes> response) {
                    if (response.isSuccessful()) {
                        JsonShippingRes jsonModel = response.body();
                        setCallbackShippingItem(jsonModel.getData());
                    } else if (response.code() == 401) {
                        Toast.makeText(ShippingAddressActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                        refreshTokenAPI();
                    } else {
                        Toast.makeText(ShippingAddressActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonShippingRes> call, Throwable t) {
                    Toast.makeText(ShippingAddressActivity.this, "Failed to all API", Toast.LENGTH_SHORT).show();

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
                        getAllShippings();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(ShippingAddressActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(ShippingAddressActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}