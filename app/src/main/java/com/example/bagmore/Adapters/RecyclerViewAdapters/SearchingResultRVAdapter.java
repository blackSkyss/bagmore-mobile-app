package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Models.data.CategoryViewModel;
import com.example.bagmore.R;

import java.util.List;

public class SearchingResultRVAdapter extends RecyclerView.Adapter<SearchingResultRVAdapter.SearchResultViewHolder> {

    private Context context;
    //List products after result (example Category)
    private List<CategoryViewModel> result;

    public SearchingResultRVAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<CategoryViewModel> categories) {
        this.result = categories;
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_results_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        CategoryViewModel category = result.get(position);
        if ((category == null)) {
            return;
        }
        holder.bindingData(category);
    }

    @Override
    public int getItemCount() {
        if ((this.result == null)) {
            return 0;
        }
        return this.result.size();
    }

    public class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public SearchResultViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtNameResult);
        }

        public void bindingData(CategoryViewModel category) {
            textView.setText(category.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // forward to homeActivity to show all of item with filter that is the name of the category
                    Toast.makeText(context, category.getName(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}