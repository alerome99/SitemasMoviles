package com.example.urclean.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;

public class AddGrupoFragment extends Fragment {
    //implements AdapterView.OnItemSelectedListener

    private EditText editTextTextNumeroGrupo;
    private Button buttonAddGrupo;
    private FirebaseConnection connection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_grupo, container, false);

        editTextTextNumeroGrupo = view.findViewById(R.id.editTextTextNumeroGrupo);
        //Añadir grupo
        buttonAddGrupo = view.findViewById(R.id.buttonAddGrupo);
        connection = FirebaseConnection.getInstance();

        view.findViewById(R.id.buttonAddGrupo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.saveGrupo(editTextTextNumeroGrupo.getText().toString(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                        } else {
                            Log.e("SAVE", "Respuesta vacia");
                            Snackbar.make(v, "Error al almacenar los datos", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }); // fin save use
            }
        });

        connection = FirebaseConnection.getInstance();

        return view;
    }
}