package com.example.urclean.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.urclean.R;
import com.example.urclean.direccionMapsActivity;
import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class IncidenciaCiudadanoFragment extends Fragment {

    private FirebaseConnection connection;
    private EditText descripcion, asunto;
    private TextView direccion;
    private Spinner spinner;
    private String dir,cod, usuario;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_incidencia_ciudadano, container, false);

        connection = FirebaseConnection.getInstance();

        spinner = (Spinner) view.findViewById(R.id.spinnerCrearIncidencia);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.spinnerCrearIncidencia,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        direccion = view.findViewById(R.id.textViewDireccionIncidencia);
        descripcion = view.findViewById(R.id.editTextDescripcionIncidencia);
        asunto = view.findViewById(R.id.editTextAsunto);

        usuario = getArguments().getString("usuario");

        if(getArguments().getString("tipo")!=null){
            dir = getArguments().getString("dir");
            cod = getArguments().getString("cod");

            if(getArguments().getString("tipo").equals("Desperfecto"))
                spinner.setSelection(1,true);

            if(getArguments().getString("asunto")!=null)
                asunto.setText(getArguments().getString("asunto"));

            if(getArguments().getString("descripcion")!=null)
                descripcion.setText(getArguments().getString("descripcion"));

            direccion.setText(dir);
        }

        view.findViewById(R.id.botonEnviarIncidencia).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(asunto.getText().toString().trim().length() == 0){
                    asunto.setError("Ingrese un asunto a la incidencia");
                }
                if(direccion.getText().toString().trim().length() == 0){
                    direccion.setError("Ingrese el lugar de la incidencia");
                }
                if(descripcion.getText().toString().trim().length() == 0){
                    descripcion.setError("Ingrese el motivo de la incidencia");
                }
                if(direccion.getText().toString().trim().length() != 0 && descripcion.getText().toString().trim().length() != 0 && asunto.getText().toString().trim().length() != 0){
                    String item = spinner.getSelectedItem().toString();
                    switch (item){
                        case "Limpieza":
                            connection.getPersona(new FirebaseCallback() {
                                @Override
                                public void onResponse(boolean correct) {
                                    if (correct) {
                                        if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                            Log.e("vacio", "esta vacio");
                                        } else {
                                            String email="";
                                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                                email = (String) document.get("email");
                                            }
                                            connection.saveTarea(email,asunto.getText().toString(),dir,cod, descripcion.getText().toString(), new FirebaseCallback() {
                                                @Override
                                                public void onResponse(boolean correct) {
                                                    if (correct) {
                                                        direccion.setText("");
                                                        descripcion.setText("");
                                                        asunto.setText("");
                                                        Snackbar.make(view, "Incidencia enviada", Snackbar.LENGTH_LONG).show();
                                                    } else {
                                                        Snackbar.make(view, "Ha habido un error", Snackbar.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                            break;
                        case "Desperfecto":
                            connection.getCurrentUser(correct -> {
                                if (correct) {
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                    } else {
                                        String email = "";
                                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                                            email = (String) document.get("email");
                                        }
                                        connection.getDesperfectosPorEmail(email, correct2 -> {
                                            if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                                            } else {
                                                boolean bandera = true;
                                                for (QueryDocumentSnapshot document : connection.getResponse()) {
                                                    if (document.get("titulo").equals(asunto.getText().toString())) {
                                                        bandera = false;
                                                    }
                                                }
                                                if (bandera) {
                                                    connection.saveDesperfecto(asunto.getText().toString(), dir, descripcion.getText().toString(), new FirebaseCallback() {
                                                        @Override
                                                        public void onResponse(boolean correct) {
                                                            if (correct) {
                                                                asunto.setText("");
                                                                direccion.setText("");
                                                                descripcion.setText("");
                                                                Snackbar.make(view, "Desperfecto enviado", Snackbar.LENGTH_LONG).show();
                                                            } else {
                                                                Snackbar.make(view, "Ha habido un error", Snackbar.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    asunto.setError("YA HAS NOTIFICADO UN DESPERFECTO CON ESE TITULO");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            break;
                    }
                }
            }
        });

        view.findViewById(R.id.botonMaps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if(asunto.getText().toString()!=null){
                    bundle.putString("asunto", asunto.getText().toString());
                }
                if(descripcion.getText().toString()!=null){
                    bundle.putString("descripcion", descripcion.getText().toString());
                }
                bundle.putString("tipo", spinner.getSelectedItem().toString());
                bundle.putString("usuario",usuario);

                Intent intent = new Intent(getActivity().getApplicationContext(), direccionMapsActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        return view;
    }
}