package com.example.urclean.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Queja;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class QuejasFragment extends Fragment implements View.OnClickListener{

    private EditText editTextQueja;
    private EditText editTextTituloQueja;
    private FirebaseConnection connection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quejas, container, false);

        connection = FirebaseConnection.getInstance();

        editTextQueja = view.findViewById(R.id.editTextQueja);
        view.findViewById(R.id.botonEnviarQueja).setOnClickListener(this);
        editTextTituloQueja = view.findViewById(R.id.editTextTituloQueja);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(editTextQueja.getText().toString().trim().length() == 0){
            editTextQueja.setError("Ingrese el motivo de su queja");
        }
        if(editTextTituloQueja.getText().toString().trim().length() == 0){
            editTextTituloQueja.setError("Ingrese un título para su queja");
        }
        if ((editTextQueja.getText().toString().trim().length() != 0) && (editTextTituloQueja.getText().toString().trim().length() != 0)){
            connection.getCurrentUser(correct -> {
                if (correct) {
                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        Queja queja = new Queja(editTextQueja.getText().toString(), null, editTextTituloQueja.getText().toString());
                        connection.saveQueja(queja, new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if (correct) {
                                    editTextQueja.setText("");
                                    editTextTituloQueja.setText("");
                                    Snackbar.make(view, "Queja enviada", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(view, "Ha habido un error", Snackbar.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        String email = "";
                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                            email = (String) document.get("email");
                        }
                        connection.getQuejasPorEmail(email, correct2 -> {
                            if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                Queja queja = new Queja(editTextQueja.getText().toString(), null, editTextTituloQueja.getText().toString());
                                connection.saveQueja(queja, new FirebaseCallback() {
                                    @Override
                                    public void onResponse(boolean correct) {
                                        if (correct) {
                                            editTextQueja.setText("");
                                            editTextTituloQueja.setText("");
                                            Snackbar.make(view, "Queja enviada", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            Snackbar.make(view, "Ha habido un error", Snackbar.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            } else {
                                boolean bandera = true;
                                for (QueryDocumentSnapshot document : connection.getResponse()) {
                                    if(document.get("titulo").equals(editTextTituloQueja.getText().toString())){
                                        bandera = false;
                                    }
                                }
                                if(bandera){
                                    Queja queja = new Queja(editTextQueja.getText().toString(), null, editTextTituloQueja.getText().toString());
                                    connection.saveQueja(queja, new FirebaseCallback() {
                                        @Override
                                        public void onResponse(boolean correct) {
                                            if (correct) {
                                                editTextQueja.setText("");
                                                editTextTituloQueja.setText("");
                                                Snackbar.make(view, "Queja enviada", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Snackbar.make(view, "Ha habido un error", Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }else{
                                    editTextTituloQueja.setError("YA TIENES UNA QUEJA CON ESE TITULO");
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}