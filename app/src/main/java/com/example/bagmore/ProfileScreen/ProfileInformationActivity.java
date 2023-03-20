package com.example.bagmore.ProfileScreen;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.RealPathUtil;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.request.JsonUpdateProfileReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.Models.json.response.JsonUpdateProfileRes;
import com.example.bagmore.Models.json.response.JsonUserProfileRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.UserService;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileInformationActivity extends AppCompatActivity {


    public ProfileInformationActivity() {
    }


    private static final int PICK_IMAGE_REQUEST_CODE = 10;
    //region init
    @BindView(R.id.btn_profile_information)
    MaterialButton btnEdit;

    @BindView(R.id.tv_fullname)
    TextView tvFullname;

    @BindView(R.id.btn_save_information)
    MaterialButton btnSave;

    @BindView(R.id.edt_first_name)
    EditText edtFirstName;

    @BindView(R.id.edt_last_name)
    EditText edtLastName;

    @BindView(R.id.edt_birthday)
    EditText edtBirthday;

    @BindView(R.id.edt_phone)
    EditText edtPhone;

    @BindView(R.id.edt_address_1)
    EditText edtAddress1;

    @BindView(R.id.edt_address_2)
    EditText edtAddress2;

    @BindView(R.id.img_update)
    ImageView imgAvatar;
    private UserService userService;

    private Uri filePath;
    private File imageAvt;
    Boolean isPermissionGranted = false;
    String email;
    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUEST_CODE = 123456;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);
        ButterKnife.bind(this);
        userService = UserRepository.getUserService();
        initToolbar();
        onClickHandler();
        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email");
        GetUserByEmail();
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_information);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_information_title);
        mTitle.setText("PERSONAL INFORMATION");
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    //region onclick handler
    private void onClickHandler() {

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableUpdateMode();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = validation();
                if (check) {
                    UpdateUserAPI();
                    /* Toast.makeText(ProfileInformationActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();*/
                }
                return;
            }
        });

        edtBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }
    //endregion

    //region image picker
    private void imagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image URI
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgAvatar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            // Glide.with(this).load(mUri).into(imgAvatar);
        } else {
            Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region enable update mode
    private void enableUpdateMode() {
        btnSave.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.INVISIBLE);
        imgAvatar.setClickable(true);
        edtFirstName.setEnabled(true);
        edtLastName.setEnabled(true);
        edtBirthday.setEnabled(true);
        edtPhone.setEnabled(true);
        edtAddress1.setEnabled(true);
        edtAddress2.setEnabled(true);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }
    //endregion

    //region show data picker
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                edtBirthday.setText(String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay));
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    //endregion
    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete Action Using"), REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(ProfileInformationActivity.this, permission, REQUEST_CODE);

        }
    }


    private void GetUserByEmail() {
        Call<JsonUserProfileRes> callGetUser = userService.getUserByEmail(email);
        callGetUser.enqueue(new Callback<JsonUserProfileRes>() {
            @Override
            public void onResponse(Call<JsonUserProfileRes> call, Response<JsonUserProfileRes> response) {
                if (response.isSuccessful()) {
                    JsonUserProfileRes json = response.body();
                    tvFullname.setText(json.getData().FirstName + " " + json.getData().LastName);
                    edtFirstName.setText(json.getData().FirstName);
                    edtLastName.setText(json.getData().LastName);
                    edtBirthday.setText(String.format(json.getData().BirthDay.substring(0, 10)));
                    edtAddress1.setText(json.getData().FirstAddress);
                    edtAddress2.setText(json.getData().SecondAddress);
                    edtPhone.setText(json.getData().Phone);
                    byte[] bytes = android.util.Base64.decode(json.getData().Avatar, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imgAvatar.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(ProfileInformationActivity.this, "Call fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonUserProfileRes> call, Throwable t) {
                Toast.makeText(ProfileInformationActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void UpdateUserAPI() {

        String strFname = edtFirstName.getText().toString().trim();
        String strLname = edtLastName.getText().toString().trim();
        String strBirthday = edtBirthday.getText().toString().trim();
        String strPhone = edtPhone.getText().toString().trim();
        String strAddress1 = edtAddress1.getText().toString().trim();
        String strAddress2 = edtAddress2.getText().toString().trim();


        Call<JsonUpdateProfileRes> result;
        TokenManager tokenManager = new TokenManager(getApplicationContext());
        if (filePath != null) {
            String strRealPath = RealPathUtil.getRealPath(this, filePath);
            Log.e("BagMore", strRealPath);
            imageAvt = new File(strRealPath);
            Bitmap bm = BitmapFactory.decodeFile(imageAvt.getPath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
            byte[] b = baos.toByteArray();
            String encodedString = Base64.encodeToString(b, Base64.DEFAULT);
            JsonUpdateProfileReq js = new JsonUpdateProfileReq(strFname, strLname, strBirthday, strPhone, strAddress1, strAddress2, encodedString);


            result = userService.updateUserProfile("bearer " + tokenManager.getAccessToken(), email, js);
        } else {
            JsonUpdateProfileReq js = new JsonUpdateProfileReq(strFname, strLname, strBirthday, strPhone, strAddress1, strAddress2, null);
            result = userService.updateUserProfile("bearer " + tokenManager.getAccessToken(), email, js);
        }
        result.enqueue(new Callback<JsonUpdateProfileRes>() {
            @Override
            public void onResponse(Call<JsonUpdateProfileRes> call, Response<JsonUpdateProfileRes> response) {
                if (response.isSuccessful()) {
                    disableUpdateMode();
                    Toast.makeText(ProfileInformationActivity.this, "update successfully", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 401) {
                    Toast.makeText(ProfileInformationActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                    refreshTokenAPI();
                } else {
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null) {
                        try {
                            String errorString = errorBody.string();
                            Gson gson = new Gson();
                            JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                            JsonLogoutRes jsonLogoutRes = gson.fromJson(errorJson, JsonLogoutRes.class);
                            Toast.makeText(ProfileInformationActivity.this, jsonLogoutRes.getMessage(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonUpdateProfileRes> call, Throwable t) {
                Toast.makeText(ProfileInformationActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //region close update mode
    private void disableUpdateMode() {
        btnSave.setVisibility(View.INVISIBLE);
        btnEdit.setVisibility(View.VISIBLE);
        imgAvatar.setClickable(true);
        edtFirstName.setEnabled(false);
        edtLastName.setEnabled(false);
        edtBirthday.setEnabled(false);
        edtPhone.setEnabled(false);
        edtAddress1.setEnabled(false);
        edtAddress2.setEnabled(false);
        imgAvatar.setOnClickListener(null);
    }
    //endregion

    //region validation
    private boolean validation() {
        int checkFlag = 0;
        if (edtFirstName.length() == 0) {
            edtFirstName.setError("First name is required!");
            checkFlag++;
        }
        if (edtLastName.length() == 0) {
            edtLastName.setError("Last name is required!");
            checkFlag++;
        }
        if (edtBirthday.length() == 0) {
            edtBirthday.setError("Birthday is required!");
            checkFlag++;
        }
        if (edtPhone.length() == 0) {
            edtPhone.setError("Phone is required!");
            checkFlag++;
        }
        if (edtAddress1.length() == 0) {
            edtAddress1.setError("Address 1 is required!");
            checkFlag++;
        }
        if (edtAddress2.length() == 0) {
            edtAddress2.setError("Address 2 is required!");
            checkFlag++;
        }

        if (checkFlag == 0) {
            return true;
        }
        return false;
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
                        UpdateUserAPI();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(ProfileInformationActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    public void navigation() {
        Intent intent = new Intent(ProfileInformationActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}