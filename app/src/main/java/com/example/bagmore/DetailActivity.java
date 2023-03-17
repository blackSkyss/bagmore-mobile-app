package com.example.bagmore;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.palette.graphics.Palette;
import androidx.viewpager.widget.ViewPager;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bagmore.Adapters.TabViewAdapters.ProductHomeTVAdapter;
import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.Models.data.ProductImageViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonProductDetailRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.Repository.ProductRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.ProductService;
import com.example.bagmore.Services.UserService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    //region init
    private TabLayout mtabLayout;
    private ViewPager mViewPager;
    private ProductHomeTVAdapter mViewPagerAdapter;
    private MaterialButton btnCart;
    ProductService productService;

    UserService userService;
    private int productId;

    ImageSlider imageSlider;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        userService = UserRepository.getUserService();
        imageSlider = findViewById(R.id.img_slider);

        // init and config tool bar
        initToolbar();

        btnCart = findViewById(R.id.btn_cart);
        mtabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_page);
        productService = ProductRepository.getProductService();

        getProductDetailById();
        OnClickHandler();
    }


    //region Call API detail
    private void getProductDetailById() {

        Intent intent = getIntent();
        productId = (int) intent.getSerializableExtra("product");

        TokenManager tokenManager = new TokenManager(getApplicationContext());
        Call<JsonProductDetailRes> callGetProducts = productService.getProductDetailById("bearer " + tokenManager.getAccessToken(), productId);
        callGetProducts.enqueue(new Callback<JsonProductDetailRes>() {
            @Override
            public void onResponse(Call<JsonProductDetailRes> call, Response<JsonProductDetailRes> response) {
                if (response.isSuccessful()) {
                    JsonProductDetailRes jsonModel = response.body();

                    List<String> imageString = new ArrayList<>();
                    List<ProductImageViewModel> listImage = response.body().getData().ProductImages;
                    for (ProductImageViewModel image : listImage) {
                        imageString.add(image.Source);
                    }
                    if (imageString != null) {
                        initSlide(imageString);
                    }

                    intiTabBar(jsonModel.getData());

                } else if (response.code() == 401) {
                    Toast.makeText(DetailActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                    refreshTokenAPI();
                } else {
                    Toast.makeText(DetailActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonProductDetailRes> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region init image slide
    private void initSlide(List<String> imageList) {
        List<SlideModel> slideModels = new ArrayList<>();

        for (String image : imageList) {
            try {

                byte[] slideData = image.getBytes("UTF-8");

                // Create a ByteArrayInputStream from the byte array
                ByteArrayInputStream bis = new ByteArrayInputStream(slideData);

                // Create an ObjectInputStream to deserialize the byte stream
                ObjectInputStream ois = new ObjectInputStream(bis);

                // Deserialize the byte stream into a SlideModel object
                SlideModel slideModel = (SlideModel) ois.readObject();

                // Add the SlideModel object to the list
                slideModels.add(slideModel);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // binding image to slider
        imageSlider.setImageList(slideModels);
    }
    //endregion

    //region init tab bar
    private void intiTabBar(ProductDetailViewModel product) {
        mViewPagerAdapter = new ProductHomeTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, product);
        // binding fragment to tab layout
        mViewPager.setAdapter(mViewPagerAdapter);
        mtabLayout.setupWithViewPager(mViewPager);

        // set animation when collapsing tool bar
        setTabLayAnimation();
    }
    //endregion

    //region init toolbar
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
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
    //endregion

    //region onClick handler
    private void OnClickHandler() {
        // go to cart screen
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(DetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

    }
    //endregion

    //region refresh token
    private void refreshTokenAPI() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            JsonRefreshTokenReq json = new JsonRefreshTokenReq(tokenManager.getAccessToken(), tokenManager.getRefreshToken());
            Call<JsonRefreshTokenRes> result = userService.userRefreshToken(json);
            result.enqueue(new Callback<JsonRefreshTokenRes>() {
                @Override
                public void onResponse(Call<JsonRefreshTokenRes> call, Response<JsonRefreshTokenRes> response) {
                    if (response.isSuccessful()) {
                        JsonRefreshTokenRes jsonRefreshTokenResponse = response.body();
                        TokenRefreshViewModel tokenRefresh = jsonRefreshTokenResponse.getData();
                        tokenManager.clearToken();
                        tokenManager.saveToken(tokenRefresh.getAccessToken(), tokenRefresh.getRefreshToken());
                        getProductDetailById();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(DetailActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    public void navigation() {
        Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion
}