package com.example.sikinijjs.tourismke;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.sikinijjs.tourismke.nearme.SearchLocbyType.ltln;

public class MoreDetails extends AppCompatActivity implements OnMapReadyCallback {
    String location_name;
    Button navigate;
    LocationManager locationManager;
    Location currentLocation;
    private GoogleMap mMap;



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_details);
        Intent intent = getIntent();
        location_name = intent.getStringExtra("place_name");
        getSupportActionBar().setTitle(location_name);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }




    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Do other setup activities here too, as described elsewhere in this tutorial.
        mMap = map;
        Toast.makeText(this, "Touch the marker for Navigation instructions", Toast.LENGTH_SHORT).show();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Add a marker in Sydney and move the camera
        //LatLng TutorialsPoint = new LatLng(Double.parseDouble(), Double.parseDouble(longtitude));


        mMap.addMarker(new
                MarkerOptions().position(ltln).title(location_name));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ltln,14));
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {

                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, null);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(location_name);

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText("Navigate to location now!");

               // Toast.makeText(getApplicationContext(),String.valueOf(marker.getTitle()), Toast.LENGTH_LONG).show();

                return infoWindow;
                //
               // return infoWindow;
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
