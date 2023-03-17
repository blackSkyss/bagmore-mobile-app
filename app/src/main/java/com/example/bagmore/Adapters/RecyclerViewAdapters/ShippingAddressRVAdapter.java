package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemShippingAddress;
import com.example.bagmore.Models.data.ShippingAddressViewModel;
import com.example.bagmore.R;

import java.util.List;

public class ShippingAddressRVAdapter extends RecyclerView.Adapter<ShippingAddressRVAdapter.MyViewHolder> {

    private List<ShippingAddressViewModel> shippings;

    private IClickItemShippingAddress iClickItemShippingAddress;

    private int checkedPosition = 0;

    public ShippingAddressRVAdapter(IClickItemShippingAddress listener) {
        this.iClickItemShippingAddress = listener;
    }

    public void setData(List<ShippingAddressViewModel> data) {
        this.shippings = data;
        notifyDataSetChanged();
    }

    public void cleanData() {
        shippings.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipping_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ShippingAddressViewModel shipping = shippings.get(position);
        if (shipping == null) {
            return;
        }

        holder.bindImageView(shipping);
    }

    @Override
    public int getItemCount() {
        if (shippings != null) {
            return shippings.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //define component of view holder
        TextView address, subtitle;

        ImageView checkShipping;
        View itemShipping;

        // connect with ui component
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkShipping = itemView.findViewById(R.id.check_shipping);
            itemShipping = itemView.findViewById(R.id.item_shipping);
            address = itemView.findViewById(R.id.tv_address_sp);
            subtitle = itemView.findViewById(R.id.tv_subtitle_sp);

        }

        //region bind view
        public void bindImageView(ShippingAddressViewModel nextSorting) {

            if (checkedPosition == -1) {
                checkShipping.setVisibility(View.INVISIBLE);
                nextSorting.setChecked(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    checkShipping.setVisibility(nextSorting.isChecked() ? View.VISIBLE : View.INVISIBLE);
                } else {
                    checkShipping.setVisibility(View.INVISIBLE);
                    nextSorting.setChecked(false);
                }
            }


            address.setText(nextSorting.getAddress());
            subtitle.setText(nextSorting.getFirstName() + " " + nextSorting.getLastName() + " - " + nextSorting.getPhone());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemShippingAddress.onClickItemShipping(nextSorting);
                    nextSorting.setChecked(!nextSorting.isChecked());
                    checkShipping.setVisibility(nextSorting.isChecked() ? View.VISIBLE : View.INVISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
        //endregion
    }
}
