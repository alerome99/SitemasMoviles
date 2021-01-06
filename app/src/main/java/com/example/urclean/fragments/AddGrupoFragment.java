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
import com.example.urclean.model.Grupo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AddGrupoFragment extends Fragment {

    private EditText editTextTextNumeroGrupo;
    private Button buttonAddGrupo;
    private FirebaseConnection connection;
    private EditText editTextCodigoPostal;
    private Button botonMostrarListaCodigos;
    private ArrayList<String> codigos;
    private EditText editTextError;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    public AddGrupoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_grupo, container, false);

        editTextTextNumeroGrupo = view.findViewById(R.id.editTextTextNumeroGrupo);
        codigos = new ArrayList<>();
        editTextError = view.findViewById(R.id.editTextError);
        botonMostrarListaCodigos = view.findViewById(R.id.botonMostrarListaCodigos);
        buttonAddGrupo = view.findViewById(R.id.buttonAddGrupo);
        editTextCodigoPostal = view.findViewById(R.id.editTextCodigoPostal);
        connection = FirebaseConnection.getInstance();
        editTextCodigoPostal.setFocusable(false);

        Bundle bundle = getArguments();

        if(getArguments().getString("CODIGO").equals("")){
        }else{
            editTextTextNumeroGrupo.setText(getArguments().getString("NUMERO"));
            editTextCodigoPostal.setText(getArguments().getString("CODIGO"));
        }

        /*
        connection.getCodigosPostales(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                } else {
                    int i = 0;
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        codigos.add((String) document.get("codigo"));
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, codigos);
                    spinnerSeleccionarCodigo.setAdapter(adaptador);
                }
            }
        });*/

        buttonAddGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getGrupos(correct -> {
                    if (correct) {
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                            Grupo g = new Grupo(editTextTextNumeroGrupo.getText().toString(), editTextCodigoPostal.getText().toString());
                            connection.saveGrupo(g, new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                    } else {
                                        Snackbar.make(v, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new MenuSupervisorFragment();
                                    getActivity().getSupportFragmentManager().beginTransaction().
                                            replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                }
                            }); // fin save use
                        } else {
                            boolean bandera1 = true;
                            boolean bandera2 = true;
                            String numero = "";
                            String codigo = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                numero = (String) document.get("numero");
                                codigo = (String) document.get("codigo");
                                if(numero!=null && codigo!=null){
                                    if (numero.equals(editTextTextNumeroGrupo.getText().toString())) {
                                        bandera1 = false;
                                    }
                                    if (codigo.equals(editTextCodigoPostal.getText().toString())) {
                                        bandera2 = false;
                                    }
                                }
                            }
                            if(bandera1 && bandera2){
                                Grupo g = new Grupo(editTextTextNumeroGrupo.getText().toString(), editTextCodigoPostal.getText().toString());
                                connection.saveGrupo(g, new FirebaseCallback() {
                                    @Override
                                    public void onResponse(boolean correct) {
                                        if (correct) {
                                        } else {
                                            Snackbar.make(v, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                                        }
                                        Fragment selectedFragment;
                                        selectedFragment = new MenuSupervisorFragment();
                                        getActivity().getSupportFragmentManager().beginTransaction().
                                                replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                    }
                                }); // fin save use
                            }
                            if(!bandera1){
                                editTextTextNumeroGrupo.setError("YA EXISTE UN GRUPO CON ESE NUMERO");
                            }
                            if(!bandera2){
                                editTextError.setError("ESE CODIGO POSTAL YA ESTA ASIGNADO A OTRO GRUPO");
                            }
                        }
                    }
                });
            }
        });

        botonMostrarListaCodigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaCodigoPostal();
                Bundle bundle = new Bundle();
                bundle.putString("NUMERO", editTextTextNumeroGrupo.getText().toString());
                selectedFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        connection = FirebaseConnection.getInstance();

        return view;
    }
}