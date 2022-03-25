package fr.uge.hyperstack.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

import fr.uge.hyperstack.activities.EditActivity;

public class Localisation {

    EditActivity context;
    LocationManager locationManager = null;
    LocationListener locationListener = null;
    private int nextPermissionRequestCode = 1;

    public Localisation(EditActivity context) {
        this.context = context;
        this.locationManager = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) { }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) { }

            @Override
            public void onProviderEnabled(String provider) { }

            @Override
            public void onProviderDisabled(String provider) { }
        };
    }

    private Runnable onSuccess(){
        return () ->{
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Permission information");
            alertDialog.setMessage("Permission already granted");
            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
            alertDialog.setButton("OK", (k,v) -> {});
            alertDialog.show();
        };
    }

    private Runnable onFailure(){
        return () ->{
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Permission information");
            alertDialog.setMessage("Permission not granted");
            alertDialog.setIcon(android.R.drawable.ic_dialog_info);
            alertDialog.setButton("OK", (k,v) -> {});
            alertDialog.show();
        };
    }

    public void runWithPermission(String permissionName, String rationale) {
        // Is the permission already granted?
        if (ContextCompat.checkSelfPermission(context, permissionName) != PackageManager.PERMISSION_GRANTED) {
            int code = nextPermissionRequestCode++; // use an unique id for the permission request
            ActivityCompat.requestPermissions(context, new String[]{permissionName}, code);
        } else
            onSuccess().run(); // the permission has already been given
    }

    /** This method is called when the user has answered to the permission request */

}
