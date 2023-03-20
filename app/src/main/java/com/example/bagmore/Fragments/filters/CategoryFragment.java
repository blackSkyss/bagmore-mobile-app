package com.example.bagmore.Fragments.filters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagmore.Adapters.RecyclerViewAdapters.CategoryRVAdapter;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemCategoryFilter;
import com.example.bagmore.Models.data.CategoryViewModel;
import com.example.bagmore.Models.json.response.JsonCategory;
import com.example.bagmore.R;
import com.example.bagmore.Repository.CategoryRepository;
import com.example.bagmore.SearchingScreen.FilterActivity;
import com.example.bagmore.Services.CategoryService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    //region init
    private FilterActivity filterActivity;

    private RecyclerView rcvCategories;
    private CategoryRVAdapter categoryAdapter;
    private List<CategoryViewModel> categories;

    private CategoryService categoryService;
    //endregion

    public CategoryFragment() {
        // Required empty public constructor
        categoryAdapter = new CategoryRVAdapter(this.getContext(), new IClickItemCategoryFilter() {
            @Override
            public void onClickHandler(CategoryViewModel model) {
                if (getActivity() instanceof FilterActivity) {
                    ((FilterActivity) getActivity()).setCategories(model.getId());
                }
            }
        });
        // categoryAdapter.SetData(getCategories());
    }

    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
        filterActivity = (FilterActivity) getActivity();
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryService = CategoryRepository.getCategoryService();

        rcvCategories = view.findViewById(R.id.rcvCategories);
        categoryAdapter = new CategoryRVAdapter(view.getContext(), new IClickItemCategoryFilter() {
            @Override
            public void onClickHandler(CategoryViewModel model) {
                if (getActivity() instanceof FilterActivity) {
                    ((FilterActivity) getActivity()).setCategories(model.getId());
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvCategories.setLayoutManager(linearLayoutManager);

        getAllCategories();

        rcvCategories.setAdapter(categoryAdapter);
    }

    //region call api
    private void getAllCategories() {
        try {
            TokenManager tokenManager = new TokenManager(getContext());
            Call<JsonCategory> result = categoryService.getCategories("bearer " + tokenManager.getAccessToken());
            result.enqueue(new Callback<JsonCategory>() {
                @Override
                public void onResponse(Call<JsonCategory> call, Response<JsonCategory> response) {
                    if (response.isSuccessful()) {
                        JsonCategory jsonModel = response.body();
                        List<CategoryViewModel> categories = jsonModel.getData();
                        categoryAdapter.SetData(categories);
                    } else {
                        Toast.makeText(filterActivity, "Unauthorized", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonCategory> call, Throwable t) {
                    Toast.makeText(filterActivity, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    public List<CategoryViewModel> getCategoriesFromAdapter() {
        this.categories = categoryAdapter.getAll();
        return this.categories;
    }

    public void UpdateCategories(List<CategoryViewModel> categories) {
        categoryAdapter.SetData(categories);
        rcvCategories.setAdapter(categoryAdapter);
    }
}