package com.example.urclean.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.urclean.R;
import com.example.urclean.model.Tarea;
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

public class MapsBarrenderoFragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private Tarea tarea;
    private static final int LOCATION_REQUEST_CODE = 101;
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

        if (ActivityCompat.checkSelfPermission(MapsBarrenderoFragment.this.getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            mMap.setMyLocationEnabled(true);


        } else {

            ActivityCompat.requestPermissions(MapsBarrenderoFragment.this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

/*
            new Thread(new Runnable() {
                public void run() {

                    Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());
                    List<Address> address;
                    try {
                        address = geocoder.getFromLocationName(strAddress, 5);
                        Address location = address.get(0);

                    LatLng valladolid = new LatLng(location.getLatitude(), location.getLongitude());

                     Marker marker = mMap.addMarker(new MarkerOptions().position(valladolid).title("Tarea").draggable(false));
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(valladolid,16));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


 */




        try {
            address = geocoder.getFromLocationName(strAddress, 5);
            Address location = address.get(0);
            //  location.getLatitude();
            //  location.getLongitude();
            LatLng valladolid = new LatLng(location.getLatitude(), location.getLongitude());

            Marker marker = mMap.addMarker(new MarkerOptions().position(valladolid).title("Tarea").draggable(false));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(valladolid,16));

        } catch (IOException e) {
            e.printStackTrace();
        }


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


}

