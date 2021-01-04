package com.example.urclean.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Respuesta;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class DetallesRespuestaFragment extends Fragment {


    private FirebaseConnection connection;
    EditText editTextRespuesta;
    EditText editTextJustificacion;
    Button botonAtrasRespuestaCambio;

    public DetallesRespuestaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_detalles_respuesta_cambio_grupo, container, false);

        Bundle bundle = getArguments();

        botonAtrasRespuestaCambio = v.findViewById(R.id.botonAtrasRespuestaCambio);
        editTextJustificacion = v.findViewById(R.id.editTextJustificacion);
        editTextRespuesta = v.findViewById(R.id.editTextRespuesta);

        editTextRespuesta.setText(getArguments().getString("RESPUESTA"));
        editTextJustificacion.setText(getArguments().getString("RAZON"));

        botonAtrasRespuestaCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaNotificacionesSupervisorFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        return v;
    }
}