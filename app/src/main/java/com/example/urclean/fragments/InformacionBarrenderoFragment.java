package com.example.urclean.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.ktx.Firebase;


public class InformacionBarrenderoFragment extends Fragment {

    private FirebaseConnection connection;
    private TextView mostrarGrupo;

    public InformacionBarrenderoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_informacion_barrendero, container, false);
        mostrarGrupo = view.findViewById(R.id.textViewMostrarGrupo);

        connection.getCurrentUser(correct -> {
            if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                Log.e("Grupo", "no tiene grupo");
            }else{
                String grupo = "";
                for (QueryDocumentSnapshot document : connection.getResponse()) {
                    grupo = (String) document.get("Grupo");
                }
                mostrarGrupo.setText(grupo);
            }
        });

        return view;
    }
}