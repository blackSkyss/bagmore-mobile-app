package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.bagmore.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutActivity extends AppCompatActivity {

    //region access to component in include layout
    @BindView(R.id.ln_bottom_order)
    View lnBottom;

    @BindView(R.id.title_bottom_order)
    TextView titleBottomCheckout;

    @BindView(R.id.shape_bottom_order)
    View shapeBottomCheckout;

    private View lnDeliveryMethod;

    private View lnShippingAddress;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        ButterKnife.bind(this);

        initUI();
        initToolbar();
        configBottomNavigation();
        onClickHandler();
    }

    //region init UI
    private void initUI() {
        lnDeliveryMethod = findViewById(R.id.ln_delivery_method);
        lnShippingAddress = findViewById(R.id.ln_shipping_address);
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
        titleBottomCheckout.setText("PAY \u25CF $430");
        titleBottomCheckout.setTextSize(15);
        shapeBottomCheckout.setBackgroundColor(Color.BLACK);
    }
    //endregion

    //region click handler
    private void onClickHandler() {
        titleBottomCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResult = new Intent();
                setResult(Activity.RESULT_OK, intentResult);
                finish();
            }
        });

        lnShippingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, ShippingAddressActivity.class);
                startActivity(intent);
            }
        });

        lnDeliveryMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckoutActivity.this, DeliveryMethodActivity.class);
                startActivity(intent);
            }
        });
    }
    //endregion
}