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
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.bagmore.Adapters.TabViewAdapters.CartTVAdapter;
import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.DeliveryActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Models.data.CartViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonCartRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.R;
import com.example.bagmore.Repository.CartRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.CartService;
import com.example.bagmore.Services.UserService;
import com.example.bagmore.Services.WishListService;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    //region init
    private static int CODE = 10;
    private TabLayout ctabLayout;
    private ViewPager cViewPager;
    private CartTVAdapter cCartTVAdapter;
    @BindView(R.id.title_bottom_order)
    TextView titleBottomOrder;

    UserService userService;

    CartService cartService;

    WishListService wishListService;

    private int totalPrice = 0;
    private boolean acceptCheckout = false;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ButterKnife.bind(this);

        initToolbar();
        initUI();

        getAllCartAPI();

        TextBottomHandler();
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

    //region init tabview
    private void initTabView(List<CartViewModel> cartList, List<CartViewModel> wishList) {
        cCartTVAdapter = new CartTVAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, cartList, wishList);
        cViewPager.setAdapter(cCartTVAdapter);
        ctabLayout.setupWithViewPager(cViewPager);
    }
    //endregion

    //region refresh activity
    public void reFreshActivity() {
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
        cartService = CartRepository.getCartService();
        userService = UserRepository.getUserService();
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
                if (acceptCheckout == false) {
                    Toast.makeText(CartActivity.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                    titleBottomOrder.setText("Nothing to checkout");
                    return;
                }

                Intent i = new Intent(CartActivity.this, CheckoutActivity.class);
                i.putExtra("total_price", totalPrice);
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

    //region get all cart API
    public void getAllCartAPI() {
        TokenManager tokenManager = new TokenManager(getApplicationContext());
        Call<JsonCartRes> callGetCarts = cartService.getCartList("bearer " + tokenManager.getAccessToken());
        callGetCarts.enqueue(new Callback<JsonCartRes>() {
            @Override
            public void onResponse(Call<JsonCartRes> call, Response<JsonCartRes> response) {
                if (response.isSuccessful()) {
                    JsonCartRes jsonModel = response.body();
                    setTitleBottom(jsonModel.getData());
                    initTabView(jsonModel.getData(), null);
                } else if (response.code() == 401) {
                    Toast.makeText(CartActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                    refreshTokenAPI();
                } else {
                    Toast.makeText(CartActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonCartRes> call, Throwable t) {
                Toast.makeText(CartActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region refresh token
    public void refreshTokenAPI() {
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
                        getAllCartAPI();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(CartActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    public void navigation() {
        Intent intent = new Intent(CartActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion

    //region set title bottom
    private void setTitleBottom(List<CartViewModel> itemsCart) {
        int total = 0;
        for (CartViewModel item : itemsCart) {
            total += (item.getPrice().intValue() * item.getAmount());
        }

        totalPrice = total;
        if (total == 0) {
            acceptCheckout = false;
            titleBottomOrder.setText("Nothing to checkout");
        } else {
            acceptCheckout = true;
            titleBottomOrder.setText("Checkout \u25cf $" + total);
        }
    }
    //endregion

}