package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ShippingAddressRVAdapter;
import com.example.bagmore.Interfaces.IClickItemShippingAddress;
import com.example.bagmore.Models.data.ShippingAddressViewModel;
import com.example.bagmore.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShippingAddressActivity extends AppCompatActivity {

    //region init
    private static final int CODE = 10;
    private MaterialButton mtAddShipping;

    private RecyclerView.LayoutManager layoutManager;

    private ShippingAddressRVAdapter shippingAddressRVAdapter;

    private RecyclerView recyclerView;

    @BindView(R.id.title_bottom_order)
    TextView titleBottom;

    @BindView(R.id.swipe_rf_shipping)
    SwipeRefreshLayout rfShipping;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        ButterKnife.bind(this);

        initUI();
        initToolbar();
        setCallbackShippingItem();
        configBottomNavigation();
        handlerClick();

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(shippingAddressRVAdapter);
        recyclerView.setHasFixedSize(true);
    }

    //region set callback recycler view
    private void setCallbackShippingItem() {
        shippingAddressRVAdapter = new ShippingAddressRVAdapter(new IClickItemShippingAddress() {
            @Override
            public void onClickItemShipping(ShippingAddressViewModel viewModel) {
                Toast.makeText(ShippingAddressActivity.this, viewModel.getAddress() + "Selected", Toast.LENGTH_SHORT).show();
            }
        });
        shippingAddressRVAdapter.setData(getData());
    }
    //endregion

    //region init UI
    private void initUI() {
        mtAddShipping = findViewById(R.id.btn_create_address);
        recyclerView = findViewById(R.id.rcv_shipping);
    }
    //endregion

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_shipping_address);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_shipping_title);
        mTitle.setText("SHIPPING ADDRESS");
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
        titleBottom.setText("DONE");
        titleBottom.setTextSize(20);
    }
    //endregion

    //region handler click event
    private void handlerClick() {
        mtAddShipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShippingAddressActivity.this, CreateAddressActivity.class);
                startActivityForResult(intent, CODE);
            }
        });

        titleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rfShipping.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshHandler();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            rfShipping.setRefreshing(true);
            onRefreshHandler();
            Toast.makeText(this, "Add successfully", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region onRefresh handler
    private void onRefreshHandler() {
        List<ShippingAddressViewModel> list = getData();
        list.add(new ShippingAddressViewModel(5, "Nettie Gordon", "741 848 9295", "406 Mihe Ridge, Baijeze", 1, R.drawable.img5));
        shippingAddressRVAdapter.cleanData();
        shippingAddressRVAdapter.setData(list);
        rfShipping.setRefreshing(false);
    }
    //endregion

    //region dummy data || call api here
    private List<ShippingAddressViewModel> getData() {
        List<ShippingAddressViewModel> list = new ArrayList<>();
        list.add(new ShippingAddressViewModel(1, "Francis Delgado", "755 707 1486", "57 Naci Terrace, Hunidpis", 1, R.drawable.img1));
        list.add(new ShippingAddressViewModel(2, "Brian Griffin", "029 105 810", "21 Prince, Singapore, AR 719", 1, R.drawable.img2));
        list.add(new ShippingAddressViewModel(3, "Roger Lyons", "472 471 1925", "142 Reda View, Ljpobdad", 1, R.drawable.img3));
        list.add(new ShippingAddressViewModel(4, "Ricardo Higgins", "409 828 2536", "824 Kihoj Pike, Ickuhiw", 1, R.drawable.img4));
        list.add(new ShippingAddressViewModel(5, "Nettie Gordon", "741 848 9295", "406 Mihe Ridge, Baijeze", 1, R.drawable.img5));
        return list;
    }
    //endregion
}