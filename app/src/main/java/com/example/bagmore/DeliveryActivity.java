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
import com.example.bagmore.Models.data.ItemCartViewModel;
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

        deliveryTVAdapter = new DeliveryTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, getAllData());
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
    private List<OrderViewModel> getAllData() {
        List<OrderViewModel> listOrder = new ArrayList<>();
       // listOrder.add(new OrderViewModel(1, 0, "2019-03-02", "district 9", 1, "fast", getDetail1()));
        listOrder.add(new OrderViewModel(2, 1, "2020-03-02", "district 10", 1, "low", getDetail2()));
        listOrder.add(new OrderViewModel(3, 2, "2021-03-02", "district 11", 1, "fast", getDetail3()));
        listOrder.add(new OrderViewModel(4, 3, "2022-03-02", "district 12", 1, "low", getDetail4()));
        listOrder.add(new OrderViewModel(5, 4, "2023-03-02", "district 13", 1, "fast", getDetail5()));
        listOrder.add(new OrderViewModel(6, 5, "2024-03-02", "district 14", 1, "low", getDetail6()));
        //listOrder.add(new OrderViewModel(7, 6, "2025-03-02", "district 15", 1, "fast", getDetail7()));
        return listOrder;
    }
    //endregion

    //region item 1
    private List<ItemCartViewModel> getDetail1() {
        List<ItemCartViewModel> listItem1 = new ArrayList<>();
        listItem1.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img1));
        listItem1.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img2));
        return listItem1;
    }
    //endregion

    //region item 2
    private List<ItemCartViewModel> getDetail2() {
        List<ItemCartViewModel> listItem2 = new ArrayList<>();
        listItem2.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img3));
        listItem2.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img4));
        return listItem2;
    }
    //endregion

    //region item 3
    private List<ItemCartViewModel> getDetail3() {
        List<ItemCartViewModel> listItem3 = new ArrayList<>();
        listItem3.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img5));
        listItem3.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img6));
        return listItem3;
    }
    //endregion

    //region item 4
    private List<ItemCartViewModel> getDetail4() {
        List<ItemCartViewModel> listItem4 = new ArrayList<>();
        listItem4.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img1));
        listItem4.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img2));
        return listItem4;
    }
    //endregion

    //region item 5
    private List<ItemCartViewModel> getDetail5() {
        List<ItemCartViewModel> listItem5 = new ArrayList<>();
        listItem5.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img1));
        listItem5.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img2));
        return listItem5;
    }
    //endregion

    //region item 6
    private List<ItemCartViewModel> getDetail6() {
        List<ItemCartViewModel> listItem6 = new ArrayList<>();
        listItem6.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img1));
        listItem6.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img2));
        return listItem6;
    }
    //endregion

    //region item 7
    private List<ItemCartViewModel> getDetail7() {
        List<ItemCartViewModel> listItem7 = new ArrayList<>();
        listItem7.add(new ItemCartViewModel(1, "Panic Beg", 2, "L", "Brown", 200, R.drawable.img1));
        listItem7.add(new ItemCartViewModel(2, "Bangle Watch", 1, "L", "Black", 180, R.drawable.img2));
        return listItem7;
    }
    //endregion

}