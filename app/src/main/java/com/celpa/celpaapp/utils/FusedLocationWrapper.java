package com.celpa.celpaapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class FusedLocationWrapper {

    private Activity activity;
    private FusedLocationProviderClient fusedLocationClient;

    public interface OnSuccessListener extends com.google.android.gms.tasks.OnSuccessListener {}

    public FusedLocationWrapper(Activity activity) {
        this.activity = activity;
    }

    private void init() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    private void getLastLocation(OnSuccessListener listener) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(listener);
    }
}
