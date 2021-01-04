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
    Button botonConfirmarRespuesta;

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

        botonConfirmarRespuesta = v.findViewById(R.id.botonConfirmarRespuesta);
        editTextJustificacion = v.findViewById(R.id.editTextJustificacion);
        editTextRespuesta = v.findViewById(R.id.editTextRespuesta);

        editTextRespuesta.setText(getArguments().getString("RESPUESTA"));
        editTextJustificacion.setText(getArguments().getString("RAZON"));

        botonConfirmarRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getNotificacionBarrenderosPorEmail(getArguments().getString("EMAIL"), correct -> {
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            String id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.actualizarEstadoRespuesta(id, new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new ListaNotificacionesBarrenderoFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).commit();
                                }
                            });
                        }
                    }
                });
            }
        });

        return v;
    }
}