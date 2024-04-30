package com.example.bagmore.AuthScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bagmore.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Button btn_sign_up_email = findViewById(R.id.btn_sign_up_email);
        Button btn_sign_in_email = findViewById(R.id.btn_sign_in_email);

        initToolbar();


        //region handler button
        btn_sign_up_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyen den screen dang ki voi email.
                Intent intent = new Intent(WelcomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        btn_sign_in_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chuyen den screen dang nhap voi email.
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        //endregion
    }

    //region back button
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_welcome);
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
}