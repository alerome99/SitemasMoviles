package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.Tarea;
import com.google.android.material.snackbar.Snackbar;

public class ResponsableActivity extends AppCompatActivity {

    private Button tratarTarea;
    private TextView descripcion,calle,distrito,nombre;
    private Tarea tarea;
    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable);


        tarea = (Tarea) getIntent().getSerializableExtra("ObjetoTarea");
        tratarTarea = findViewById(R.id.botonCompletadoTarea);
        descripcion = findViewById(R.id.descripcionResp);
        calle = findViewById(R.id.nombreCalleResp);
        connection = FirebaseConnection.getInstance();

        descripcion.setText(tarea.getDescripcion());
        calle.setText(tarea.getCalle());
        Log.e("ASIGNAR",tarea.getId());

        tratarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("COMPLETAR",tarea.getId());
                connection.marcarCompletadaTarea(tarea.getId(), new FirebaseCallback() {
                    @Override
                    public void onResponse(boolean correct) {
                        if (correct) {
                            Snackbar.make(v, "Marcada como completada", Snackbar.LENGTH_LONG).show();
                            startActivity(new Intent(ResponsableActivity.this, menuBarrendero.class));
                        } else {
                            Snackbar.make(v, "Pues eso que no va", Snackbar.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}