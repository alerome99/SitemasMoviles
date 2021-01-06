package com.example.urclean.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.urclean.R;
import com.example.urclean.model.Tarea;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsBarrenderoFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener , GoogleMap.OnPolygonClickListener{

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Tarea tarea;
    private static final int LOCATION_REQUEST_CODE = 101;
    FusedLocationProviderClient fusedLocationProviderClient;
    public MapsBarrenderoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_maps_barrendero, container, false);

        Bundle bundle = getArguments();
        tarea = (Tarea) bundle.getSerializable("ObjetoTarea");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);


        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }else{
        }


        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
        List<Address> address;
        String strAddress = tarea.getCalle();
        String lat = tarea.getLat();
        String lng = tarea.getLng();

        if (ActivityCompat.checkSelfPermission(MapsBarrenderoFragment.this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);


        } else {

            ActivityCompat.requestPermissions(MapsBarrenderoFragment.this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        Log.e("tag",lat);
        Log.e("tag",lng);

        double latd = Double.parseDouble(lat.replace(",","."));
        double lngd = Double.parseDouble(lng.replace(",","."));


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsBarrenderoFragment.this.getActivity());


        LatLng valladolid = new LatLng(latd, lngd);

        Marker marker = mMap.addMarker(new MarkerOptions().position(valladolid).title("Tarea").draggable(false));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(valladolid,16));

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Log.e("LOC","ESTO");

                Location location = task.getResult();

                if(location != null ){

                    mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(location.getLatitude(),location.getLongitude()), new LatLng(latd,lngd))
                            .width(5)
                            .color(android.R.color.holo_red_dark));

                }else{
                    Log.e("NL","No Hay localizacion");
                }


            }
        });

/*
        Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(, ),
                        new LatLng(latd,lngd)));

*/
        /*
        googleMap.addPolyline(new PolylineOptions()
        .add(new LatLng(start_lat, start_lon), new LatLng(end_lat,end_lon))
         .width(5)
        .color(Color.RED));
         */


    }



    protected void requestPermission(String permissionType, int requestCode) {
        int permission = ContextCompat.checkSelfPermission(MapsBarrenderoFragment.this.getActivity(),
                permissionType);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsBarrenderoFragment.this.getActivity(),
                    new String[]{permissionType}, requestCode
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {

                if (grantResults.length == 0
                        || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapsBarrenderoFragment.this.getActivity(), "Unable to show location - permission required", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }


    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }
}

