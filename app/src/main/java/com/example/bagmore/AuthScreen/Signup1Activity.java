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

import com.example.bagmore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Signup1Activity extends AppCompatActivity {

    //region init
    @BindView(R.id.btn_continue)
    Button btnContinue;

    @BindView(R.id.edt_email_signup)
    EditText edtEmail;

    @BindView(R.id.edt_password_signup)
    EditText edtPassword;

    @BindView(R.id.edt_cf_password_signup)
    EditText edtCfPassword;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        ButterKnife.bind(this);

        initToolbar();
        onClickHandler();
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_signup);
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
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean check = validation();
                if (check) {
                    Intent intent = new Intent(Signup1Activity.this, Signup2Activity.class);
                    startActivity(intent);
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
        if (edtCfPassword.length() == 0) {
            edtCfPassword.setError("Confirm password is required!");
            checkFlag++;
        }

        if (!edtPassword.getText().toString().trim().equals(edtCfPassword.getText().toString().trim())) {
            Toast.makeText(this, "Two password must same value", Toast.LENGTH_SHORT).show();
            checkFlag++;
        }

        if (checkFlag == 0) {
            return true;
        }
        return false;
    }
    //endregion
}
