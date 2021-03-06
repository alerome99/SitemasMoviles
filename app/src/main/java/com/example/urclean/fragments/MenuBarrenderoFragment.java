package com.example.urclean.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.urclean.AccesoActivity;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;


public class MenuBarrenderoFragment extends Fragment {

    private Button buttonLogout,botonHistorial,SolicitarCambioGrupo,botonFichar;
    private FirebaseConnection connection;

    public MenuBarrenderoFragment() {
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu_barrendero, container, false);
        buttonLogout = v.findViewById(R.id.Logoutbarrendero);
        botonHistorial = v.findViewById(R.id.BotonHistorialBarrendero);
        SolicitarCambioGrupo = v.findViewById(R.id.SolicitarCambioGrupo);
        botonFichar = v.findViewById(R.id.ButtonFichar);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connection.logout(new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {

                        if(correct){
                            startActivity(new Intent( MenuBarrenderoFragment.this.getActivity(), AccesoActivity.class));
                        }
                    }
                });
            }
        });

        botonHistorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new HistorialTareasFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }



        });

        SolicitarCambioGrupo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new SolicitarCambioGrupoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        botonFichar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new InformacionBarrenderoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }



        });

        return v;
    }


}