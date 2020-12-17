package com.example.urclean;

import android.content.ClipData;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.urclean.R;
import com.example.urclean.firebase.FirebaseConnection;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Perfil extends AppCompatActivity {

    private FirebaseConnection connection;
    TextView textViewEmail;
    TextView textViewName;
    TextView textViewUsername;
    TextView testViewPhone;
    TextView textViewDni;
    EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_personal);

        textViewEmail = findViewById(R.id.textViewEmail);
        textViewName = findViewById(R.id.textViewName);
        textViewUsername = findViewById(R.id.textViewUsername);
        testViewPhone = findViewById(R.id.textViewTelefono);
        textViewDni = findViewById(R.id.textViewDni);

        connection = FirebaseConnection.getInstance();
        connection.getTypeUser(correct -> {
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
                    textViewEmail.setText(email);
                    textViewName.setText(name);
                    textViewUsername.setText(username);
                    testViewPhone.setText(phone);
                    textViewDni.setText(dni);
                }
            }else{

            }
        });

        super.onCreate(savedInstanceState);
    }
}
