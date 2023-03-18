package com.example.bagmore.OrderScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ItemCartRVAdapter;
import com.example.bagmore.DetailActivity;
import com.example.bagmore.Interfaces.IClickItemCartItem;
import com.example.bagmore.Models.data.CartViewModel;
import com.example.bagmore.Models.data.OrderItemViewModel;
import com.example.bagmore.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderDetailActivity extends AppCompatActivity {

    //region init
    private RecyclerView rcvOrderDetail;

    private ItemCartRVAdapter itemCartRVAdapter;

    private List<CartViewModel> itemCarts;

    private List<OrderItemViewModel> itemOrders;

    @BindView(R.id.title_bottom_order)
    TextView total;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        ButterKnife.bind(this);

        initToolbar();


        itemOrders = (List<OrderItemViewModel>) getIntent().getExtras().get("object_listitem");
        rcvOrderDetail = findViewById(R.id.rcv_detail_order);

        onCallbackHandler(itemOrders);
        configTextBottom();
    }

    //region callback handler
    private void onCallbackHandler(List<OrderItemViewModel> items) {
        itemCartRVAdapter = new ItemCartRVAdapter(new IClickItemCartItem() {
            @Override
            public void handlerButtonClick(CartViewModel viewModel) {

            }

            @Override
            public void moveTo(CartViewModel viewModel) {

                Intent intent = new Intent(OrderDetailActivity.this, DetailActivity.class);
                intent.putExtra("product", viewModel.getProductId());
                startActivity(intent);
            }
        }, "Detail", false, true);

        itemCarts = convertToCartViewModel(items);
        itemCartRVAdapter.setData(itemCarts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        rcvOrderDetail.setLayoutManager(linearLayoutManager);
        rcvOrderDetail.setAdapter(itemCartRVAdapter);
    }
    //endregion

    //region convert OrderItemViewModel to CartViewModel
    private List<CartViewModel> convertToCartViewModel(List<OrderItemViewModel> items) {
        List<CartViewModel> itemCarts = new ArrayList<>();
        for (OrderItemViewModel itemOrder : items) {
            CartViewModel itemCart = new CartViewModel(
                    itemOrder.getProductId(),
                    itemOrder.getProductName(),
                    itemOrder.getPrice(),
                    0.5f,
                    itemOrder.getAmount(),
                    itemOrder.getColorName(),
                    itemOrder.getSizeName(),
                    itemOrder.getProductsImage());
            itemCarts.add(itemCart);
        }
        return itemCarts;
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
        total.setText("TOTAL \u25cf $" + getTotalAmount());
    }
    //endregion

    //region get total amount
    private int getTotalAmount() {
        int total = 0;
        for (CartViewModel item : itemCarts) {
            total += item.getAmount() * item.getPrice().intValue();
        }
        return total;
    }
    //endregion

}