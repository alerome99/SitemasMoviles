package com.example.urclean;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class solicitudCambioTurno extends AppCompatActivity {

    Button btnCalOrigen;
    Button btnCalDestino;
    TextView txtFechaOrigen;
    TextView txtFechaDestino;
    Calendar c;
    DatePickerDialog dpd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud_cambio_turno);

        btnCalOrigen = (Button) findViewById(R.id.buttonAbrirCalendarioOrigen);
        btnCalDestino = (Button) findViewById(R.id.buttonAbrirCalendarioDestino);
        txtFechaOrigen = (TextView) findViewById(R.id.textViewTextoFechaOrigen);
        txtFechaDestino = (TextView) findViewById(R.id.textViewTextoFechaDestino);

    }

    public void abrirCalendario (View v) {
        switch (v.getId()){
            case R.id.buttonAbrirCalendarioOrigen:
                btnCalOrigen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        c= Calendar.getInstance();
                        int dia = c.get(Calendar.DAY_OF_MONTH);
                        int mes = c.get(Calendar.MONTH);
                        int anio = c.get(Calendar.YEAR);

                        dpd = new DatePickerDialog(solicitudCambioTurno.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                txtFechaOrigen.setText(dayOfMonth+ "/" +month+ "/" +year);
                            }
                        },dia,mes,anio);
                        dpd.show();
                    }
                });
                break;

            case R.id.buttonAbrirCalendarioDestino:
                btnCalDestino.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        c= Calendar.getInstance();
                        int dia = c.get(Calendar.DAY_OF_MONTH);
                        int mes = c.get(Calendar.MONTH);
                        int anio = c.get(Calendar.YEAR);

                        dpd = new DatePickerDialog(solicitudCambioTurno.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                txtFechaDestino.setText(dayOfMonth+ "/" +month+ "/" +year);
                            }
                        },dia,mes,anio);
                        dpd.show();
                    }
                });

                break;
        }


        /*btnCalOrigen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c= Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int anio = c.get(Calendar.YEAR);

                dpd = new DatePickerDialog(solicitudCambioTurno.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        txtFechaOrigen.setText(dayOfMonth+ "/" +month+ "/" +year);
                    }
                },dia,mes,anio);
                dpd.show();
            }
        });*/
    }

}