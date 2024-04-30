package com.example.bagmore.AuthScreen;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.bagmore.HandlerException.Validation;
import com.example.bagmore.Helpers.RealPathUtil;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST_CODE = 10;
    //region init
    @BindView(R.id.btn_sign_up)
    Button btnSignup;

    @BindView(R.id.btn_gender_man)
    Button btnGenderMan;

    @BindView(R.id.btn_gender_woman)
    Button btnGenderWoman;

    @BindView(R.id.edt_fname_signup)
    EditText edtFname;

    @BindView(R.id.edt_lname_signup)
    EditText edtLname;

    @BindView(R.id.edt_email_signup)
    EditText edtEmail;

    @BindView(R.id.edt_password_signup)
    EditText edtPassword;

    @BindView(R.id.edt_password_cf_signup)
    EditText edtConfirmPassword;
    @BindView(R.id.edt_birthday_signup)
    EditText edtBirthday;

    @BindView(R.id.edt_phone_signup)
    EditText edtPhone;

    @BindView(R.id.edt_address1_signup)
    EditText edtAddress1;

    @BindView(R.id.edt_address2_signup)
    EditText edtAddress2;

    @BindView(R.id.img_avt)
    ImageView imgAvatar;
    private String gender = "true";

    private UserService userService;

    private Uri filePath;
    private File imageAvt;
    Boolean isPermissionGranted = false;

    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int REQUEST_CODE = 123456;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        initToolbar();
        onClickHandler();
        userService = UserRepository.getUserService();
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_signup_profile);
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

    //region onClick handler
    private void onClickHandler() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                boolean check = validation();
                if (check) {
                    signupAPI();
                }
                return;
            }
        });

        btnGenderMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGenderMan.setBackgroundColor(getResources().getColor(R.color.black));
                btnGenderMan.setTextColor(getResources().getColor(R.color.white));
                btnGenderWoman.setBackgroundColor(getResources().getColor(R.color.white));
                btnGenderWoman.setTextColor(getResources().getColor(R.color.black));
                gender = "true";
                Toast.makeText(SignupActivity.this, "Selected gender man: " + gender, Toast.LENGTH_SHORT).show();
            }
        });

        btnGenderWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnGenderWoman.setBackgroundColor(getResources().getColor(R.color.black));
                btnGenderWoman.setTextColor(getResources().getColor(R.color.white));
                btnGenderMan.setBackgroundColor(getResources().getColor(R.color.white));
                btnGenderMan.setTextColor(getResources().getColor(R.color.black));
                gender = "false";
                Toast.makeText(SignupActivity.this, "Selected gender women: " + gender, Toast.LENGTH_SHORT).show();
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

    //region show image picker
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

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            isPermissionGranted = true;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete Action Using"), REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(SignupActivity.this, permission, REQUEST_CODE);

        }
    }
    //endregion

    //region Call Signup API
    private void signupAPI() {
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();
        String strFname = edtFname.getText().toString().trim();
        String strLname = edtLname.getText().toString().trim();
        String strBirthday = edtBirthday.getText().toString().trim();
        String strPhone = edtPhone.getText().toString().trim();
        String strAddress1 = edtAddress1.getText().toString().trim();
        String strAddress2 = edtAddress2.getText().toString().trim();

        RequestBody requestBodyEmail = RequestBody.create(MediaType.parse("multipart/form-data"), strEmail);
        RequestBody requestBodyGender = RequestBody.create(MediaType.parse("multipart/form-data"), gender);
        RequestBody requestBodyPassword = RequestBody.create(MediaType.parse("multipart/form-data"), strPassword);
        RequestBody requestBodyFname = RequestBody.create(MediaType.parse("multipart/form-data"), strFname);
        RequestBody requestBodyLname = RequestBody.create(MediaType.parse("multipart/form-data"), strLname);
        RequestBody requestBodyBirthday = RequestBody.create(MediaType.parse("multipart/form-data"), strBirthday);
        RequestBody requestBodyPhone = RequestBody.create(MediaType.parse("multipart/form-data"), strPhone);
        RequestBody requestBodyAddress1 = RequestBody.create(MediaType.parse("multipart/form-data"), strAddress1);
        RequestBody requestBodyAddress2 = RequestBody.create(MediaType.parse("multipart/form-data"), strAddress2);

        Call<JsonLogoutRes> result;

        if (filePath != null) {
            String strRealPath = RealPathUtil.getRealPath(this, filePath);
            Log.e("BagMore", strRealPath);
            imageAvt = new File(strRealPath);
            RequestBody requestBodyAvt = RequestBody.create(MediaType.parse("image/*"), imageAvt);
            MultipartBody.Part multipartBodyAvt = MultipartBody.Part.createFormData("Image", imageAvt.getName(), requestBodyAvt);

            result = userService.userRegister(multipartBodyAvt,
                    requestBodyEmail,
                    requestBodyPassword,
                    requestBodyGender,
                    requestBodyFname,
                    requestBodyLname,
                    requestBodyBirthday,
                    requestBodyPhone,
                    requestBodyAddress1,
                    requestBodyAddress2);
        } else {
            result = userService.userRegister(null,
                    requestBodyEmail,
                    requestBodyPassword,
                    requestBodyGender,
                    requestBodyFname,
                    requestBodyLname,
                    requestBodyBirthday,
                    requestBodyPhone,
                    requestBodyAddress1,
                    requestBodyAddress2);
        }

        result.enqueue(new Callback<JsonLogoutRes>() {
            @Override
            public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    navigation();
                }

                if (response.code() == 400) {
                    ResponseBody errorBody = response.errorBody();
                    if (errorBody != null) {
                        try {
                            String errorString = errorBody.string();
                            Gson gson = new Gson();
                            JsonObject errorJson = gson.fromJson(errorString, JsonObject.class);
                            JsonLogoutRes jsonLogoutRes = gson.fromJson(errorJson, JsonLogoutRes.class);
                            edtEmail.setError(jsonLogoutRes.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion

    //region validation
    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validation() {
        int checkFlag = 0;

        //region check empty
        if (edtFname.length() == 0) {
            edtFname.setError("First name is required!");
            checkFlag++;
        }
        if (edtLname.length() == 0) {
            edtLname.setError("Last name is required!");
            checkFlag++;
        }

        if (edtEmail.length() == 0) {
            edtEmail.setError("Email is required!");
            checkFlag++;
        }

        if (edtPassword.length() == 0) {
            edtPassword.setError("Password is required!");
            checkFlag++;
        }

        if (edtConfirmPassword.length() == 0) {
            edtConfirmPassword.setError("Confirm password is required!");
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
        //endregion

        //region check regex
        if (Validation.checkRegexEmail(edtEmail.getText().toString().trim()) == false) {
            edtEmail.setError("Email format is not valid!");
            checkFlag++;
        }

        if (edtBirthday.length() > 0) {
            if (Validation.checkAge(edtBirthday.getText().toString().trim()) == false) {
                edtBirthday.setError("Age must be greater than 13");
                checkFlag++;
            }
        }
        //endregion

        //region check length
        if (edtEmail.length() > 100) {
            edtEmail.setError("Email must <= 100 character");
            checkFlag++;
        }
        if (edtPassword.length() > 50 || edtPassword.length() < 8) {
            edtPassword.setError("Password must from 8 to 50 character");
            checkFlag++;
        }
        if (edtPhone.length() != 10) {
            edtPhone.setError("Phone must be 10 character");
            checkFlag++;
        }
        if (edtFname.length() > 15) {
            edtFname.setError("First name must <= 15");
            checkFlag++;
        }
        if (edtLname.length() > 35) {
            edtLname.setError("Last name must <= 35");
            checkFlag++;
        }
        if (edtAddress1.length() > 100) {
            edtAddress1.setError("Address 1 must <= 100");
            checkFlag++;
        }
        if (edtAddress2.length() > 100) {
            edtAddress2.setError("Address 2 must <= 100");
            checkFlag++;
        }
        //endregion

        //region check confirm password
        if (!edtPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())) {
            edtPassword.setError("Password and Confirm password must match");
            checkFlag++;
        }
        //endregion

        if (checkFlag == 0) {
            return true;
        }
        return false;
    }
    //endregion
}
