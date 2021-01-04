package com.example.urclean.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.AdapterGrupo;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.CodigoPostal;
import com.example.urclean.model.Grupo;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class AddGrupoFragment extends Fragment {
    //implements AdapterView.OnItemSelectedListener

    private EditText editTextTextNumeroGrupo;
    private Button buttonAddGrupo;
    private FirebaseConnection connection;
    private EditText editTextCodigoPostal;
    private Button botonMostrarListaCodigos;
    private Spinner spinnerSeleccionarCodigo;
    private ArrayList<String> codigos;

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
        spinnerSeleccionarCodigo = view.findViewById(R.id.spinnerSeleccionarCodigo);
        botonMostrarListaCodigos = view.findViewById(R.id.botonMostrarListaCodigos);
        buttonAddGrupo = view.findViewById(R.id.buttonAddGrupo);
        editTextCodigoPostal = view.findViewById(R.id.editTextCodigoPostal);
        connection = FirebaseConnection.getInstance();
        //editTextCodigoPostal.setFocusable(false);

        //Bundle bundle = getArguments();
        /*
        if(getArguments().getString("CODIGO").equals("")){
        }else{
            editTextCodigoPostal.setText(getArguments().getString("CODIGO"));
        }*/

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
        });

        buttonAddGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grupo g = new Grupo(editTextTextNumeroGrupo.getText().toString(), spinnerSeleccionarCodigo.getSelectedItem().toString());
                connection.saveGrupo(g, new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                        } else {
                            Log.e("SAVE", "Respuesta vacia");
                            Snackbar.make(v, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                        }
                        Fragment selectedFragment;
                        selectedFragment = new MenuSupervisorFragment();
                        getActivity().getSupportFragmentManager().beginTransaction().
                                replace(R.id.fragment_container, selectedFragment).commit();
                    }
                }); // fin save use
            }
        });

        botonMostrarListaCodigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaCodigoPostal();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        connection = FirebaseConnection.getInstance();

        return view;
    }
}