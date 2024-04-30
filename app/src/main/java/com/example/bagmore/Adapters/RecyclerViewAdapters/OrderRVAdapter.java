package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemOrder;
import com.example.bagmore.Models.data.OrderViewModel;
import com.example.bagmore.R;

import java.util.List;

public class OrderRVAdapter extends RecyclerView.Adapter<OrderRVAdapter.MyViewHolder> {


    private List<OrderViewModel> orders;

    private IClickItemOrder iClickItemOrder;


    public OrderRVAdapter(List<OrderViewModel> items, IClickItemOrder listener) {
        this.orders = items;
        this.iClickItemOrder = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final OrderViewModel order = orders.get(position);
        if (order == null) {
            return;
        }

        holder.tvDeliveryStatus.setText("Delivery status: " + String.valueOf(order.getDeliveryStatus()));
        holder.tvOrderDate.setText("Order date: " + order.getOrderDay());
        holder.tvShipAddress.setText("Shipping address: " + order.getShipAddress());
        holder.tvDeliveryMethod.setText("Delivery method: " + order.getDeliveryMethod());

        holder.lnCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemOrder.onClickHandler(order);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (orders != null) {
            return orders.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //define component of view holder
        TextView tvDeliveryStatus, tvOrderDate, tvShipAddress, tvDeliveryMethod;

        View lnCartItem;
        // connect with ui component

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDeliveryStatus = itemView.findViewById(R.id.tv_delivery_status);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvShipAddress = itemView.findViewById(R.id.tv_ship_address);
            tvDeliveryMethod = itemView.findViewById(R.id.tv_delivery_mt);
            lnCartItem = itemView.findViewById(R.id.ln_order_item);
        }

    }
}
