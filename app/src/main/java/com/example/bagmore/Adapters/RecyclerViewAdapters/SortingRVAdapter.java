package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemSort;
import com.example.bagmore.Models.data.SortingViewModel;
import com.example.bagmore.R;

import java.util.List;

public class SortingRVAdapter extends RecyclerView.Adapter<SortingRVAdapter.SortingViewHolder> {

    private Context context;
    private List<SortingViewModel> sortingList;

    private IClickItemSort iClickItemSort;
    private int checkedPosition = 0;

    public SortingRVAdapter(Context context, IClickItemSort iClickItemSort) {
        this.context = context;
        this.iClickItemSort = iClickItemSort;
    }

    public void setData(List<SortingViewModel> sortingList) {
        this.sortingList = sortingList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SortingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sort_item, parent, false);
        return new SortingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SortingViewHolder holder, int position) {
        SortingViewModel sorting = sortingList.get(position);
        if (sorting == null) {
            return;
        }
        holder.bindImageView(sorting);
    }

    @Override
    public int getItemCount() {
        if ((this.sortingList == null)) {
            return 0;
        }
        return this.sortingList.size();
    }

    public class SortingViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        private View rlItemSort;

        public SortingViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtSortName);
            imageView = itemView.findViewById(R.id.imgCheckSorting);
            rlItemSort = itemView.findViewById(R.id.rl_item_sort);
        }

        public void bindImageView(SortingViewModel nextSorting) {

            if (checkedPosition == -1) {
                imageView.setVisibility(View.INVISIBLE);
                nextSorting.setChecked(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    imageView.setVisibility(nextSorting.isChecked() ? View.VISIBLE : View.INVISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                    nextSorting.setChecked(false);
                }
            }
            textView.setText(nextSorting.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iClickItemSort.onClickHandler(nextSorting);
                    nextSorting.setChecked(!nextSorting.isChecked());
                    imageView.setVisibility(nextSorting.isChecked() ? View.VISIBLE : View.INVISIBLE);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                    }
                }
            });
        }
    }

    public SortingViewModel getSelected() {
        if ((checkedPosition != -1)) {
            return this.sortingList.get(checkedPosition);
        }
        return null;
    }
}
