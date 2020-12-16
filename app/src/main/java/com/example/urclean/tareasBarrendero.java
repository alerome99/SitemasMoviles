package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class tareasBarrendero extends AppCompatActivity {

    private FirebaseConnection connection;
    private String grupoUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        grupoUser = "";
        connection = FirebaseConnection.getInstance();
        
        connection.getPersona(new FirebaseCallback() {
            @Override
            public void onResponse(boolean correct) {
                if (correct) {
                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {
                        Log.e("vacio", "eta vacio");
                    } else {
                        for (QueryDocumentSnapshot document : connection.getResponse()) {
                            grupoUser = (String) document.get("grupo");
                            //Log.e("NAME",""+(String) document.get("name"));
                            Log.e("NAME",""+(String) document.get("grupo"));
                        }

                        connection.getTarea(grupoUser, new FirebaseCallback() {
                            @Override
                            public void onResponse(boolean correct) {
                                if (correct){
                                    if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                                    }else{
                                        for (QueryDocumentSnapshot document : connection.getResponse()){
                                            Log.e("Descripcion",""+(String) document.get("Descripcion"));

                                        }
                                    }
                                }
                            }
                        });
                    }

                } else {
                    Log.e("ffail", "fail conection");
                }
            }
        });
    }
}
