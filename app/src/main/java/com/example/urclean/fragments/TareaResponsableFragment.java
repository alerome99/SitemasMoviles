package com.example.urclean.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.android.material.snackbar.Snackbar;

public class TareaResponsableFragment extends Fragment {

    private Button tratarTarea, gps;
    private TextView descripcion,calle,distrito,nombre;
    private Tarea tarea;
    private FirebaseConnection connection;

    public TareaResponsableFragment() {
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

        View v = inflater.inflate(R.layout.fragment_tarea_responsable, container, false);
        Bundle bundle = getArguments();
        tarea = (Tarea) bundle.getSerializable("ObjetoTarea");

        tratarTarea = v.findViewById(R.id.botonCompletadoTarea);
        descripcion = v.findViewById(R.id.descripcionResp);
        calle = v.findViewById(R.id.CalleResp);
        gps = v.findViewById(R.id.gpsRes);
        descripcion.setText(tarea.getDescripcion());
        calle.setText(tarea.getCalle());

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new MapsBarrenderoFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ObjetoTarea", tarea);
                selectedFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().
                 replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
            }
        });

        tratarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.marcarCompletadaTarea(tarea.getId(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                           Snackbar.make(v, "Marcada como completada", Snackbar.LENGTH_LONG).show();

                            Fragment selectedFragment = new MenuBarrenderoFragment();
                            getActivity().getSupportFragmentManager().beginTransaction().
                                    replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
                        } else {
                             Snackbar.make(v, "Pues eso que no va", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });



       return v;
    }
}