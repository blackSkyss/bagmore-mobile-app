package com.example.bagmore.AuthScreen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bagmore.HandlerException.Dialog;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.UserService;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    private byte[] imageAvt;
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

    //region onClick handler
    private void onClickHandler() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePicker();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        edtBirthday.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
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
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image URI

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            imageAvt = outputStream.toByteArray();

            Uri imageUri = data.getData();
            // Use Glide to load the image into an ImageView
            Glide.with(this).load(imageUri).into(imgAvatar);

        }
    }

    //endregion

    //region validation
    private boolean validation() {
        int checkFlag = 0;
        if (edtFname.length() == 0) {
            edtFname.setError("First name is required!");
            checkFlag++;
        }
        if (edtLname.length() == 0) {
            edtLname.setError("Last name is required!");
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

    //region Call Signup API
    private void signupAPI() {
        RequestBody requestBody = fillBody();
        Call<JsonLogoutRes> result = userService.userRegister(requestBody);
        result.enqueue(new Callback<JsonLogoutRes>() {
            @Override
            public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    navigation();

                } else {
                    Dialog.showDialog(SignupActivity.this, "Signup execution", "Sign up failed!");
                }
            }

            @Override
            public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region Request body
    private RequestBody fillBody() {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("Email", edtEmail.getText().toString().trim())
                .addFormDataPart("Password", edtPassword.getText().toString().trim())
                .addFormDataPart("Gender", gender.trim())
                .addFormDataPart("FirstName", edtFname.getText().toString().trim())
                .addFormDataPart("LastName", edtLname.getText().toString().trim())
                .addFormDataPart("BirthDay", edtBirthday.getText().toString().trim())
                .addFormDataPart("Phone", edtPhone.getText().toString().trim())
                .addFormDataPart("FirstAddress", edtAddress1.getText().toString().trim())
                .addFormDataPart("SecondAddress", edtAddress2.getText().toString().trim())
                .addFormDataPart("Image", imageAvt.toString())
                .build();

        return requestBody;
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
}
