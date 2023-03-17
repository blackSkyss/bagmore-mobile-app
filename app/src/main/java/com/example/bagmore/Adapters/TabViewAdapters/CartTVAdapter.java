package com.example.bagmore.Adapters.TabViewAdapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.bagmore.Fragments.MyBagFragment;
import com.example.bagmore.Fragments.WishListFragment;
import com.example.bagmore.Fragments.cart.CartNothingFragment;
import com.example.bagmore.Fragments.cart.WishlistNothingFragment;
import com.example.bagmore.Models.data.CartViewModel;

import java.util.List;

public class CartTVAdapter extends FragmentStatePagerAdapter {

    private List<CartViewModel> itemCarts;
    private List<CartViewModel> itemWishlists;

    public CartTVAdapter(@NonNull FragmentManager fm, int behavior, List<CartViewModel> itemCarts, List<CartViewModel> itemWishlists) {
        super(fm, behavior);
        this.itemCarts = itemCarts;
        this.itemWishlists = itemWishlists;
    }

    // init fragment
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                if (itemWishlists != null) {
                    if (itemWishlists.size() > 0) {
                        return new WishListFragment(itemWishlists);
                    } else {
                        return new WishlistNothingFragment();
                    }
                } else {
                    return new WishlistNothingFragment();
                }
            default:
                if (itemCarts != null) {
                    if (itemCarts.size() > 0)
                        return new MyBagFragment(itemCarts);
                    else {
                        return new CartNothingFragment();
                    }
                } else {
                    return new CartNothingFragment();
                }
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
                title = "My Bag";
                break;
            case 1:
                title = "Wish List";
                break;
        }
        return title;
    }
}
