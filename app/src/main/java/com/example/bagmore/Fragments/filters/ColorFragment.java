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

import com.example.bagmore.Adapters.RecyclerViewAdapters.ColorRVAdapter;
import com.example.bagmore.Helpers.TokenManager;
import com.example.bagmore.Interfaces.IClickItemColorFilter;
import com.example.bagmore.Models.data.ColorViewModel;
import com.example.bagmore.Models.json.response.JsonColor;
import com.example.bagmore.R;
import com.example.bagmore.Repository.ColorRepository;
import com.example.bagmore.SearchingScreen.FilterActivity;
import com.example.bagmore.Services.ColorService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColorFragment extends Fragment {

    //region init
    private FilterActivity filterActivity;
    private RecyclerView rcvColors;
    private ColorRVAdapter colorAdapter;
    private List<ColorViewModel> colors;

    private ColorService colorService;
    //endregion

    public ColorFragment() {
        // Required empty public constructor
    }

    public static ColorFragment newInstance(String param1, String param2) {
        ColorFragment fragment = new ColorFragment();
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
        return inflater.inflate(R.layout.fragment_color, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        colorService = ColorRepository.getColorService();

        rcvColors = view.findViewById(R.id.rcvColors);
        colorAdapter = new ColorRVAdapter(view.getContext(), new IClickItemColorFilter() {
            @Override
            public void onClickHandler(ColorViewModel model) {
                if (getActivity() instanceof FilterActivity) {
                    ((FilterActivity) getActivity()).setColors(model.getId());
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rcvColors.setLayoutManager(linearLayoutManager);

        getAllColors();

        rcvColors.setAdapter(colorAdapter);
    }

    //region call api
    private void getAllColors() {
        try {
            TokenManager tokenManager = new TokenManager(getContext());
            Call<JsonColor> result = colorService.getColors("bearer " + tokenManager.getAccessToken());
            result.enqueue(new Callback<JsonColor>() {
                @Override
                public void onResponse(Call<JsonColor> call, Response<JsonColor> response) {
                    if (response.isSuccessful()) {
                        JsonColor jsonModel = response.body();
                        List<ColorViewModel> colors = jsonModel.getData();
                        colorAdapter.setData(colors);
                    } else {
                        Toast.makeText(filterActivity, "Unauthorized", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonColor> call, Throwable t) {
                    Toast.makeText(filterActivity, "Failed to call API", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    //endregion


    public List<ColorViewModel> getColorFromAdapter() {
        this.colors = colorAdapter.getAll();
        return this.colors;
    }

    public void UpdateColors(List<ColorViewModel> colors) {
        colorAdapter.setData(colors);
        rcvColors.setAdapter(colorAdapter);
    }


}