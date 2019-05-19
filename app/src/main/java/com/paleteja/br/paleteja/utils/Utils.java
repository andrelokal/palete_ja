package com.paleteja.br.paleteja.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;

public class Utils {

    public static Double[] getLatLong(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return new Double[] {0.0,0.0};
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        return new Double[] {location.getLongitude(), location.getLatitude()};
    }
}
