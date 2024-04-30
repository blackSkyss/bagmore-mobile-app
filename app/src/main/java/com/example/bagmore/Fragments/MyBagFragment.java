package com.example.bagmore.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.ItemCartRVAdapter;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemCartItem;
import com.example.bagmore.Models.data.CartViewModel;
import com.example.bagmore.Models.data.TokenRefreshViewModel;
import com.example.bagmore.Models.json.request.JsonRefreshTokenReq;
import com.example.bagmore.Models.json.response.JsonLogoutRes;
import com.example.bagmore.Models.json.response.JsonRefreshTokenRes;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.R;
import com.example.bagmore.Repository.CartRepository;
import com.example.bagmore.Repository.UserRepository;
import com.example.bagmore.Services.CartService;
import com.example.bagmore.Services.UserService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBagFragment extends Fragment {

    //region init
    @BindView(R.id.rcv_my_bag)
    RecyclerView rcvCart;

    @BindView(R.id.swipe_my_bag)
    SwipeRefreshLayout rfMyBag;

    private ItemCartRVAdapter itemCartRVAdapter;

    private List<CartViewModel> items;

    private UserService userService;

    private CartService cartService;

    //endregion
    public MyBagFragment(List<CartViewModel> items) {
        this.items = items;

    }

    public static MyBagFragment newInstance(String param1, String param2, List<CartViewModel> items) {
        MyBagFragment fragment = new MyBagFragment(items);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartService = CartRepository.getCartService();
        userService = UserRepository.getUserService();

        callBackHandler(view);
        rfMyBag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reFreshHandler();
            }
        });
    }

    //region refresh handler
    private void reFreshHandler() {
        CartActivity activity = (CartActivity) getActivity();
        activity.getAllCartAPI();
        rfMyBag.setRefreshing(false);
    }
    //endregion

    //region callback handler
    private void callBackHandler(View view) {
        itemCartRVAdapter = new ItemCartRVAdapter(new IClickItemCartItem() {
            @Override
            public void handlerButtonClick(CartViewModel viewModel) {
                new AlertDialog.Builder(getContext()).setTitle("Confirm delete this item!!!").setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        removeItemCartAPI(viewModel);
                    }
                }).setNegativeButton("No", null).show();
            }

            @Override
            public void moveTo(CartViewModel viewModel) {
                Toast.makeText(view.getContext(), "Move to wish list", Toast.LENGTH_SHORT).show();
            }
        }, "Move Wishlist", true, false);

        itemCartRVAdapter.setData(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvCart.setLayoutManager(linearLayoutManager);
        rcvCart.setAdapter(itemCartRVAdapter);
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    //region Call API remove
    private void removeItemCartAPI(CartViewModel cartItem) {
        TokenManager tokenManager = new TokenManager(getContext());
        Call<JsonLogoutRes> callGetCarts = cartService.removeItemCart("bearer " + tokenManager.getAccessToken(), cartItem.getProductId(), cartItem.getColorName(), cartItem.getAmount(), cartItem.getSizeName());
        callGetCarts.enqueue(new Callback<JsonLogoutRes>() {
            @Override
            public void onResponse(Call<JsonLogoutRes> call, Response<JsonLogoutRes> response) {
                if (response.isSuccessful()) {
                    if (getActivity() instanceof CartActivity) {
                        Toast.makeText(getContext(), "Remove successfully", Toast.LENGTH_SHORT).show();
                        ((CartActivity) getActivity()).reFreshActivity();
                    }
                } else if (response.code() == 401) {
                    Toast.makeText(getContext(), "ReAuthentication", Toast.LENGTH_SHORT).show();
                    refreshTokenAPI(cartItem);
                } else {
                    Toast.makeText(getContext(), "Remove failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonLogoutRes> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to call API", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //endregion

    //region refresh token
    public void refreshTokenAPI(CartViewModel itemCart) {
        try {
            TokenManager tokenManager = new TokenManager(getContext());
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
                        removeItemCartAPI(itemCart);
                    } else {
                        tokenManager.clearToken();
                        if (getActivity() instanceof CartActivity) {
                            ((CartActivity) getActivity()).navigation();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonRefreshTokenRes> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion
}