package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bagmore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_address);

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
                    Intent intentResult = new Intent();
                    setResult(Activity.RESULT_OK, intentResult);
                    finish();
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

        if (checkFlag == 0) {
            return true;
        }
        return false;
    }
    //endregion
}