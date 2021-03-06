package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Notificacion;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class SolicitarCambioGrupoFragment extends Fragment {


    Spinner spinner;
    private FirebaseConnection connection;
    TextView editTextGrupoActual;
    EditText editTextRazon;
    ArrayList<String> listaGrupos;
    Button buttonSolicitarCambio;
    private String email;
    TextView textViewError;

    public SolicitarCambioGrupoFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_solicitud_cambio_grupo, container, false);

        buttonSolicitarCambio = v.findViewById(R.id.buttonSolicitarCambio);
        textViewError = v.findViewById(R.id.textViewError);
        spinner = v.findViewById(R.id.spinner);
        editTextGrupoActual = v.findViewById(R.id.editTextGrupoActual);
        editTextRazon = v.findViewById(R.id.editTextRazon);
        listaGrupos = new ArrayList<>();


        connection.getGrupos(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                } else {
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        listaGrupos.add((String) document.get("numero"));
                    }
                    connection.getCurrentUser(correct2 -> {
                        if (correct2){
                            if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                            }else{
                                String grupo = "";
                                for (QueryDocumentSnapshot document : connection.getResponse()) {
                                    grupo = (String) document.get("Grupo");
                                }
                                editTextGrupoActual.setText(grupo);
                                for(int p = 0; p<listaGrupos.size(); p++){
                                    if(listaGrupos.get(p).equals(grupo)){
                                        listaGrupos.remove(p);
                                    }
                                }
                                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(getActivity(),
                                        android.R.layout.simple_spinner_dropdown_item, listaGrupos);
                                spinner.setAdapter(adaptador);
                            }
                        }else{
                        }
                    });
                }
            }
        });


        buttonSolicitarCambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //añadir una persona al grupo que este
                connection.getCurrentUser(correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            email = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                email = (String) document.get("email");
                            }
                            connection.getNotificacionesSupervisor(correct2 ->{
                                if (correct2) {
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                        Notificacion n = new Notificacion(spinner.getSelectedItem().toString(), editTextGrupoActual.getText().toString(),
                                                editTextRazon.getText().toString(), email);
                                        connection.crearNotificacion(n, new FirebaseCallback() {
                                            @Override
                                            public void onResponse(boolean correct) {
                                                if (correct) {
                                                    Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                } else {
                                                    Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                                }
                                                Fragment selectedFragment;
                                                selectedFragment = new MenuBarrenderoFragment();
                                                getActivity().getSupportFragmentManager().beginTransaction().
                                                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                            }
                                        });
                                    } else {
                                        Boolean bandera = true;
                                        String comprobacion = "";
                                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                                            comprobacion = (String) document.get("email");
                                            if(email.equals(comprobacion)){
                                                bandera = false;
                                            }
                                        }
                                        if(bandera){
                                            Notificacion n = new Notificacion(spinner.getSelectedItem().toString(), editTextGrupoActual.getText().toString(),
                                                    editTextRazon.getText().toString(), email);
                                            connection.crearNotificacion(n, new FirebaseCallback() {
                                                @Override
                                                public void onResponse(boolean correct) {
                                                    if (correct) {
                                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                                    } else {
                                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                                    }
                                                    Fragment selectedFragment;
                                                    selectedFragment = new MenuBarrenderoFragment();
                                                    getActivity().getSupportFragmentManager().beginTransaction().
                                                            replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                                                }
                                            });
                                        }else{
                                            editTextRazon.setError("YA TIENES UNA SOLICITUD EN PROCESO");
                                        }
                                    }
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