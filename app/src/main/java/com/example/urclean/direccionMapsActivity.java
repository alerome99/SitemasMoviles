package com.example.urclean;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
    private String dir, cod;
    private TextView tvCoordenadas, tvDireccion;
    private String asunto, tipo, descripcion;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direccion_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Bundle bundle = this.getIntent().getExtras();
        if (bundle.getString("asunto") != null) {
            asunto = bundle.getString("asunto");
        }

        tipo = bundle.getString("tipo");

        if (bundle.getString("descripcion") != null) {
            descripcion = bundle.getString("descripcion");
        }

        tvCoordenadas = findViewById(R.id.textViewCoordenadas);
        tvDireccion = findViewById((R.id.textViewDireccion));

        findViewById(R.id.botonAceptarCoordenadas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(direccionMapsActivity.this, MenuCiudadanoActivity.class);

                Bundle b = new Bundle();
                b.putString("dir", dir);
                b.putString("cod", cod);
                b.putString("tipo", tipo);
                if (asunto != null) {
                    b.putString("asunto", asunto);
                }
                if (descripcion != null) {
                    b.putString("descripcion", descripcion);
                }

                intent.putExtras(b);

                startActivity(intent);

            }
        });

        findViewById(R.id.botonAtras).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(direccionMapsActivity.this, MenuCiudadanoActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.botonUbicacion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(direccionMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(direccionMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(myLocation!=null){
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        double lat = myLocation.getLatitude();
                        double lng = myLocation.getLongitude();
                        LatLng latLng = new LatLng(lat,lng);
                        marker.setPosition(latLng);
                        List<Address> direccion = geocoder.getFromLocation(lat, lng, 1);
                        dir = direccion.get(0).getAddressLine(0);
                        tvDireccion.setText("Dirección: "+dir);
                        cod = direccion.get(0).getPostalCode();
                        String title = String.format(Locale.getDefault(), getString(R.string.marker_detail_latlng),
                                lat, lng);
                        tvCoordenadas.setText("Coordenadas: "+ title);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng valladolid = new LatLng(41.6521328, -4.728562); // Coordenadas de valladolid
        marker = mMap.addMarker(new MarkerOptions().position(valladolid).title("Valladolid").draggable(true));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(valladolid, 16));

        googleMap.setOnMarkerDragListener(this);

        mMap.getUiSettings().setZoomControlsEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

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
        double lat = marker.getPosition().latitude;
        double lng = marker.getPosition().longitude;

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> direccion = geocoder.getFromLocation(lat, lng, 1);
            dir = direccion.get(0).getAddressLine(0);
            tvDireccion.setText("Dirección: "+dir);
            cod = direccion.get(0).getPostalCode();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}