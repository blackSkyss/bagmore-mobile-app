package com.example.bagmore.Fragments.searchs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Adapters.RecyclerViewAdapters.SearchingResultRVAdapter;
import com.example.bagmore.Models.data.CategoryViewModel;
import com.example.bagmore.R;

import java.util.ArrayList;
import java.util.List;

public class SearchingResultFragment extends Fragment {
    private RecyclerView rcvSearchingResult;
    private SearchingResultRVAdapter adapter;

    private List<CategoryViewModel> categories;

    public SearchingResultFragment() {
        // Required empty public constructor
    }

    public static SearchingResultFragment newInstance(String param1, String param2) {
        SearchingResultFragment fragment = new SearchingResultFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searching_result, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rcvSearchingResult = view.findViewById(R.id.rcvSearchResults);
        adapter = new SearchingResultRVAdapter(view.getContext());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvSearchingResult.setLayoutManager(linearLayoutManager);

        adapter.setData(categories);
        rcvSearchingResult.setAdapter(adapter);
    }

    public void setCategories(List<CategoryViewModel> categories) {
        this.categories = categories;
    }

    public List<CategoryViewModel> searchProducts(String searchValue) {
        //Execute search and get result
        List<CategoryViewModel> categories = new ArrayList<>();
        categories.add(new CategoryViewModel(1, "Hand bag", 1));
        categories.add(new CategoryViewModel(2, "Cross Body Bag", 1));
        categories.add(new CategoryViewModel(3, "Satchel Bag", 1));
        categories.add(new CategoryViewModel(4, "Tote Bag", 1));
        categories.add(new CategoryViewModel(5, "Backpack Bag", 1));
        return categories;
    }
}