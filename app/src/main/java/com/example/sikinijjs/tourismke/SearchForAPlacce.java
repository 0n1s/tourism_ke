package com.example.sikinijjs.tourismke;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class SearchForAPlacce extends FragmentActivity {
  LatLng ltln;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_aplacce);



        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(Place.TYPE_COUNTRY)
                .setCountry("KE")
                .build();

        autocompleteFragment
                .setFilter(autocompleteFilter);
        autocompleteFragment     .setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.


                String placeDetailsStr = place.getName() + "\n"
                        + place.getId() + "\n"
                        + place.getLatLng().toString() + "\n"
                        + place.getAddress() + "\n"
                        + place.getAttributions();

                ltln=place.getLatLng();
               // Toast.makeText(SearchForAPlacce.this, placeDetailsStr, Toast.LENGTH_LONG).show();

                startActivity(new Intent(SearchForAPlacce.this, AfterSearch.class)
                        .putExtra("place_name",place.getName())
                .putExtra("Place_id",String.valueOf(place.getId().toString()))
                .putExtra("cordinates", place.getLatLng().toString())
                .putExtra("address", place.getAddress())
                .putExtra("Attributions", place.getAttributions()));



//        PlacesDetails pl = new PlacesDetails();
//        ArrayList<Double> list = new ArrayList<Double>();
//        list = pl.placeDetail(place.getId());

        //Toast.makeText(SearchForAPlacce.this,  place.getAttributions(), Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onError(Status status)
            {
                // TODO: Handle the error.
                Toast.makeText(SearchForAPlacce.this, "An error has occured!", Toast.LENGTH_SHORT).show();
            }
        });

        GeoDataClient geoDataClient;




    }

    public void get_location_details()
    {

    }
}


/*

 */