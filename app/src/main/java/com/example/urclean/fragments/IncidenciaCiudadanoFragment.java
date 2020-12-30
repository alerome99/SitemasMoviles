package com.example.urclean.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.direccionMapsActivity;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;

import java.util.Locale;

public class IncidenciaCiudadanoFragment extends Fragment {
    //implements AdapterView.OnItemSelectedListener

    private FirebaseConnection connection;
    private EditText direccion, descripcion;
    private Spinner spinner;
    private String lat, lng;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incidencia_ciudadano, container, false);

        connection = FirebaseConnection.getInstance();

        spinner = (Spinner) view.findViewById(R.id.spinnerCrearIncidencia);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinnerCrearIncidencia,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        direccion = view.findViewById(R.id.editTextDireccionIncidencia);
        descripcion = view.findViewById(R.id.editTextDescripcionIncidencia);

        if(getArguments()!=null){
            lat = String.format(Locale.getDefault(),"%1$.4f" , getArguments().getDouble("lat"));
            lng = String.format(Locale.getDefault(),"%1$.4f" , getArguments().getDouble("lng"));
            direccion.setText(lat+" , "+lng);
        }

        view.findViewById(R.id.botonEnviarIncidencia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = spinner.getSelectedItem().toString();
                Log.d("onTouch", item);
                switch (item){
                    case "Limpieza":
                        break;
                    case "Desperfecto":
                        connection.saveDesperfecto(direccion.getText().toString(), descripcion.getText().toString(), new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if(correct){

                                }else{

                                }
                            }
                        });
                        break;
                }
            }
        });

        view.findViewById(R.id.botonMaps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), direccionMapsActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}