package com.example.bagmore.Fragments;

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
import com.example.bagmore.Interfaces.IClickItemCartItem;
import com.example.bagmore.Models.data.CartViewModel;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WishListFragment extends Fragment {

    //region init
    private RecyclerView rcvWishlist;

    private ItemCartRVAdapter itemWishRVAdapter;

    private List<CartViewModel> items;

    @BindView(R.id.swipe_wish_list)
    SwipeRefreshLayout rfWishlist;
    //endregion

    public WishListFragment(List<CartViewModel> items) {
        this.items = items;
    }


    public static WishListFragment newInstance(String param1, String param2, List<CartViewModel> items) {
        WishListFragment fragment = new WishListFragment(items);
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
        rcvWishlist = view.findViewById(R.id.rcv_wish_list);

        onCallbackHandler(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvWishlist.setLayoutManager(linearLayoutManager);
        rcvWishlist.setAdapter(itemWishRVAdapter);
        rfWishlist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onRefreshHandler();
            }
        });
    }

    //region onRefresh handler
    private void onRefreshHandler() {
        CartActivity activity = (CartActivity) getActivity();
        activity.refreshActivity();
        rfWishlist.setRefreshing(false);
    }
    //endregion

    //region callback handler
    private void onCallbackHandler(View view) {
        itemWishRVAdapter = new ItemCartRVAdapter(new IClickItemCartItem() {
            @Override
            public void handlerButtonClick(CartViewModel viewModel) {
                Toast.makeText(view.getContext(), "Remove item from wishlist", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void moveTo(CartViewModel viewModel) {
                Toast.makeText(view.getContext(), "Move to bag", Toast.LENGTH_SHORT).show();
            }
        }, "Move Bag", true, false);
        itemWishRVAdapter.setData(items);
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

}