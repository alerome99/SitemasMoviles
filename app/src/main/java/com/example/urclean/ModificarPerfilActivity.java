package com.example.urclean;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.urclean.firebase.FirebaseCallback;
import com.example.urclean.firebase.FirebaseConnection;
import com.example.urclean.model.userClient;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ModificarPerfilActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextEmail;
    EditText editTextPhone;
    Button buttonConfirmar;
    private FirebaseConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_modificar);
        connection = FirebaseConnection.getInstance();
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonConfirmar = findViewById(R.id.buttonConfirmar);

        connection.getTypeUser(correct -> {
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
                    editTextEmail.setFocusable(true);
                    editTextName.setFocusable(true);
                    editTextPhone.setFocusable(true);
                }
            } else {

            }
        });

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.out.println(editTextEmail.getText());
            }
        });
    }
}
