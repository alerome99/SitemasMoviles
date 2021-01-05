package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class DetallesUsuarioFragment extends Fragment {


    Button buttonRegistrarBarrendero;
    Spinner spinner;
    private FirebaseConnection connection;
    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;
    ArrayList<String> listaGrupos;

    public DetallesUsuarioFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_muestra_informacion_usuario, container, false);

        Bundle bundle = getArguments();

        buttonRegistrarBarrendero = v.findViewById(R.id.buttonRegistrarBarrendero);
        spinner = v.findViewById(R.id.spinner);
        editTextName = v.findViewById(R.id.editTextName);
        editTextPhone = v.findViewById(R.id.editTextPhone);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        listaGrupos = new ArrayList<>();

        editTextEmail.setText(getArguments().getString("EMAIL"));
        editTextPhone.setText(getArguments().getString("TELEFONO"));
        editTextName.setText(getArguments().getString("NAME"));

        buttonRegistrarBarrendero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modificar el campo tipo del usuario a barrendero
                connection.getUsuarioPorEmail(getArguments().getString("EMAIL"), correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            String id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.convertirBarrendero(id, new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                                    }
                                    Fragment selectedFragment;
                                    selectedFragment = new ListaUsuariosFragment();
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

        return v;
    }
}