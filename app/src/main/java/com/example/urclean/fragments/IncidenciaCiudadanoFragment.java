package com.example.urclean.fragments;

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
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;

public class IncidenciaCiudadanoFragment extends Fragment implements View.OnClickListener {
    //implements AdapterView.OnItemSelectedListener

    private FirebaseConnection connection;
    private EditText direccion, descripcion;
    private Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incidencia_ciudadano, container, false);

        spinner = (Spinner) view.findViewById(R.id.spinnerCrearIncidencia);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinnerCrearIncidencia,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        direccion = view.findViewById(R.id.editTextDireccionIncidencia);
        descripcion = view.findViewById(R.id.editTextDescripcionIncidencia);

        view.findViewById(R.id.botonEnviarIncidencia).setOnClickListener(this);

        connection = FirebaseConnection.getInstance();

        return view;
    }

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
}