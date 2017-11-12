package domain.hackathon.hackathon2017;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;


public class GPS extends Activity {
    private LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    private LocationListener locationListener;
    double lon,lat;


    public void GetCurrentLocation() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
           requestPermissions(new String[]{
                   Manifest.permission.ACCESS_FINE_LOCATION,
                   Manifest.permission.ACCESS_COARSE_LOCATION,
                   Manifest.permission.INTERNET}
                   ,10);
            return;
        }
        }
        else
            getLocation();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 10:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                   getLocation();
        }
    }

    private void getLocation()
    {
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location)
            {
                lon=location.getLongitude();
                lat=location.getLatitude();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s)
            {
                Intent Intent= new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(Intent);
            }
        };
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }
}
