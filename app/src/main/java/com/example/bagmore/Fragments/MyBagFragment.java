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
import com.example.bagmore.Models.data.ItemCartViewModel;
import com.example.bagmore.OrderScreen.CartActivity;
import com.example.bagmore.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MyBagFragment extends Fragment {

    //region init
    @BindView(R.id.rcv_my_bag)
    RecyclerView rcvCart;

    @BindView(R.id.swipe_my_bag)
    SwipeRefreshLayout rfMyBag;

    private ItemCartRVAdapter itemCartRVAdapter;

    private List<ItemCartViewModel> items;

    //endregion
    public MyBagFragment(List<ItemCartViewModel> items) {
        this.items = items;

    }

    public static MyBagFragment newInstance(String param1, String param2, List<ItemCartViewModel> items) {
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
        callBackHandler(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvCart.setLayoutManager(linearLayoutManager);
        rcvCart.setAdapter(itemCartRVAdapter);

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
        activity.refreshActivity();
        rfMyBag.setRefreshing(false);
    }
    //endregion

    //region callback handler
    private void callBackHandler(View view) {
        itemCartRVAdapter = new ItemCartRVAdapter(new IClickItemCartItem() {
            @Override
            public void handlerButtonClick(ItemCartViewModel viewModel) {
                Toast.makeText(view.getContext(), "Remove item from cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void moveTo(ItemCartViewModel viewModel) {
                Toast.makeText(view.getContext(), "Move to wish list", Toast.LENGTH_SHORT).show();
            }
        }, "Move Wishlist", true, false);
        itemCartRVAdapter.setData(items);
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}