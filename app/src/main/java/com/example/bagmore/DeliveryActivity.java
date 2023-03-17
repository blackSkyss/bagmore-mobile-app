package com.example.bagmore;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bagmore.Adapters.TabViewAdapters.DeliveryTVAdapter;
import com.example.bagmore.Models.data.CartViewModel;
import com.example.bagmore.Models.data.OrderViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryActivity extends AppCompatActivity {

    //region init
    @BindView(R.id.tl_delivery)
    TabLayout tlDelivery;

    @BindView(R.id.vp_delivery)
    ViewPager vpDelivery;

    private DeliveryTVAdapter deliveryTVAdapter;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        ButterKnife.bind(this);

        initToolbar();

        deliveryTVAdapter = new DeliveryTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, null);
        vpDelivery.setAdapter(deliveryTVAdapter);
        tlDelivery.setupWithViewPager(vpDelivery);
    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_delivery);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_delivery);
        mTitle.setText("DELIVERY");
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

    //region refresh activity
    public void refreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
    //endregion

    //region dummy data order

    //endregion



}