package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.userClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ModificarPerfilActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPhone;
    Button buttonModificar;
    TextView textView15;
    private FirebaseConnection connection;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_modificar);
        connection = FirebaseConnection.getInstance();
        editTextName = findViewById(R.id.editTextName);
        textView15 = findViewById(R.id.textView15);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonModificar = findViewById(R.id.buttonModificar);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        textView15.setVisibility(View.GONE);
        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(ModificarPerfilActivity.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity(new Intent(ModificarPerfilActivity.this, ListaUsuariosActivity.class));
                        //Ir a Incidencia
                        break;
                    case (R.id.navigation_list_ciudadano):
                        startActivity(new Intent(ModificarPerfilActivity.this, ListaBarrenderosActivity.class));
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        startActivity(new Intent(ModificarPerfilActivity.this, AddGrupoActivity.class));
                        //Ir a lista de notificaciones
                        break;
                }
            }
        });

        connection.getPersona(correct -> {
            if (correct) {
                if (connection.getResponse().isEmpty() || connection.getResponse() == null) {

                } else {
                    String email = "";
                    String username = "";
                    String phone = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()) {
                        email = (String) document.get("email");
                        username = (String) document.get("username");
                        phone = (String) document.get("telefono");
                    }
                    editTextEmail.setText(email);
                    editTextName.setText(username);
                    editTextPhone.setText(phone);
                    textView15.setText(email);
                    editTextEmail.setFocusable(true);
                    editTextName.setFocusable(true);
                    editTextPhone.setFocusable(true);
                }
            } else {

            }
        });

        //por ahora solo se pueden modificar el telefono el usuario y el email
        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.getUsuarioPorEmail(textView15.getText().toString(), correct -> {
                    if (correct){
                        if (connection.getResponse().isEmpty() || connection.getResponse() == null){
                        }else{
                            String id = "";
                            for (QueryDocumentSnapshot document : connection.getResponse()) {
                                id = (String) document.getId();
                            }
                            connection.modificarDatos(id, editTextName.getText().toString(), editTextPhone.getText().toString(),
                                    editTextEmail.getText().toString(), new FirebaseCallback() {
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
                    }else{
                    }
                });
            }
        });
    }
}
