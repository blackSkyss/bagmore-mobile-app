package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonAddShippingReq;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.ShippingAddressRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.ShippingAddressService;
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

public class CreateAddressActivity extends AppCompatActivity {

    //region init
    @BindView(R.id.title_bottom_order)
    TextView titleBottom;

    @BindView(R.id.edt_fname_address)
    EditText edtFName;

    @BindView(R.id.edt_lname_address)
    EditText edtLName;

    @BindView(R.id.edt_phone_address)
    EditText edtPhone;

    @BindView(R.id.edt_address_line)
    EditText edtAddressLine;

    UserService userService;

    ShippingAddressService shippingAddressService;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);
        userService = UserRepository.getUserService();
        shippingAddressService = ShippingAddressRepository.getShippingAddressService();

        ButterKnife.bind(this);

        configBottomNavigation();
        onClickHandler();
        initToolbar();
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_create_address);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_create_address_title);
        mTitle.setText("ADDRESS");
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
        titleBottom.setText("SAVE INFORMATION");
    }
    //endregion

    //region click handler
    private void onClickHandler() {
        titleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = validation();
                if (check) {
                    addShippingAPI();
                }
                return;
            }
        });
    }
    //endregion

    //region validation
    private boolean validation() {
        int checkFlag = 0;
        if (edtFName.length() == 0) {
            edtFName.setError("First name is required!");
            checkFlag++;
        }
        if (edtLName.length() == 0) {
            edtLName.setError("Last name is required!");
            checkFlag++;
        }
        if (edtPhone.length() == 0) {
            edtPhone.setError("Phone is required!");
            checkFlag++;
        }
        if (edtAddressLine.length() == 0) {
            edtAddressLine.setError("Address line is required!");
            checkFlag++;
        }

        if (edtFName.length() > 15) {
            edtFName.setError("First name <= 15 character!");
            checkFlag++;
        }
        if (edtLName.length() > 35) {
            edtLName.setError("Last name <= 35 character!");
            checkFlag++;
        }
        if (edtPhone.length() != 10) {
            edtPhone.setError("Phone must be 10 character!");
            checkFlag++;
        }
        if (edtAddressLine.length() > 100) {
            edtAddressLine.setError("Address <= 100 character!");
            checkFlag++;
        }

        if (checkFlag == 0) {
            return true;
        }
        return false;
    }
    //endregion

    //region navigation
    private void navigationResult() {
        Intent intentResult = new Intent();
        setResult(Activity.RESULT_OK, intentResult);
        finish();
    }
    //endregion

    //region Call API add shipping
    private void addShippingAPI() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            String strFname = edtFName.getText().toString().trim();
            String strLname = edtLName.getText().toString().trim();
            String strPhone = edtPhone.getText().toString().trim();
            String strAddress = edtAddressLine.getText().toString().trim();
            JsonAddShippingReq model = new JsonAddShippingReq(strFname, strLname, strPhone, strAddress);
            Call<JsonLogoutRes> callAddShipping = shippingAddressService.addShipping("bearer " + tokenManager.getAccessToken(), model);
            callAddShipping.enqueue(new Callback<JsonLogoutRes>() {
                @Override
                public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreateAddressActivity.this, "Add successfully", Toast.LENGTH_SHORT).show();
                        navigationResult();
                    } else if (response.code() == 401) {
                        Toast.makeText(CreateAddressActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                        refreshTokenAPI();
                    } else {
                        ResponseBody errorBody = response.errorBody();
                        if (errorBody != null) {
                            try {
                                String errorString = errorBody.string();
                                Gson gson = new Gson();
                                JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                                JsonLogoutRes jsonLogoutRes = gson.fromJson(errorJson, JsonLogoutRes.class);
                                Toast.makeText(CreateAddressActivity.this, jsonLogoutRes.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                    Toast.makeText(CreateAddressActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();

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
                        addShippingAPI();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(CreateAddressActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(CreateAddressActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}