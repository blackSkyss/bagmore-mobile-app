package com.example.bagmore.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.bagmore.R;


public class CompositionFragment extends Fragment {

    public CompositionFragment() {

    }


    public static CompositionFragment newInstance(String param1, String param2) {
        CompositionFragment fragment = new CompositionFragment();
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
}