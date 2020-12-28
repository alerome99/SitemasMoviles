package com.example.urclean;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.android.material.snackbar.Snackbar;

public class Tarea_Concreta extends AppCompatActivity {

    private Button tratarTarea;
    private TextView descripcion,calle,distrito,nombre;
    private Tarea tarea;
    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarea_concreta);

        tarea = (Tarea) getIntent().getSerializableExtra("ObjetoTarea");
        tratarTarea = findViewById(R.id.botonTratarTarea);
        descripcion = findViewById(R.id.descripcionTarea);
        calle = findViewById(R.id.calleTarea);
        distrito = findViewById(R.id.distritoTarea);
        nombre = findViewById(R.id.nombreTarea);
        connection = FirebaseConnection.getInstance();

        descripcion.setText(tarea.getDescripcion());
        calle.setText(tarea.getCalle());
        //distrito.setText(tarea.getDistrito());
        nombre.setText(tarea.getName());
        //estado.setText(tarea.getEstado();
        Log.e("ASIGNAR",tarea.getId());

        tratarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ASIGNAR",tarea.getId());
                connection.asignarResponsable(tarea.getId(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                            Snackbar.make(v, "Update realizado", Snackbar.LENGTH_LONG).show();
                        } else {
                            Snackbar.make(v, "No se ha podido realizar el update", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }

}
