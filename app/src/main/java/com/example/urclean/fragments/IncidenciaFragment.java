package com.example.urclean.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.urclean.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncidenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncidenciaFragment extends Fragment {


    public IncidenciaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incidencia, container, false);
    }
    //dani me comes el orto
}