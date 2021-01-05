package com.example.urclean.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.urclean.AccesoActivity;
import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;


public class MenuSupervisorFragment extends Fragment {

    private Button Logoutsupervisor;
    private FirebaseConnection connection;
    private Button buttonRegistroBarrendero;
    private Button buttonAddBarrendero;
    private Button buttonCrearGrupo;
    private Button buttonListaQuejas;
    private Button buttonListaDesperfectos;

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
        buttonListaQuejas = v.findViewById(R.id.buttonListaQuejas);
        buttonListaDesperfectos = v.findViewById(R.id.buttonListaDesperfectos);

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

        buttonListaQuejas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent( MenuSupervisorFragment.this.getActivity(), quejasSupervisor.class));
                Fragment selectedFragment;
                selectedFragment = new QuejasSupervisorFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        buttonListaDesperfectos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startActivity(new Intent( MenuSupervisorFragment.this.getActivity(), quejasSupervisor.class));
                Fragment selectedFragment;
                selectedFragment = new DesperfectosSupervisorFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        buttonAddBarrendero.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment selectedFragment;
                selectedFragment = new ListaBarrenderosFragment();
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        buttonCrearGrupo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String bandera = "";
                Bundle bundle = new Bundle();
                bundle.putString("CODIGO", bandera);
                Fragment selectedFragment;
                selectedFragment = new AddGrupoFragment();
                selectedFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        return v;
    }


}