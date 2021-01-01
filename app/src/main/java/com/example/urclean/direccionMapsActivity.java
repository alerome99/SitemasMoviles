package com.example.urclean;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class direccionMapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private GoogleMap mMap;
    private double lat, lng;
    private String dir;
    private TextView tvCoordenadas, tvDireccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvCoordenadas = findViewById(R.id.textViewCoordenadas);
        tvDireccion = findViewById((R.id.textViewDireccion));

        findViewById(R.id.botonAceptarCoordenadas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (direccionMapsActivity.this, MenuCiudadanoActivity.class);

                Bundle b = new Bundle();
                b.putString("lat", String.format(Locale.getDefault(), "%1$.4f", lat));
                b.putString("lng", String.format(Locale.getDefault(), "%1$.4f", lng));
                b.putString("dir", dir);

                intent.putExtras(b);

                startActivity(intent);

            }
        });

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng valladolid = new LatLng(41.6521328, -4.728562); // Coordenadas de valladolid
        mMap.addMarker(new MarkerOptions().position(valladolid).title("Valladolid").draggable(true));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(valladolid, 12));

        googleMap.setOnMarkerDragListener(this);


    }

    @Override
    public void onMarkerDragStart(Marker marker) {
       // Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        String title = String.format(Locale.getDefault(), getString(R.string.marker_detail_latlng),
                marker.getPosition().latitude, marker.getPosition().longitude);
        tvCoordenadas.setText("Coordenadas: "+ title);
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        // Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
        lat = marker.getPosition().latitude;
        lng = marker.getPosition().longitude;

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> direccion = geocoder.getFromLocation(lat, lng, 1);
            dir = direccion.get(0).getAddressLine(0);
            tvDireccion.setText("Dirección: "+dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}