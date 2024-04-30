package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemSizeFilter;
import com.example.bagmore.Models.data.SizeViewModel;
import com.example.bagmore.R;

import java.util.List;

public class SizeRVAdapter extends RecyclerView.Adapter<SizeRVAdapter.SizeViewHolder> {
    private Context context;
    private List<SizeViewModel> sizes;

    private static IClickItemSizeFilter iClickItemSizeFilter;

    public SizeRVAdapter(Context context, IClickItemSizeFilter iClickItemSizeFilter) {
        this.context = context;
        this.iClickItemSizeFilter  = iClickItemSizeFilter;
    }

    public void setData(List<SizeViewModel> sizes) {
        this.sizes = sizes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SizeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filter_item, parent, false);
        return new SizeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SizeViewHolder holder, int position) {
        SizeViewModel size = sizes.get(position);
        if (size == null) {
            return;
        }
        holder.bindImageView(size);
    }

    @Override
    public int getItemCount() {
        if (sizes == null) {
            return 0;
        }
        return sizes.size();
    }

    public static class SizeViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private CardView cartColor;

        public SizeViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtNameItem);
            imageView = itemView.findViewById(R.id.imgCheckedItem);
            cartColor = itemView.findViewById(R.id.cartColor);
        }

        public void bindImageView(SizeViewModel size) {
            imageView.setVisibility(size.isChecked() ? View.VISIBLE : View.INVISIBLE);
            textView.setText(size.getName());
            cartColor.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemSizeFilter.onClickHandler(size);
                    size.setChecked(!size.isChecked());
                    imageView.setVisibility(size.isChecked() ? View.VISIBLE : View.INVISIBLE);
                }
            });
        }
    }

    public List<SizeViewModel> getAll() {
        return this.sizes;
    }
}