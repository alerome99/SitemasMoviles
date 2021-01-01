package com.example.urclean.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.urclean.AccesoActivity;
import com.example.urclean.MenuSupervisorActivity;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.menuBarrendero;


public class MenuSupervisorFragment extends Fragment {

    private Button Logoutsupervisor;
    private FirebaseConnection connection;
    private Button buttonRegistroBarrendero;
    private Button buttonAddBarrendero;
    private Button buttonCrearGrupo;

    public MenuSupervisorFragment() {
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
        View v = inflater.inflate(R.layout.fragment_menu_supervisor, container, false);
        Logoutsupervisor = v.findViewById(R.id.Logoutsupervisor);
        buttonRegistroBarrendero = v.findViewById(R.id.buttonRegistroBarrendero);
        buttonAddBarrendero = v.findViewById(R.id.buttonAddBarrendero);
        buttonCrearGrupo = v.findViewById(R.id.buttonCrearGrupo);


        Logoutsupervisor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connection.logout(new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {

                        if(correct){
                            startActivity(new Intent( MenuSupervisorFragment.this.getActivity(), AccesoActivity.class));
                        }
                    }
                });
            }
        });

        buttonRegistroBarrendero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaUsuariosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        buttonAddBarrendero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaBarrenderosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        buttonCrearGrupo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new AddGrupoFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).commit();
            }
        });

        return v;
    }


}