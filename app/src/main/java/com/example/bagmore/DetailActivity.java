package com.example.bagmore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.adapters.ViewPagerAdapter;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bagmore.Adapters.TabViewAdapters.ProductHomeTVAdapter;
import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.Models.data.ProductViewModel;
import com.example.bagmore.Models.json.request.JsonProductDetailReq;
import com.example.bagmore.Models.json.response.JsonProductDetailRes;
import com.example.bagmore.Models.json.response.JsonProductViewModel;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.Repository.ProductRepository;
import com.example.bagmore.SearchingScreen.SortActivity;
import com.example.bagmore.Services.ProductService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TabLayout mtabLayout;
    private ViewPager mViewPager;
    private ProductHomeTVAdapter mViewPagerAdapter;
    private MaterialButton btnCart;
    private Button btnAddToCart;
    ProductService productService;
    private int productId;
    ProductDetailViewModel product = new ProductDetailViewModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageSlider imageSlider = findViewById(R.id.img_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        // set image for image slider
        slideModels.add(new SlideModel(R.drawable.img1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.img4, ScaleTypes.FIT));

        // binding image to slider
        imageSlider.setImageList(slideModels);

        // init and config tool bar
        initToolbar();

        btnCart = findViewById(R.id.btn_cart);
        btnAddToCart = findViewById(R.id.btn_add_cart);
        mtabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_page);

        mViewPagerAdapter = new ProductHomeTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        // binding fragment to tab layout
        mViewPager.setAdapter(mViewPagerAdapter);
        mtabLayout.setupWithViewPager(mViewPager);

        // set animation when collapsing tool bar
        setTabLayAnimation();

        // set click handler for button
        OnClickHandler();
        productService = ProductRepository.getProductService();
          ProductDetailViewModel productDetail =  getProductDetailById();

        }


        private  ProductDetailViewModel getProductDetailById(){

            Intent intent = getIntent();
            productId = (int) intent.getSerializableExtra("product");
            Call<JsonProductDetailRes> callGetProducts = productService.getProductDetailById(productId);
            callGetProducts.enqueue(new Callback<JsonProductDetailRes>() {
                @Override
                public void onResponse(Call<JsonProductDetailRes> call, Response<JsonProductDetailRes> response) {
                    if (response.isSuccessful()) {
                        JsonProductDetailRes jsonModel = response.body();
                         product = jsonModel.getData();
                        Toast.makeText(DetailActivity.this, "Success detail", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DetailActivity.this, "Vao day", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonProductDetailRes> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "VKLKKKKK vcc", Toast.LENGTH_SHORT).show();
                }
            });
            return product;
        }
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.tool_bar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Bag More");
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
        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTabLayAnimation() {
        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img3);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                int myColor = palette.getVibrantColor(getResources().getColor(R.color.black));
                int myDarkColor = palette.getVibrantColor(getResources().getColor(R.color.black));
                collapsingToolbarLayout.setContentScrimColor(myColor);
                collapsingToolbarLayout.setStatusBarScrimColor(myDarkColor);
            }
        });
    }

    private void OnClickHandler(){
        // go to cart screen
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // add current product to cart
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailActivity.this, "Add to cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}