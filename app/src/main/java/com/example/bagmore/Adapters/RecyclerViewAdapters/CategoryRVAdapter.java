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

import com.example.bagmore.Interfaces.IClickItemCategoryFilter;
import com.example.bagmore.Models.data.CategoryViewModel;
import com.example.bagmore.R;

import java.util.List;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.CategoryViewHolder> {
    private Context context;
    private List<CategoryViewModel> categories;

    private static IClickItemCategoryFilter iClickItemCategoryFilter;

    public CategoryRVAdapter(Context context, IClickItemCategoryFilter iClickItemCategoryFilter) {
        this.context = context;
        this.iClickItemCategoryFilter = iClickItemCategoryFilter;
    }

    public void SetData(List<CategoryViewModel> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_filter_item, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryViewModel category = categories.get(position);
        if (category == null) {
            return;
        }
        holder.bindImageView(category);
    }

    @Override
    public int getItemCount() {
        if (categories == null) {
            return 0;
        }
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private CardView cartColor;
        List<CategoryViewModel> selectedCategories;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtNameItem);
            imageView = itemView.findViewById(R.id.imgCheckedItem);
            cartColor = itemView.findViewById(R.id.cartColor);
        }

        public void bindImageView(CategoryViewModel category) {
            imageView.setVisibility(category.isChecked() ? View.VISIBLE : View.INVISIBLE);
            textView.setText(category.getName());
            cartColor.setVisibility(View.INVISIBLE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemCategoryFilter.onClickHandler(category);
                    category.setChecked(!category.isChecked());
                    imageView.setVisibility(category.isChecked() ? View.VISIBLE : View.INVISIBLE);
                }
            });
        }
    }

    public List<CategoryViewModel> getAll() {
        return categories;
    }
}
