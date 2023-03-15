package com.example.bagmore.OrderScreen;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.DeliveryMethodRVAdapter;
import com.example.bagmore.Interfaces.IClickItemDeliveryMethod;
import com.example.bagmore.Models.data.DeliveryMethodViewModel;
import com.example.bagmore.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeliveryMethodActivity extends AppCompatActivity {

    //region init
    private RecyclerView.LayoutManager layoutManager;

    private DeliveryMethodRVAdapter deliveryMethodRVAdapter;

    private RecyclerView recyclerView;

    @BindView(R.id.title_bottom_order)
    TextView titleBottom;

    @BindView(R.id.swipe_rf_delivery)
    SwipeRefreshLayout rfDelivery;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_method);
        ButterKnife.bind(this);

        initUI();
        initToolbar();
        setCallbackDeliveryItem();
        configBottomNavigation();
        onClickHandler();

        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(deliveryMethodRVAdapter);
        recyclerView.setHasFixedSize(true);
    }

    //region set callback recycler view
    private void setCallbackDeliveryItem() {
        deliveryMethodRVAdapter = new DeliveryMethodRVAdapter(new IClickItemDeliveryMethod() {
            @Override
            public void onClickItemDelivery(DeliveryMethodViewModel viewModel) {
                Toast.makeText(DeliveryMethodActivity.this, viewModel.getName() + "Selected", Toast.LENGTH_SHORT).show();
            }
        });
        deliveryMethodRVAdapter.setData(getListDelivery());
    }
    //endregion

    //region init ui
    private void initUI() {
        recyclerView = findViewById(R.id.rcv_delivery);
    }
    //endregion

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_delivery_method);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_delivery_title);
        mTitle.setText("DELIVERY METHOD");
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

    //region click handler
    private void onClickHandler() {
        titleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rfDelivery.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshHandler();
            }
        });

    }
    //endregion

    //region onReFreshHandler
    private void onRefreshHandler() {
        deliveryMethodRVAdapter.cleanData();
        deliveryMethodRVAdapter.setData(getListDelivery());
        rfDelivery.setRefreshing(false);
    }
    //endregion

    //region dummy data || call api here
    private List<DeliveryMethodViewModel> getListDelivery() {
        List<DeliveryMethodViewModel> list = new ArrayList<>();
        list.add(new DeliveryMethodViewModel(1, "Standard Shipping", 0, "Shipping 4-6 working days", 1));
        list.add(new DeliveryMethodViewModel(2, "Next day", 30, "Shipping 4-6 working days", 1));
        return list;
    }
    //endregion
}