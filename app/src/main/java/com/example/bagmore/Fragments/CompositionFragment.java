package com.example.bagmore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bagmore.Models.data.ProductDetailViewModel;
import com.example.bagmore.R;


public class CompositionFragment extends Fragment {

    //region init
    TextView tvComposition;
    private ProductDetailViewModel product;
    //endregion

    public CompositionFragment(ProductDetailViewModel product) {
        this.product = product;
    }


    public static CompositionFragment newInstance(String param1, String param2, ProductDetailViewModel product) {
        CompositionFragment fragment = new CompositionFragment(product);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_composition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvComposition = view.findViewById(R.id.tv_composition_product);
        tvComposition.setText(android.text.Html.fromHtml(product.getComposition()));
    }
}