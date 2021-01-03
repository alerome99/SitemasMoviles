package com.example.urclean.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class DetallesQuejaFragment extends Fragment {


    Button marcarQuejaLeida;
    Button marcarQuejaCompletada;
    private FirebaseConnection connection;
    EditText editTextTituloQueja;
    EditText editTextDescripcionQueja;
    Button botonAtrasDetallesQueja;

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
        botonAtrasDetallesQueja = v.findViewById(R.id.botonAtrasDetallesQueja);
        marcarQuejaCompletada = v.findViewById(R.id.marcarQuejaCompletada);
        editTextTituloQueja = v.findViewById(R.id.editTextTituloQueja);
        editTextDescripcionQueja = v.findViewById(R.id.editTextDescripcionQueja);
        editTextTituloQueja.setText(getArguments().getString("TITULO"));
        editTextDescripcionQueja.setText(getArguments().getString("DESCRIPCION"));

        if(getArguments().getString("ESTADO").equals("RECIBIDA")){
            marcarQuejaLeida.setVisibility(View.INVISIBLE);
        }

        botonAtrasDetallesQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new QuejasSupervisorFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        marcarQuejaLeida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getQuejaPorEmailTitulo(getArguments().getString("EMAIL"), getArguments().getString("TITULO"), correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            String id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.cambiarEstadoQueja(id, "1", new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new QuejasSupervisorFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).commit();
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

                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.cambiarEstadoQueja(id, "2", new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {

                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new QuejasSupervisorFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).commit();
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