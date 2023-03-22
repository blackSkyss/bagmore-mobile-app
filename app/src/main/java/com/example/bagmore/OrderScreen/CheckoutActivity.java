package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.HandlerException.Dialog;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonCheckout;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.OrderRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.OrderService;
import com.example.bagmore.Services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    //region access to component in include layout
    private static int CODE_DELIVERY = 10;
    private static int CODE_SHIPPING = 20;
    @BindView(R.id.ln_bottom_order)
    View lnBottom;

    @BindView(R.id.title_bottom_order)
    TextView titleBottomCheckout;

    @BindView(R.id.shape_bottom_order)
    View shapeBottomCheckout;

    @BindView(R.id.tv_delivery_ck)
    TextView tv_delivery;

    @BindView(R.id.tv_shipping_ck)
    TextView tv_shipping;

    private View lnDeliveryMethod;

    private View lnShippingAddress;

    private int total = 0;

    private int price = 0;

    private int idDelivery = 0;

    private int idShipping = 0;

    private OrderService orderService;

    private UserService userService;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        initUI();
        initToolbar();

        total = getIntent().getIntExtra("total_price", 0);

        configBottomNavigation();
        onClickHandler();
    }

    //region init UI
    private void initUI() {
        lnDeliveryMethod = findViewById(R.id.ln_delivery_method);
        lnShippingAddress = findViewById(R.id.ln_shipping_address);
        orderService = OrderRepository.getOrderService();
        userService = UserRepository.getUserService();
    }
    //endregion

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_checkout);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_checkout_title);
        mTitle.setText("CHECKOUT");
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
        lnBottom.setBackgroundResource(R.color.light_grey);
        titleBottomCheckout.setTextColor(Color.BLACK);
        titleBottomCheckout.setText("PAY \u25CF $" + total);
        titleBottomCheckout.setTextSize(15);
        shapeBottomCheckout.setBackgroundColor(Color.BLACK);
    }
    //endregion

    //region click handler
    private void onClickHandler() {
        titleBottomCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String error = "";
                //go to list order
                if (idDelivery == 0) {
                    error += "Please choose delivery method!\n";
                }
                if (idShipping == 0) {
                    error += "Please choose shipping address!\n";
                }
                if (error != "") {
                    Dialog.showDialog(CheckoutActivity.this, "Checkout execution", error);
                    return;
                }
                checkOut();
            }
        });

        lnShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, ShippingAddressActivity.class);
                startActivityForResult(intent, CODE_SHIPPING);
            }
        });

        lnDeliveryMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, DeliveryMethodActivity.class);
                startActivityForResult(intent, CODE_DELIVERY);
            }
        });
    }
    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DELIVERY && resultCode == Activity.RESULT_OK) {
            idDelivery = data.getIntExtra("id_delivery", 0);

            total -= price;
            price = data.getIntExtra("price_delivery", 0);
            total += price;

            tv_delivery.setText("\u25CF Delivery: " + data.getStringExtra("name_delivery"));

            configBottomNavigation();
        } else if (requestCode == CODE_SHIPPING && resultCode == Activity.RESULT_OK) {
            idShipping = data.getIntExtra("id_shipping", 0);
            tv_shipping.setText("\u25CF Shipping: " + data.getStringExtra("name_shipping"));
        } else {

        }
    }

    //region call api checkout
    private void checkOut() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            JsonCheckout model = new JsonCheckout(idDelivery, idShipping);
            Call<JsonLogoutRes> callCheckout = orderService.checkOut("bearer " + tokenManager.getAccessToken(), model);
            callCheckout.enqueue(new Callback<JsonLogoutRes>() {
                @Override
                public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CheckoutActivity.this, "Checkout successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(Activity.RESULT_OK, intent);
                        finish();

                    } else if (response.code() == 401) {
                        Toast.makeText(CheckoutActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                        refreshTokenAPI();
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            try {
                                String errorString = errorBody.string();
                                Gson gson = new Gson();
                                JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                                JsonLogoutRes jsonLogoutRes = gson.fromJson(errorJson, JsonLogoutRes.class);
                                Toast.makeText(CheckoutActivity.this, jsonLogoutRes.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                    Toast.makeText(CheckoutActivity.this, "Failed to all API", Toast.LENGTH_SHORT).show();

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
                        checkOut();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(CheckoutActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(CheckoutActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}