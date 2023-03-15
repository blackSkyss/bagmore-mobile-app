package com.example.bagmore.OrderScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ItemCartRVAdapter;
import com.example.bagmore.DetailActivity;
import com.example.bagmore.Interfaces.IClickItemCartItem;
import com.example.bagmore.Models.data.ItemCartViewModel;
import com.example.bagmore.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends AppCompatActivity {

    //region init
    private RecyclerView rcvOrderDetail;

    private ItemCartRVAdapter itemCartRVAdapter;

    private List<ItemCartViewModel> items;

    @BindView(R.id.title_bottom_order)
    TextView total;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ButterKnife.bind(this);

        initToolbar();
        configTextBottom();


        items = (List<ItemCartViewModel>) getIntent().getExtras().get("object_listitem");

        rcvOrderDetail = findViewById(R.id.rcv_detail_order);

        onCallbackHandler();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcvOrderDetail.setLayoutManager(linearLayoutManager);
        rcvOrderDetail.setAdapter(itemCartRVAdapter);
    }

    //region callback handler
    private void onCallbackHandler() {
        itemCartRVAdapter = new ItemCartRVAdapter(new IClickItemCartItem() {
            @Override
            public void handlerButtonClick(ItemCartViewModel viewModel) {

            }

            @Override
            public void moveTo(ItemCartViewModel viewModel) {
                Intent intent = new Intent(OrderDetailActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        }, "Detail", false, true);
        itemCartRVAdapter.setData(items);
    }
    //endregion

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_order_detail);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_order_detail);
        mTitle.setText("ORDER DETAIL");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // visible back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }


    // Back to previous activity
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

    //region config text bottom
    private void configTextBottom() {
        total.setText("TOTAL \u25cf $300");
    }
    //endregion
}