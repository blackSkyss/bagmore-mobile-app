package com.example.bagmore.Adapters.RecyclerViewAdapters;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Interfaces.IClickItemProductListener;
import com.example.bagmore.Models.data.ProductViewModel;
import com.example.bagmore.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ProductHomeRVAdapter extends RecyclerView.Adapter<ProductHomeRVAdapter.MyViewHolder> {

    private List<ProductViewModel> products;

    private List<ProductViewModel> wishlist = new ArrayList<>();
    private IClickItemProductListener iClickItemProductListener;

    public ProductHomeRVAdapter(IClickItemProductListener listener) {
        this.iClickItemProductListener = listener;
    }

    public void setData(List<ProductViewModel> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public void cleanData() {
        products.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //choose item of view holder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final ProductViewModel product = products.get(position);
        if (product == null) {
            return;
        }

        byte[] bytes = android.util.Base64.decode(products.get(position).getSource(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        holder.image.setImageBitmap(bitmap);

        String price = "$" + product.getMinPrice() + " - " + "$" + product.getMaxPrice();
        holder.title.setText(price);
        holder.description.setText(android.text.Html.fromHtml(product.getDescription()));

        // handler click on image
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.OnClickImageView(product);

            }
        });

        // handler click on material button
        holder.btFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (wishlist.contains(products.get(position))) {
                    // remove to wish list
                    wishlist.remove(products.get(position));
                    iClickItemProductListener.OnClickRemoveFavoriteButton(product);
                    holder.btFavorite.setIconResource(R.drawable.ic_favorite_border_30);
                    holder.btFavorite.setIconTintResource(R.color.black);
                } else {
                    // add to wish list
                    wishlist.add(products.get(position));
                    iClickItemProductListener.OnClickAddFavoriteButton(product);
                    holder.btFavorite.setIconResource(R.drawable.ic_favorite_fill_30);
                    holder.btFavorite.setIconTintResource(R.color.favorite);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //amount of item
        if (products != null) {
            return products.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        //define component of view holder
        ImageView image;
        TextView title, description;
        MaterialButton btFavorite;


        // connect with ui component
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image__view);
            btFavorite = itemView.findViewById(R.id.btn_favorite);
            title = itemView.findViewById(R.id.tv_title);
            description = itemView.findViewById(R.id.tv_desciption);
        }
    }
}
