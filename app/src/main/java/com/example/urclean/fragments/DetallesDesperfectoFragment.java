package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class DetallesDesperfectoFragment extends Fragment {

    Button marcarDesperfectoLeida;
    Button marcarDesperfectoCompletada;
    private FirebaseConnection connection;
    EditText editTextTituloDesperfecto;
    EditText editTextDescripcionDesperfecto;
    EditText editTextDireccionDesperfecto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_detalles_desperfecto, container, false);

        Bundle bundle = getArguments();

        marcarDesperfectoLeida = v.findViewById(R.id.marcarDesperfectoLeida);
        marcarDesperfectoCompletada = v.findViewById(R.id.marcarDesperfectoCompletada);
        editTextTituloDesperfecto = v.findViewById(R.id.editTextTituloDesperfecto);
        editTextDescripcionDesperfecto = v.findViewById(R.id.editTextDescripcionDesperfecto);
        editTextDireccionDesperfecto = v.findViewById(R.id.editTextDireccionDesperfecto);
        editTextTituloDesperfecto.setText(getArguments().getString("TITULO"));
        editTextDescripcionDesperfecto.setText(getArguments().getString("DESCRIPCION"));
        editTextDireccionDesperfecto.setText(getArguments().getString("DIRECCION"));

        if(getArguments().getString("ESTADO").equals("RECIBIDA")){
            marcarDesperfectoLeida.setVisibility(View.INVISIBLE);
        }

        marcarDesperfectoLeida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getDesperfectoPorEmailTitulo(getArguments().getString("EMAIL"), getArguments().getString("TITULO"), correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            String id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.cambiarEstadoDesperfecto(id, "1", new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new DesperfectosSupervisorFragment();
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

        marcarDesperfectoCompletada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getDesperfectoPorEmailTitulo(getArguments().getString("EMAIL"), getArguments().getString("TITULO"), correct -> {
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        } else {
                            String id = "";

                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.cambiarEstadoDesperfecto(id, "2", new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {

                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new DesperfectosSupervisorFragment();
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
