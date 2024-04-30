package com.example.bagmore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ProductHomeRVAdapter;
import com.example.bagmore.AuthScreen.LoginActivity;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemProductListener;
import com.example.bagmore.Models.data.ProductViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonProductViewModel;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.ProfileScreen.ProfileOverallActivity;
import com.example.bagmore.Repository.ProductRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.SearchingScreen.FilterActivity;
import com.example.bagmore.SearchingScreen.SortActivity;
import com.example.bagmore.Services.ProductService;
import com.example.bagmore.Services.UserService;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {


    //region init UI component
    private static int CODE_SORT = 10;

    private static int CODE_FILTER = 20;
    ProductService productService;
    RecyclerView.LayoutManager layoutManager;
    ProductHomeRVAdapter recyclerViewHomeAdapter;
    private RecyclerView recyclerView;
    private MaterialButton btnSort, btnFilter, btnHome, btnMap, btnDiscovery, btnShop, btnAccount;
    @BindView(R.id.swipe_rf_product)
    SwipeRefreshLayout rfProduct;
    String email;
    UserService userService;

    private String keySort;
    private List<Integer> categories = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private List<Integer> sizes = new ArrayList<>();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initUI();

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        setCallBackFunction();
        getAllProduct();
        OnClickHandler();

        recyclerView.setAdapter(recyclerViewHomeAdapter);
        recyclerView.setHasFixedSize(true);

        Intent intent = getIntent();
        email = (String) intent.getSerializableExtra("email");

    }

    //region call api
    private void getAllProduct() {
        try {
            TokenManager tokenManager = new TokenManager(getApplicationContext());
            String token = tokenManager.getAccessToken();
            Call<JsonProductViewModel> callGetProducts = productService.getProducts("bearer " + token, keySort, categories, colors, sizes);
            callGetProducts.enqueue(new Callback<JsonProductViewModel>() {
                @Override
                public void onResponse(Call<JsonProductViewModel> call, Response<JsonProductViewModel> response) {
                    if (response.isSuccessful()) {
                        JsonProductViewModel jsonModel = response.body();
                        recyclerViewHomeAdapter.setData(jsonModel.getProductViewModels());
                        recyclerViewHomeAdapter.notifyDataSetChanged();
                    } else if (response.code() == 401) {
                        Toast.makeText(HomeActivity.this, "ReAuthentication", Toast.LENGTH_SHORT).show();
                        refreshTokenAPI();
                    } else {
                        Toast.makeText(HomeActivity.this, "Load failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonProductViewModel> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Failed to all API", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region set call back
    private void setCallBackFunction() {
        recyclerViewHomeAdapter = new ProductHomeRVAdapter(new IClickItemProductListener() {
            // go to detail screen
            @Override
            public void OnClickImageView(ProductViewModel product) {
                Intent intent = new Intent(HomeActivity.this, DetailActivity.class);
                intent.putExtra("product", product.getId());
                startActivity(intent);
            }

            // add product to wish list
            @Override
            public void OnClickAddFavoriteButton(ProductViewModel product) {
                Toast.makeText(HomeActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnClickRemoveFavoriteButton(ProductViewModel product) {
                Toast.makeText(HomeActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region init UI
    private void initUI() {
        recyclerView = findViewById(R.id.rcv);
        btnSort = findViewById(R.id.btn_sort);
        btnFilter = findViewById(R.id.btn_filter);
        btnHome = findViewById(R.id.btn_home);
        btnMap = findViewById(R.id.btn_map);
        btnDiscovery = findViewById(R.id.btn_discovery);
        btnShop = findViewById(R.id.btn_shop);
        btnAccount = findViewById(R.id.btn_account);
        productService = ProductRepository.getProductService();
        userService = UserRepository.getUserService();
    }
    //endregion

    //region click handler logic
    private void OnClickHandler() {
        rfProduct.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshHandler();
            }
        });

        // go to sort screen
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iSort = new Intent(HomeActivity.this, SortActivity.class);
                startActivityForResult(iSort, CODE_SORT);
            }
        });

        // go to filter screen
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iFilter = new Intent(HomeActivity.this, FilterActivity.class);
                startActivityForResult(iFilter, CODE_FILTER);
            }
        });

        // reload home screen
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });

        // go to notification screen
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // go to discovery screen
        btnDiscovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        // go to cart screen
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        // go to account screen
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileOverallActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }
    //endregion

    //region onRefresh handler
    private void onRefreshHandler() {
        getAllProduct();
        recyclerViewHomeAdapter.notifyDataSetChanged();
        rfProduct.setRefreshing(false);
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
                        getAllProduct();
                    } else {
                        tokenManager.clearToken();
                        navigation();
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(HomeActivity.this, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    //region navigation
    private void navigation() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_SORT && resultCode == Activity.RESULT_OK) {
            keySort = data.getStringExtra("key_sort");
            onRefreshHandler();
        } else if (requestCode == CODE_FILTER && resultCode == Activity.RESULT_OK) {
            categories = (List<Integer>) data.getIntegerArrayListExtra("category_list");
            colors = (List<Integer>) data.getIntegerArrayListExtra("color_list");
            sizes = (List<Integer>) data.getIntegerArrayListExtra("size_list");
            onRefreshHandler();
        }
    }
}