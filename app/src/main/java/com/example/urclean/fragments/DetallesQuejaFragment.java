package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.NotificacionCiudadano;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;


public class DetallesQuejaFragment extends Fragment {


    Button marcarQuejaLeida;
    Button marcarQuejaCompletada;
    private FirebaseConnection connection;
    TextView editTextTituloQueja;
    TextView editTextDescripcionQueja;

    public DetallesQuejaFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_detalles_queja, container, false);

        Bundle bundle = getArguments();

        marcarQuejaLeida = v.findViewById(R.id.marcarQuejaLeida);
        marcarQuejaCompletada = v.findViewById(R.id.marcarQuejaCompletada);
        editTextTituloQueja = v.findViewById(R.id.editTextTituloQueja);
        editTextDescripcionQueja = v.findViewById(R.id.editTextDescripcionQueja);
        editTextTituloQueja.setText(getArguments().getString("TITULO"));
        editTextDescripcionQueja.setText(getArguments().getString("DESCRIPCION"));

        if(getArguments().getString("ESTADO").equals("RECIBIDA")){
            marcarQuejaLeida.setVisibility(View.INVISIBLE);
        }

        marcarQuejaLeida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getQuejaPorEmailTitulo(getArguments().getString("EMAIL"), getArguments().getString("TITULO"), correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            String id = "";
                            String descripcion = "";
                            String email = "";
                            String estado = "";
                            String titulo = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                                descripcion = document.getString("descripcion");
                                email = document.getString("email");
                                estado = document.getString("estado");
                                titulo = document.getString("titulo");
                            }
                            NotificacionCiudadano n = new NotificacionCiudadano(descripcion,email,estado,titulo);
                            connection.cambiarEstadoQueja(id, "1", new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    n.setEstado("RECIBIDA");
                                    connection.crearNotificacionCiudadano(n, new FirebaseCallback() {
                                        @Override
                                        public void onResponse(boolean correct) {
                                        }
                                    });
                                    Fragment selectedFragment;
                                    selectedFragment = new QuejasSupervisorFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                }
                            });
                        }
                    }else{
                    }
                });
            }
        });

        marcarQuejaCompletada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getQuejaPorEmailTitulo(getArguments().getString("EMAIL"), getArguments().getString("TITULO"), correct -> {
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            String id = "";
                            String descripcion = "";
                            String email = "";
                            String estado = "";
                            String titulo = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                                descripcion = document.getString("descripcion");
                                email = document.getString("email");
                                estado = document.getString("estado");
                                titulo = document.getString("titulo");
                            }
                            NotificacionCiudadano n = new NotificacionCiudadano(descripcion,email,estado,titulo);
                            connection.cambiarEstadoQueja(id, "2", new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    n.setEstado("SOLUCIONADA");
                                    connection.crearNotificacionCiudadano(n, new FirebaseCallback() {
                                        @Override
                                        public void onResponse(boolean correct) {}});
                                    Fragment selectedFragment;
                                    selectedFragment = new QuejasSupervisorFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                }
                            });
                        }
                    } else {
                    }
                });
            }
        });

        return v;
    }
}