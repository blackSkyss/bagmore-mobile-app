package com.example.bagmore.Fragments.delivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bagmore.Adapters.RecyclerViewAdapters.OrderRVAdapter;
import com.example.bagmore.DeliveryActivity;
import com.example.bagmore.Interfaces.IClickItemOrder;
import com.example.bagmore.Models.data.OrderViewModel;
import com.example.bagmore.OrderScreen.OrderDetailActivity;
import com.example.bagmore.R;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CanceledFragment extends Fragment {

    //region init
    private RecyclerView rcvCanceled;

    private OrderRVAdapter orderRVAdapter;

    private List<OrderViewModel> items;

    @BindView(R.id.swipe_rf_canceled)
    SwipeRefreshLayout rfCanceled;
    //endregion


    public CanceledFragment(List<OrderViewModel> items) {
        this.items = items;
        // Required empty public constructor
    }


    public static CanceledFragment newInstance(String param1, String param2, List<OrderViewModel> items) {
        CanceledFragment fragment = new CanceledFragment(items);
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
        rcvCanceled = view.findViewById(R.id.rcv_canceled);
        orderRVAdapter = new OrderRVAdapter(items, new IClickItemOrder() {
            @Override
            public void onClickHandler(OrderViewModel viewModel) {

                Intent intent = new Intent(view.getContext(), OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object_listitem", (Serializable) viewModel.getProductOrders());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvCanceled.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rcvCanceled.addItemDecoration(itemDecoration);
        rcvCanceled.setAdapter(orderRVAdapter);
        rfCanceled.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reFreshHandler();
            }
        });
    }

    //region refresh handler
    private void reFreshHandler() {
        DeliveryActivity activity = (DeliveryActivity) getActivity();
        activity.refreshActivity();
        rfCanceled.setRefreshing(false);
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_canceled, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}