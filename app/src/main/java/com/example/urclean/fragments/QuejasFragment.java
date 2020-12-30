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

public class QuejasFragment extends Fragment implements View.OnClickListener{

    private EditText editTextQueja;
    private FirebaseConnection connection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quejas, container, false);

        connection = FirebaseConnection.getInstance();

        editTextQueja = view.findViewById(R.id.editTextQueja);
        view.findViewById(R.id.botonEnviarQueja).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        connection.saveQueja(editTextQueja.getText().toString(), new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {
                if(correct){
                    editTextQueja.setText("");
                }else{

                }
            }
        });
    }
}