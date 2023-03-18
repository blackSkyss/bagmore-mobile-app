package com.example.bagmore.AuthScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bagmore.HandlerException.Dialog;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.HomeActivity;
import com.example.bagmore.Models.data.TokenViewModel;
import com.example.bagmore.Models.json.request.JsonUserLoginReq;
import com.example.bagmore.Models.json.response.JsonLoginRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.UserService;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    //region init
    private UserService userService;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.edt_email_login)
    EditText edtEmail;

    @BindView(R.id.edt_password_login)
    EditText edtPassword;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        initToolbar();
        onClickHandler();

        userService = UserRepository.getUserService();

    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_login);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // visible back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    //region onClick handler
    private void onClickHandler() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = validation();
                if (check) {
                    loginAPI(edtEmail.getText().toString(), edtPassword.getText().toString());
                }
                return;
            }
        });
    }
    //endregion

    //region validation
    private boolean validation() {
        int checkFlag = 0;
        if (edtEmail.length() == 0) {
            edtEmail.setError("Email is required!");
            checkFlag++;
        }
        if (edtPassword.length() == 0) {
            edtPassword.setError("Password is required!");
            checkFlag++;
        }

        if (checkFlag == 0) {
            return true;
        }
        return false;
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion

    //region call api
    private void loginAPI(String email, String password) {
        try {
            JsonUserLoginReq user = new JsonUserLoginReq(email, password);
            Call<JsonLoginRes> result = userService.userLogin(user);
            result.enqueue(new Callback<JsonLoginRes>() {
                @Override
                public void onResponse(Call<JsonLoginRes> call, Response<JsonLoginRes> response) {
                    if (response.isSuccessful()) {
                        JsonLoginRes data = response.body();
                        TokenViewModel token = data.getData();
                        TokenManager tokenManager = new TokenManager(getApplicationContext());
                        tokenManager.saveToken(token.getAccessToken(), token.getRefreshToken());
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("email", data.getEmail());
                        startActivity(intent);
                        //navigation();
                    } else {
                        Dialog.showDialog(LoginActivity.this, "Login execution", "Account does not exist");
                    }
                }

                @Override
                public void onFailure(Call<JsonLoginRes> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //endregion
}