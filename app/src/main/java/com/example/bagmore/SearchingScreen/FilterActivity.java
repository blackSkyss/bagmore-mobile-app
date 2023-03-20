package com.example.bagmore.SearchingScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.bagmore.Adapters.TabViewAdapters.FilterTVAdapter;
import com.example.bagmore.Fragments.filters.CategoryFragment;
import com.example.bagmore.Fragments.filters.ColorFragment;
import com.example.bagmore.Fragments.filters.SizeFragment;
import com.example.bagmore.Models.data.CategoryViewModel;
import com.example.bagmore.Models.data.ColorViewModel;
import com.example.bagmore.Models.data.SizeViewModel;
import com.example.bagmore.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilterActivity extends AppCompatActivity {

    //region init
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ImageView imgClose;
    private TextView txtClearAll;

    @BindView(R.id.title_bottom_order)
    TextView tvFiltering;

    private List<Integer> categoryList = new ArrayList<>();
    private List<Integer> colorList = new ArrayList<>();
    private List<Integer> sizeList = new ArrayList<>();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ButterKnife.bind(this);

        configTextBottom();
        onClickFiltering();

        //set color for action bar
        Window window = FilterActivity.this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(FilterActivity.this, R.color.white));
        }

        //set tabLayout
        setLayout();


        //close Filter -> back to HomeActivity
        imgClose = findViewById(R.id.imgCloseFilter);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to HomeActivity
                finish();
            }
        });

        //clear All -> set all of selected option to false
        txtClearAll = findViewById(R.id.txtClearAll);
        txtClearAll.setOnClickListener(v -> {
            sendSignalClearAll();
        });
    }

    //region set category
    public void setCategories(int categories) {
        categoryList.add(categories);
    }
    //endregion

    //region set color
    public void setColors(int colors) {
        colorList.add(colors);
    }
    //endregion

    //region set size
    public void setSizes(int sizes) {
        sizeList.add(sizes);
    }
    //endregion

    //region config text bottom
    private void configTextBottom() {
        tvFiltering.setText("APPLY FILTER");
    }
    //endregion

    //region handler filtering bottom
    //Apply Filter -> get all value filters -> back to HomeActivity with filtered data
    private void onClickFiltering() {
        tvFiltering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putIntegerArrayListExtra("category_list", (ArrayList<Integer>) categoryList);
                intent.putIntegerArrayListExtra("color_list", (ArrayList<Integer>) colorList);
                intent.putIntegerArrayListExtra("size_list", (ArrayList<Integer>) sizeList);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
    //endregion

    //region get filtered bag
    public String getFilteredBags() {
        //test
        String value = "";
        //end test

        //get FilterViewPagerAdapter
        FilterTVAdapter filterViewPagerAdapter = (FilterTVAdapter) viewPager.getAdapter();
        //Get current Fragment
        CategoryFragment categoryFragment = filterViewPagerAdapter.getCategoryFragment();
        ColorFragment colorFragment = filterViewPagerAdapter.getColorFragment();
        SizeFragment sizeFragment = filterViewPagerAdapter.getSizeFragment();
        List<CategoryViewModel> categories = categoryFragment.getCategoriesFromAdapter();

        for (CategoryViewModel category : categories) {
            if (category.isChecked()) {
                value += category.getName() + "  ";
            }
        }
        List<ColorViewModel> colors = colorFragment.getColorFromAdapter();
        for (ColorViewModel color : colors) {
            if (color.isChecked()) {
                value += color.getName() + " ";
            }
        }

        List<SizeViewModel> sizes = sizeFragment.getSizeFromAdapter();
        for (SizeViewModel size : sizes) {
            if (size.isChecked()) {
                value += size.getName() + " ";
            }
        }
        return value;
    }
    //endregion

    //region clear all handler
    private void sendSignalClearAll() {
        // get current tab position
        int tabPosition = tabLayout.getSelectedTabPosition();

        //get FilterViewPagerAdapter
        FilterTVAdapter filterViewPagerAdapter = (FilterTVAdapter) viewPager.getAdapter();

        //Get current Fragment
        CategoryFragment categoryFragment = filterViewPagerAdapter.getCategoryFragment();
        ColorFragment colorFragment = filterViewPagerAdapter.getColorFragment();
        SizeFragment sizeFragment = filterViewPagerAdapter.getSizeFragment();

        if (categoryFragment != null && tabPosition == 0) {
            List<CategoryViewModel> categories = categoryFragment.getCategoriesFromAdapter();
            for (CategoryViewModel category : categories) {
                if (category.isChecked()) {
                    category.setChecked(false);
                }
            }
            categoryFragment.UpdateCategories(categories);
        }
        if (colorFragment != null && tabPosition == 1) {
            List<ColorViewModel> colors = colorFragment.getColorFromAdapter();
            for (ColorViewModel color : colors) {
                if (color.isChecked()) {
                    color.setChecked(false);
                }
            }
            colorFragment.UpdateColors(colors);
        }

        if (colorFragment != null && tabPosition == 2) {
            List<SizeViewModel> sizes = sizeFragment.getSizeFromAdapter();
            for (SizeViewModel size : sizes) {
                if (size.isChecked()) {
                    size.setChecked(false);
                }
            }
            sizeFragment.updateSizes(sizes);
        }

    }
    //endregion

    //region set tab layout
    private void setLayout() {
        //set tabLayout
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        FilterTVAdapter filterViewPagerAdapter = new FilterTVAdapter(this);
        viewPager.setAdapter(filterViewPagerAdapter);
        String categoryText = "Category";
        String colorText = "Color";
        String sizeText = "Size";

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(categoryText);
                    break;
                case 1:
                    tab.setText(colorText);
                    break;
                case 2:
                    tab.setText(sizeText);
                    break;
            }
        }).attach();
    }
    //endregion
}