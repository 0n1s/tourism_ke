package com.example.sikinijjs.tourismke.nearme;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sikinijjs.tourismke.AfterSearch;
import com.example.sikinijjs.tourismke.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchLocbyType extends AppCompatActivity {
Double latitude;
Double longitude;
String nearby;
public static LatLng ltln;

ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_locby_type);
              /*

    movie_theater
    museum
    restaurant
    stadium
    zoo
    casino
    amusement_park
    aquarium

     */


        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<String> options=new ArrayList<String>();
        options.add("hospital");
        options.add("museum");
        options.add("restaurant");
        options.add("school");
        options.add("mosque");
        options.add("insurance_agency");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        spinner.setAdapter(adapter);





        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText place_name=(EditText)findViewById(R.id.editText3);
                nearby= spinner.getSelectedItem().toString();
                return_cordinates(place_name.getText().toString());

            }
        });


        listView =(ListView)findViewById(R.id.listview);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);




            }
        });




    }

    public void return_cordinates(final String adress) {

        final ProgressDialog progressDialog = new ProgressDialog(SearchLocbyType.this);
        progressDialog.setMessage("Fetching coordinates for "+ adress);
        progressDialog.show();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Geocoder geocoder = new Geocoder(SearchLocbyType.this);
                List<Address> addresses;
                try {
                    addresses = geocoder.getFromLocationName(adress, 1);
                    if (addresses.size() > 0) {
                        latitude = addresses.get(0).getLatitude();
                        longitude = addresses.get(0).getLongitude();
                        // Toast.makeText(this, String.valueOf(longitude).substring(0,4), Toast.LENGTH_SHORT).show();
                        String url = getUrl(latitude, longitude, nearby);
                        progressDialog.dismiss();
                        fetchdata(url);
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(SearchLocbyType.this, "Location not found!", Toast.LENGTH_SHORT).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });



    }

    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + "10000");
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyCyG976e6EXwSj8BS2jrfmOym0CJ6OcsC8");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    public void fetchdata(final String url_data)
    {
        class GetNearbyPlacesData extends AsyncTask<Void,Void,String>
        {
            String googlePlacesData;
            GoogleMap mMap;
            String url;
            ProgressDialog progressDialog = new ProgressDialog(SearchLocbyType.this);




            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog.setMessage("Searching nearby places");
                progressDialog.show();
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    Log.d("GetNearbyPlacesData", "doInBackground entered");
                    //  mMap = (GoogleMap) params[0];
                    url=url_data;
                    DownloadUrl downloadUrl = new DownloadUrl();
                    googlePlacesData = downloadUrl.readUrl(url);
                    Log.d("GooglePlacesReadTask", "doInBackground Exit");
                } catch (Exception e) {
                    Log.d("GooglePlacesReadTask", e.toString());
                }
                return googlePlacesData;
            }

            @Override
            protected void onPostExecute(String result)
            {
              //  Toast.makeText(SearchLocbyType.this, result, Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Log.d("GooglePlacesReadTask", "onPostExecute Entered");
                List<HashMap<String, String>> nearbyPlacesList = null;
                DataParser dataParser = new DataParser();
                nearbyPlacesList =  dataParser.parse(result);
                ShowNearbyPlaces(nearbyPlacesList);
                Log.d("GooglePlacesReadTask", "onPostExecute Exit");
            }

            private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

                for (int i = 0; i < nearbyPlacesList.size(); i++)
                {
                    //Log.d("onPostExecute","Entered into showing locations");
                    HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
                    double lat = Double.parseDouble(googlePlace.get("lat"));
                    double lng = Double.parseDouble(googlePlace.get("lng"));
                    String placeName = googlePlace.get("place_name");
                    String vicinity = googlePlace.get("vicinity");
                    ltln=new LatLng(lat,lng);
                    Location loc1 = new Location("");
                    loc1.setLatitude(lat);
                    loc1.setLongitude(lng);
                    Location loc2 = new Location("");
                    loc2.setLatitude(latitude);
                    loc2.setLongitude(longitude);
                    float final_distance = loc1.distanceTo(loc2)/1000;
                    HashMap<String, String> employees = new HashMap<>();
                    employees.put("place_name", placeName);
                    employees.put("lat", String.valueOf(lat));
                    employees.put("distance", String.valueOf(final_distance));
                    employees.put("lng", String.valueOf(lng));
                    employees.put("latlng", String.valueOf(lng)+","+String.valueOf(lat));
                    employees.put("vicinity",vicinity);
                    employees.put("type",nearby);
                    list.add(employees);



                }


                ListAdapter adapter = new SimpleAdapter(SearchLocbyType.this, list, R.layout.listviewlist,
                        new String[]{"place_name",
                                "latlng",
                                "distance"
                        }, new int[]{R.id.textView5, R.id.textView10,R.id.textView9});
                listView.setAdapter(adapter);

            }
        }
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
        getNearbyPlacesData.execute();



    }



    private void showthem(String s)
    {



    }






}
