package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuCiudadanoActivity extends AppCompatActivity {

    private Button botonQuejas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuciudadano);
        botonQuejas = (Button)findViewById(R.id.botonQuejasCiu);

    }

    public void clickQuejas(View v){
        //Creamos el intent
        Intent intent = new Intent(MenuCiudadanoActivity.this, QuejasActivity.class);
        startActivity(intent);
    }
}