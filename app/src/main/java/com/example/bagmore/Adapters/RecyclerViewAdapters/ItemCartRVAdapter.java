package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemCartItem;
import com.example.bagmore.Models.data.ItemCartViewModel;
import com.example.bagmore.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ItemCartRVAdapter extends RecyclerView.Adapter<ItemCartRVAdapter.MyViewHolder> {


    private List<ItemCartViewModel> cartItems;

    private IClickItemCartItem clickItemCartItem;

    private String titleButton;

    private boolean isEnableClose;

    private boolean isHaveBtn;


    public ItemCartRVAdapter(IClickItemCartItem listener, String titleButton, boolean isEnable, boolean isHaveBtn) {
        this.clickItemCartItem = listener;
        this.titleButton = titleButton;
        this.isEnableClose = isEnable;
        this.isHaveBtn = isHaveBtn;
    }

    public void setData(List<ItemCartViewModel> cartItems){
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    public void cleanData(){
        cartItems.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ItemCartViewModel itemCart = cartItems.get(position);
        if (itemCart == null) {
            return;
        }

        if (isEnableClose == false) {
            holder.btnRemove.setVisibility(View.INVISIBLE);
        }

        if (isHaveBtn == false) {
            holder.btnMove.setVisibility(View.INVISIBLE);
        }

        holder.imgItem.setImageResource(itemCart.getImage());
        holder.tvTitle.setText(itemCart.getName() + " X " + itemCart.getQuantity());
        holder.tvSize.setText("Size: " + itemCart.getSize());
        holder.tvColor.setText("Color:" + itemCart.getColor());
        holder.tvPrice.setText("$" + String.valueOf(itemCart.getPrice()));
        holder.btnMove.setText(titleButton);
        holder.btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemCartItem.moveTo(itemCart);
            }
        });

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemCartItem.handlerButtonClick(itemCart);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (cartItems != null) {
            return cartItems.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //define component of view holder
        ImageView imgItem;
        MaterialButton btnRemove, btnMove;
        TextView tvTitle, tvSize, tvColor, tvPrice;


        // connect with ui component
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.iv_cart_item);
            btnRemove = itemView.findViewById(R.id.btn_delete_item);
            btnMove = itemView.findViewById(R.id.btn_move);
            tvTitle = itemView.findViewById(R.id.tv_title_cart_item);
            tvSize = itemView.findViewById(R.id.tv_size_cart_item);
            tvColor = itemView.findViewById(R.id.tv_color_cart_item);
            tvPrice = itemView.findViewById(R.id.tv_price_cart_item);
        }

    }
}
