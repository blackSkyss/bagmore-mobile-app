package com.example.bagmore.Adapters.TabViewAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.bagmore.Fragments.filters.CategoryFragment;
import com.example.bagmore.Fragments.filters.ColorFragment;
import com.example.bagmore.Fragments.filters.SizeFragment;
import com.example.bagmore.SearchingScreen.FilterActivity;

public class FilterTVAdapter extends FragmentStateAdapter {
    private CategoryFragment categoryFragment;
    private ColorFragment colorFragment;
    private SizeFragment sizeFragment;
    private FilterActivity filterActivity;

    public FilterTVAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        filterActivity = (FilterActivity) fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                categoryFragment = new CategoryFragment();
                return categoryFragment;
            case 1:
                colorFragment = new ColorFragment();
                return colorFragment;
            case 2:
                sizeFragment = new SizeFragment();
                return sizeFragment;
            default:
                categoryFragment = new CategoryFragment();
                return categoryFragment;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public CategoryFragment getCategoryFragment() {
        return categoryFragment;
    }

    public ColorFragment getColorFragment() {
        return colorFragment;
    }

    public SizeFragment getSizeFragment() {
        return sizeFragment;
    }
}
