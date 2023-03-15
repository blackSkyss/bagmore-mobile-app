package com.example.bagmore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ProductHomeRVAdapter;
import com.example.bagmore.Interfaces.IClickItemProductListener;
import com.example.bagmore.Models.data.ProductViewModel;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.ProfileScreen.ProfileOverallActivity;
import com.example.bagmore.SearchingScreen.FilterActivity;
import com.example.bagmore.SearchingScreen.SearchActivity;
import com.example.bagmore.SearchingScreen.SortActivity;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {


    //region init UI component
    // ProductService productService;
    RecyclerView.LayoutManager layoutManager;
    ProductHomeRVAdapter recyclerViewHomeAdapter;
    private RecyclerView recyclerView;
    private ImageButton btnSearch;
    private MaterialButton btnSort, btnFilter, btnHome, btnNotification, btnDiscovery, btnShop, btnAccount;
    @BindView(R.id.swipe_rf_product)
    SwipeRefreshLayout rfProduct;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initUI();

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        // getAllProduct();
        setCallBackFunction();

        recyclerView.setAdapter(recyclerViewHomeAdapter);
        recyclerView.setHasFixedSize(true);

        OnClickHandler();

    }

    //region call api
//    private void getAllProduct() {
//
//        try {
//            Call<JsonProductViewModel> callGetProducts = productService.getProducts();
//            callGetProducts.enqueue(new Callback<JsonProductViewModel>() {
//                @Override
//                public void onResponse(Call<JsonProductViewModel> call, Response<JsonProductViewModel> response) {
//                    JsonProductViewModel jsonModel = response.body();
//                    List<ProductViewModel> products = jsonModel.getProductViewModels();
//
//                    // setCallBackFunction(products);
//                    Toast.makeText(HomeActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(Call<JsonProductViewModel> call, Throwable t) {
//
//                    if (t instanceof IOException) {
//                        try {
//                            Toast.makeText(HomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//                            throw new IOException("some thing wrong", t);
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    } else {
//                        try {
//                            throw new Exception(t);
//                        } catch (Exception e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                }
//            });
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
    //endregion

    //region set call back
    private void setCallBackFunction() {
        recyclerViewHomeAdapter = new ProductHomeRVAdapter(new IClickItemProductListener() {
            // go to detail screen
            @Override
            public void OnClickImageView(ProductViewModel product) {
                startActivity(new Intent(HomeActivity.this, DetailActivity.class));
            }

            // add product to wish list
            @Override
            public void OnClickAddFavoriteButton(ProductViewModel product) {
                Toast.makeText(HomeActivity.this, "Add to wish list", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnClickRemoveFavoriteButton(ProductViewModel product) {
                Toast.makeText(HomeActivity.this, "Remove from wish list", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewHomeAdapter.setData(getData());
    }
    //endregion

    //region init UI
    private void initUI() {
        recyclerView = findViewById(R.id.rcv);
        btnSearch = findViewById(R.id.btn_search);
        btnSort = findViewById(R.id.btn_sort);
        btnFilter = findViewById(R.id.btn_filter);
        btnHome = findViewById(R.id.btn_home);
        btnNotification = findViewById(R.id.btn_notification);
        btnDiscovery = findViewById(R.id.btn_discovery);
        btnShop = findViewById(R.id.btn_shop);
        btnAccount = findViewById(R.id.btn_account);

//        productService = ProductRepository.getProductService();
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

        // go to search screen
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // go to sort screen
        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SortActivity.class);
                startActivity(intent);
            }
        });

        // go to filter screen
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FilterActivity.class);
                startActivity(intent);
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
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Coming soon", Toast.LENGTH_SHORT).show();
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
                startActivity(intent);
            }
        });
    }
    //endregion

    //region onRefresh handler
    private void onRefreshHandler() {
        recyclerViewHomeAdapter.cleanData();
        recyclerViewHomeAdapter.setData(getData());
        rfProduct.setRefreshing(false);
    }
    //endregion

    //region dummy data
    private List<ProductViewModel> getData() {
        List<ProductViewModel> list = new ArrayList<>();
        list.add(new ProductViewModel(1, "haha", "120", "180", "Wrist watch in gold color metallic", R.drawable.img1));
        list.add(new ProductViewModel(2, "haha", "250", "380", "Women's bag in orange color with strips", R.drawable.img2));
        list.add(new ProductViewModel(3, "haha", "150", "200", "Triple fallen shoulder mini skater dress", R.drawable.img3));
        list.add(new ProductViewModel(4, "haha", "320", "400", "Eva embellished cami midi wedding dress", R.drawable.img4));
        list.add(new ProductViewModel(5, "haha", "110", "210", "Women's bag in orange color with strips", R.drawable.img5));
        list.add(new ProductViewModel(6, "haha", "310", "420", "Wrist watch in gold color metallic", R.drawable.img6));
        return list;
    }
    //endregion
}