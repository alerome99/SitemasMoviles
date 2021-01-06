package com.example.urclean.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.NotificacionCiudadano;
import com.example.urclean.model.Tarea;
import com.google.android.material.snackbar.Snackbar;


public class TareaConcretaFragment extends Fragment {


    private Button tratarTarea;
    private TextView descripcion,calle,distrito,nombre;
    private Tarea tarea;
    private FirebaseConnection connection;


    public TareaConcretaFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = FirebaseConnection.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_tarea_concreta, container, false);

        Bundle bundle = getArguments();
         tarea = (Tarea) bundle.getSerializable("ObjetoTarea");

        tratarTarea = v.findViewById(R.id.botonTratarTarea);
        descripcion = v.findViewById(R.id.descripcionTarea);
        calle = v.findViewById(R.id.calleTarea);
        distrito = v.findViewById(R.id.distritoTarea);
        nombre = v.findViewById(R.id.nombreTarea);

        descripcion.setText(tarea.getDescripcion());
        calle.setText(tarea.getCalle());
        nombre.setText(tarea.getName());
        Log.e("ASIGNAR",tarea.getId());

        NotificacionCiudadano n = new NotificacionCiudadano(tarea.getDescripcion(), tarea.getEmail(), tarea.getEstado(), tarea.getName(), tarea.getCalle(), tarea.getGrupo());
        tratarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ASIGNAR",tarea.getId());
                connection.asignarResponsable(tarea.getId(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                            Snackbar.make(v, "Tarea asignada a ti", Snackbar.LENGTH_LONG).show();

                            n.setEstado("EnCurso");
                            connection.crearNotificacionCiudadano(n, new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {}});

                            Fragment selectedFragment = new MenuBarrenderoFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();

                        } else {
                            Snackbar.make(v, "No se ha podido asignar a ti la tarea", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

        return v;
    }
}