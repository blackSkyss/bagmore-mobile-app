package com.example.bagmore.OrderScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bagmore.Adapters.TabViewAdapters.CartTVAdapter;
import com.example.bagmore.DeliveryActivity;
import com.example.bagmore.Models.data.ItemCartViewModel;
import com.example.bagmore.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {

    //region init
    private static int CODE = 10;
    private TabLayout ctabLayout;
    private ViewPager cViewPager;
    private CartTVAdapter cCartTVAdapter;
    @BindView(R.id.title_bottom_order)
    TextView titleBottomOrder;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        initToolbar();
        initUI();
        TextBottomHandler();

        cCartTVAdapter = new CartTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, getItemCarts(), getWishlists());

        cViewPager.setAdapter(cCartTVAdapter);
        ctabLayout.setupWithViewPager(cViewPager);

    }

    //region init toolbar
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tb_cart_home);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.tv_cart_title);
        mTitle.setText("Bag More");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            // visible back button
            actionBar.setDisplayHomeAsUpEnabled(true);
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

    //region refresh activity
    private void reFreshActivity() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);

    }
    //endregion

    //region init UI
    private void initUI() {
        ctabLayout = findViewById(R.id.tl_cart_home);
        cViewPager = findViewById(R.id.vp_cart_home);
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

    //region text bottom handler
    private void TextBottomHandler() {
        titleBottomOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivityForResult(i, CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            reFreshActivity();
            Intent intent = new Intent(CartActivity.this, DeliveryActivity.class);
            startActivity(intent);
        }
    }

    //endregion

    //region dummy data || call api here
    public static List<ItemCartViewModel> getItemCarts() {
        List<ItemCartViewModel> itemCarts = new ArrayList<>();
        itemCarts.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img1));
        itemCarts.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img2));
        return itemCarts;
    }

    public static List<ItemCartViewModel> getWishlists() {
        List<ItemCartViewModel> itemCarts = new ArrayList<>();
        itemCarts.add(new ItemCartViewModel(3, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img3));
        itemCarts.add(new ItemCartViewModel(4, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img4));
        return itemCarts;
    }
    //endregion

}