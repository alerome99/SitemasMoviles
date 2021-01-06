package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Respuesta;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.w3c.dom.Text;


public class DetallesNotificacionFragment extends Fragment {


    Button buttonAceptarCambio;
    Button buttonRechazarCambio;
    private FirebaseConnection connection;
    TextView textViewRazon;
    EditText editTextJustificacion;
    TextView textViewGrupoActual;
    TextView textViewGrupoDestino;
    private String email;
    private String idBarrendero;
    private String id;

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

        textViewGrupoDestino = v.findViewById(R.id.textViewGrupoDestino);
        textViewGrupoActual = v.findViewById(R.id.textViewGrupoActual);
        editTextJustificacion = v.findViewById(R.id.editTextJustificacion);
        textViewRazon = v.findViewById(R.id.textViewRazon);
        buttonRechazarCambio = v.findViewById(R.id.buttonRechazarCambio);
        buttonAceptarCambio = v.findViewById(R.id.buttonAceptarCambio);

        textViewGrupoDestino.setText(getArguments().getString("GRUPODESTINO"));
        textViewGrupoActual.setText(getArguments().getString("GRUPOACTUAL"));
        textViewRazon.setText(getArguments().getString("RAZON"));

        buttonAceptarCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getNotificacionSupervisorPorEmail(getArguments().getString("EMAIL"), correct->{
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.getCurrentUser(correct2 -> {
                                if (correct2) {
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                    } else {
                                        email = "";
                                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                                            email = (String) document.get("email");
                                        }
                                        connection.getUsuarioPorEmail(getArguments().getString("EMAIL"), correct3 -> {
                                            if (correct3) {
                                                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                                } else {
                                                    idBarrendero = "";
                                                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                                                        idBarrendero = (String) document.getId();
                                                    }
                                                    Respuesta r = new Respuesta("Concedido", editTextJustificacion.getText().toString(), email,
                                                            getArguments().getString("EMAIL"));
                                                    connection.crearRespuesta(r, new FirebaseCallback() {
                                                        @Override
                                                        public void onResponse(boolean correct) {
                                                            if (correct) {
                                                                Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                            } else {
                                                                Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                    connection.asignarGrupo(idBarrendero, textViewGrupoDestino.getText().toString(), new FirebaseCallback() {
                                                        @Override
                                                        public void onResponse(boolean correct) {
                                                            if (correct) {
                                                                Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                            } else {
                                                                Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                    connection.actualizarEstadoNotificacionBarrendero(id, "CONCEDIDO", new FirebaseCallback() {
                                                        @Override
                                                        public void onResponse(boolean correct) {
                                                            if (correct) {
                                                                //Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                            } else {
                                                                //Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                                            }
                                                            Fragment selectedFragment;
                                                            selectedFragment = new ListaNotificacionesSupervisorFragment();
                                                            getActivity().getSupportFragmentManager().beginTransaction().
                                                                    replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });

        buttonRechazarCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getNotificacionSupervisorPorEmail(getArguments().getString("EMAIL"), correct->{
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.getCurrentUser(correct2 -> {
                                if (correct2) {
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                    } else {
                                        String email = "";
                                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                                            email = (String) document.get("email");
                                        }
                                        Respuesta r = new Respuesta("No Concedido", editTextJustificacion.getText().toString(), email,
                                                getArguments().getString("EMAIL"));
                                        connection.crearRespuesta(r, new FirebaseCallback() {
                                            @Override
                                            public void onResponse(boolean correct) {
                                                if (correct) {
                                                    Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                } else {
                                                    Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                        connection.actualizarEstadoNotificacionBarrendero(id, "NOCONCEDIDO", new FirebaseCallback() {
                                            @Override
                                            public void onResponse(boolean correct) {
                                                if (correct) {
                                                    //Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                } else {
                                                    //Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
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
                    }
                });
            }
        });

        return v;
    }
}