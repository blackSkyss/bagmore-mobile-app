package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemCategoryFilter;
import com.example.bagmore.Interfaces.IClickItemColorFilter;
import com.example.bagmore.Models.data.ColorViewModel;
import com.example.bagmore.R;

import java.util.List;

public class ColorRVAdapter extends RecyclerView.Adapter<ColorRVAdapter.ColorViewHolder> {
    private List<ColorViewModel> colors;
    private Context context;

    private static IClickItemColorFilter iClickItemColorFilter;


    public ColorRVAdapter(Context context, IClickItemColorFilter iClickItemColorFilter) {
        this.context = context;
        this.iClickItemColorFilter = iClickItemColorFilter;
    }

    public void setData(List<ColorViewModel> colors) {
        this.colors = colors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filter_item, parent, false);
        return new ColorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
        ColorViewModel color = colors.get(position);
        if (color == null) {
            return;
        }
        holder.bindImageView(color);
    }

    @Override
    public int getItemCount() {
        if (colors == null) {
            return 0;
        }
        return colors.size();
    }

    public static class ColorViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private ImageView imgColor;

        public ColorViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtNameItem);
            imageView = itemView.findViewById(R.id.imgCheckedItem);
            imgColor = itemView.findViewById(R.id.codeColor);
        }

        public void bindImageView(ColorViewModel color) {
            imageView.setVisibility(color.isChecked() ? View.VISIBLE : View.INVISIBLE);
            textView.setText(color.getName());
            imgColor.setBackgroundColor(Color.parseColor(color.getCodeColor()));
            itemView.setOnClickListener(v -> {
                iClickItemColorFilter.onClickHandler(color);
                color.setChecked(!color.isChecked());
                imageView.setVisibility(color.isChecked() ? View.VISIBLE : View.INVISIBLE);
            });
        }


    }

    public List<ColorViewModel> getAll() {
        return this.colors;
    }
}