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


public class DetallesBarrenderoFragment extends Fragment {


    Button buttonGrupo;
    Spinner spinner;
    private FirebaseConnection connection;
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    ArrayList<String> listaGrupos;
    Button botonAtrasDetallesBarrandero;


    public DetallesBarrenderoFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_muestra_informacion_barrendero, container, false);

        Bundle bundle = getArguments();

        buttonGrupo = v.findViewById(R.id.buttonGrupo);
        botonAtrasDetallesBarrandero = v.findViewById(R.id.botonAtrasDetallesBarrandero);
        spinner = v.findViewById(R.id.spinner);
        editTextName = v.findViewById(R.id.editTextName);
        editTextPhone = v.findViewById(R.id.editTextPhone);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        listaGrupos = new ArrayList<>();

        editTextEmail.setText(getArguments().getString("EMAIL"));
        editTextPhone.setText(getArguments().getString("TELEFONO"));
        editTextName.setText(getArguments().getString("NAME"));

        botonAtrasDetallesBarrandero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaBarrenderosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        connection.getGrupos(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    int i = 0;
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        listaGrupos.add((String) document.get("numero"));
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, listaGrupos);
                    spinner.setAdapter(adaptador);
                }
            }
        });
        buttonGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //añadir una persona al grupo que este
                connection.getUsuarioPorEmail(getArguments().getString("EMAIL"), correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            String id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.asignarGrupo(id, spinner.getSelectedItem().toString(), new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new ListaBarrenderosFragment();
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

        return v;
    }
}