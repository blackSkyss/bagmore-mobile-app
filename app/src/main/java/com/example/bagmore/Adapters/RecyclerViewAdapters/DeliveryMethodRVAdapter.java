package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemDeliveryMethod;
import com.example.bagmore.Models.data.DeliveryMethodViewModel;
import com.example.bagmore.R;

import java.util.List;

public class DeliveryMethodRVAdapter extends RecyclerView.Adapter<DeliveryMethodRVAdapter.MyViewHolder> {


    private List<DeliveryMethodViewModel> deliveries;

    private IClickItemDeliveryMethod iClickItemDeliveryMethod;

    private int checkedPosition = 0;

    public DeliveryMethodRVAdapter(IClickItemDeliveryMethod listener) {
        this.iClickItemDeliveryMethod = listener;
    }

    public void setData(List<DeliveryMethodViewModel> data) {
        this.deliveries = data;
        notifyDataSetChanged();
    }

    public void cleanData() {
        deliveries.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DeliveryMethodViewModel delivery = deliveries.get(position);
        if (delivery == null) {
            return;
        }

        holder.bindImageView(delivery);
    }

    @Override
    public int getItemCount() {
        if (deliveries != null) {
            return deliveries.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //define component of view holder
        ImageView checkDelivery;
        TextView title, description;
        View itemDelivery;

        // connect with ui component
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemDelivery = itemView.findViewById(R.id.item_delivery);
            checkDelivery = itemView.findViewById(R.id.check_delivery);
            title = itemView.findViewById(R.id.tv_names_price_delivery);
            description = itemView.findViewById(R.id.tv_description_delivery);
        }

        //region bind view
        public void bindImageView(DeliveryMethodViewModel nextSorting) {

            if (checkedPosition == -1) {
                checkDelivery.setVisibility(View.INVISIBLE);
                nextSorting.setChecked(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    checkDelivery.setVisibility(nextSorting.isChecked() ? View.VISIBLE : View.INVISIBLE);
                } else {
                    checkDelivery.setVisibility(View.INVISIBLE);
                    nextSorting.setChecked(false);
                }
            }

            if (nextSorting.getPrice() > 0) {
                title.setText(nextSorting.getName() + " - " + nextSorting.getPrice() + "$");
            } else {
                title.setText(nextSorting.getName() + " - Free");
            }

            description.setText(nextSorting.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemDeliveryMethod.onClickItemDelivery(nextSorting);
                    nextSorting.setChecked(!nextSorting.isChecked());
                    checkDelivery.setVisibility(nextSorting.isChecked() ? View.VISIBLE : View.INVISIBLE);
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
