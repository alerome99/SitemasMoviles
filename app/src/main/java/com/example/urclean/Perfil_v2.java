package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseConnection;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Perfil_v2 extends AppCompatActivity {
    private FirebaseConnection connection;
    EditText editTextName;
    EditText editTextDni;
    EditText editTextPhone;
    EditText editTextEmail;
    EditText editTextUsername;
    Button buttonModificar;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal_v2);

        editTextName = findViewById(R.id.editTextName);
        editTextDni = findViewById(R.id.editTextDni);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        buttonModificar = findViewById(R.id.buttonModificar);

        connection = FirebaseConnection.getInstance();

        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        navigation.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case (R.id.navigation_home):
                        startActivity(new Intent(Perfil_v2.this, Perfil_v2.class));
                        break;
                    case (R.id.navigation_incidencia_ciudadano):
                        startActivity(new Intent(Perfil_v2.this, ListaUsuariosActivity.class));
                        //Ir a Incidencia
                        break;
                    case (R.id.navigation_list_ciudadano):
                        //Ir a...?
                        break;
                    case (R.id.navigation_notifications):
                        //Ir a lista de notificaciones
                        break;
                }
            }
        });

        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Perfil_v2.this, ModificarPerfilActivity.class));
            }
        });

        connection.getPersona(correct -> {
            if (correct){
                if (connection.getResponse().isEmpty() || connection.getResponse() == null){

                }else{
                    String email = "";
                    String name = "";
                    String username = "";
                    String phone = "";
                    String dni = "";
                    for (QueryDocumentSnapshot document : connection.getResponse()){
                        email = (String) document.get("email");
                        name = (String) document.get("name");
                        username = (String) document.get("username");
                        phone = (String) document.get("telefono");
                        dni = (String) document.get("dni");
                    }
                    editTextEmail.setText(email);
                    editTextName.setText(name);
                    editTextUsername.setText(username);
                    editTextPhone.setText(phone);
                    editTextDni.setText(dni);
                    editTextEmail.setFocusable(false);
                    editTextName.setFocusable(false);
                    editTextUsername.setFocusable(false);
                    editTextPhone.setFocusable(false);
                    editTextDni.setFocusable(false);
                }
            }else{

            }
        });

        super.onCreate(savedInstanceState);
    }
}
