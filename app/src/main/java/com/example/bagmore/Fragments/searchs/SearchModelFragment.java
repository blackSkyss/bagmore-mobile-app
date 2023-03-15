package com.example.bagmore.Fragments.searchs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.bagmore.R;

public class SearchModelFragment extends Fragment {


    public SearchModelFragment() {
        // Required empty public constructor
    }

    public static SearchModelFragment newInstance(String param1, String param2) {
        SearchModelFragment fragment = new SearchModelFragment();
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
        return inflater.inflate(R.layout.fragment_search_model, container, false);
    }
}