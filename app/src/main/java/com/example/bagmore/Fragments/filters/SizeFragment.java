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

import com.example.bagmore.Adapters.RecyclerViewAdapters.SizeRVAdapter;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemSizeFilter;
import com.example.bagmore.Models.data.SizeViewModel;
import com.example.bagmore.Models.json.response.JsonSize;
import com.example.bagmore.R;
import com.example.bagmore.Repository.SizeRepository;
import com.example.bagmore.SearchingScreen.FilterActivity;
import com.example.bagmore.Services.SizeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SizeFragment extends Fragment {

    //region init
    private FilterActivity filterActivity;
    private RecyclerView rcvSize;
    private SizeRVAdapter sizeAdapter;
    private List<SizeViewModel> sizes;

    private SizeService sizeService;
    //endregion


    public SizeFragment() {
        // Required empty public constructor
    }

    public static SizeFragment newInstance(String param1, String param2) {
        SizeFragment fragment = new SizeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        filterActivity = (FilterActivity) getActivity();
        return inflater.inflate(R.layout.fragment_size, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sizeService = SizeRepository.getSizeService();

        rcvSize = view.findViewById(R.id.rcvSizes);
        sizeAdapter = new SizeRVAdapter(view.getContext(), new IClickItemSizeFilter() {
            @Override
            public void onClickHandler(SizeViewModel model) {
                if (getActivity() instanceof FilterActivity) {
                    ((FilterActivity) getActivity()).setSizes(model.getId());
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvSize.setLayoutManager(linearLayoutManager);

        getAllSizes();

        rcvSize.setAdapter(sizeAdapter);
    }

    //region call api
    private void getAllSizes() {
        try {
            TokenManager tokenManager = new TokenManager(getContext());
            Call<JsonSize> result = sizeService.getSizes("bearer " + tokenManager.getAccessToken());
            result.enqueue(new Callback<JsonSize>() {
                @Override
                public void onResponse(Call<JsonSize> call, Response<JsonSize> response) {
                    if (response.isSuccessful()) {
                        JsonSize jsonModel = response.body();
                        List<SizeViewModel> sizes = jsonModel.getData();
                        sizeAdapter.setData(sizes);
                    } else {
                        Toast.makeText(filterActivity, "Unauthorized", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonSize> call, Throwable t) {
                    Toast.makeText(filterActivity, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion

    public List<SizeViewModel> getSizeFromAdapter() {
        this.sizes = sizeAdapter.getAll();
        return this.sizes;
    }

    public void updateSizes(List<SizeViewModel> sizes) {
        sizeAdapter.setData(sizes);
        rcvSize.setAdapter(sizeAdapter);
    }

}