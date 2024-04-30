package com.example.bagmore.Adapters.TabViewAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bagmore.Fragments.CompositionFragment;
import com.example.bagmore.Fragments.InformationFragment;
import com.example.bagmore.Models.data.ProductDetailViewModel;

public class ProductHomeTVAdapter extends FragmentStatePagerAdapter {
    private ProductDetailViewModel product;

    public ProductHomeTVAdapter(@NonNull FragmentManager fm, int behavior, ProductDetailViewModel product) {
        super(fm, behavior);
        this.product = product;
    }


    // init fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new CompositionFragment(product);

            default:
                return new InformationFragment(product);
        }

    }

    // amount of fragment in tab view
    @Override
    public int getCount() {
        return 2;
    }

    // set title for each tab
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Information";
                break;
            case 1:
                title = "Composition";
                break;
        }
        return title;
    }


}
