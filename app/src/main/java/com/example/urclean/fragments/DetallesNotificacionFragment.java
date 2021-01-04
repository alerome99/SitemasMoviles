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


public class DetallesNotificacionFragment extends Fragment {


    Button buttonAceptarCambio;
    Button buttonRechazarCambio;
    private FirebaseConnection connection;
    EditText editTextRazon;
    EditText editTextJustificacion;
    EditText editTextGrupoActual;
    EditText editTextGrupoDestino;
    Button botonAtrasRespuestaCambio;

    public DetallesNotificacionFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_respuesta_cambio_grupo, container, false);

        Bundle bundle = getArguments();

        editTextGrupoDestino = v.findViewById(R.id.editTextGrupoDestino);
        botonAtrasRespuestaCambio = v.findViewById(R.id.botonAtrasRespuestaCambio);
        editTextGrupoActual = v.findViewById(R.id.editTextGrupoActual);
        editTextJustificacion = v.findViewById(R.id.editTextJustificacion);
        editTextRazon = v.findViewById(R.id.editTextRazon);
        buttonRechazarCambio = v.findViewById(R.id.buttonRechazarCambio);
        buttonAceptarCambio = v.findViewById(R.id.buttonAceptarCambio);

        editTextGrupoDestino.setText(getArguments().getString("GRUPODESTINO"));
        editTextGrupoActual.setText(getArguments().getString("GRUPOACTUAL"));
        editTextRazon.setText(getArguments().getString("RAZON"));

        botonAtrasRespuestaCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaNotificacionesSupervisorFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        buttonAceptarCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getCurrentUser(correct2 -> {
                    if (correct2) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            String email = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                email = (String) document.get("email");
                            }
                            Respuesta r = new Respuesta("Concedido", editTextJustificacion.getText().toString(), email);
                            connection.crearRespuesta(r, new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new ListaNotificacionesSupervisorFragment();
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