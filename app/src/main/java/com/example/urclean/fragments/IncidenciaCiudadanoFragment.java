package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;

public class IncidenciaCiudadanoFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incidencia_ciudadano, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerCrearIncidencia);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinnerCrearIncidencia,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();
        switch (item){
            case "Limpieza":
                // Asignar a tareas
                break;
            case "Desperfecto":
                // Asignar a desperfecto
                break;
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}