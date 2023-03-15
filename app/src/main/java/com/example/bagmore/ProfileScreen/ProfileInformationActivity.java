package com.example.bagmore.ProfileScreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.bagmore.R;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileInformationActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST_CODE = 10;
    //region init
    @BindView(R.id.btn_profile_information)
    MaterialButton btnEdit;

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

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_information);
        ButterKnife.bind(this);

        initToolbar();
        onClickHandler();
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
                    disableUpdateMode();
                    Toast.makeText(ProfileInformationActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                }
                return;
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
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the image URI
            Uri imageUri = data.getData();

            // Use Glide to load the image into an ImageView
            Glide.with(this).load(imageUri).into(imgAvatar);
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
                imagePicker();
            }
        });
    }
    //endregion

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
}