package com.example.sikinijjs.tourismke.nearme;

import android.app.AlertDialog;
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
import com.example.sikinijjs.tourismke.Function;
import com.example.sikinijjs.tourismke.MoreDetails;
import com.example.sikinijjs.tourismke.R;
import com.example.sikinijjs.tourismke.SearchForAPlacce;
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



        final Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayList<String> options=new ArrayList<String>();
        options.add("movie_theatre");
        options.add("museum");
        options.add("restaurant");
        options.add("stadium");
        options.add("zoo");
        options.add("aquarium");
        options.add("amusement_park");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options);
        spinner.setAdapter(adapter);
//spinner2

        final Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        ArrayList<String> options2=new ArrayList<String>();
        options2.add("Select your county");
        options2.add("Baringo");
        options2.add("Bomet");
        options2.add("Nyeri");
        options2.add("Kirinyaga");
        options2.add("Turkana");
        options2.add("West Pokot");
        options2.add("Samburu");
        options2.add("Nairobi");
        options2.add("Nyamira");
        options2.add("Kisii");

        options2.add("Migori");
        options2.add("Homa Bay");
        options2.add("Kisumu");
        options2.add("Siaya");
        options2.add("Busia");
        options2.add("Bungoma");
        options2.add("Vihiga");
        options2.add("Kakamega");
        options2.add("Bomet");
        options2.add("Kericho");

        options2.add("Kajiado");
        options2.add("Narok");
        options2.add("Nakuru");
        options2.add("Laikipia");
        options2.add("Baringo");
        options2.add("Nandi");
        options2.add("Elgeyo-Marakwet");
        options2.add("Uasin Gishu");
        options2.add("Trans-Nzoia");
        options2.add("Samburu");

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,options2);
        spinner2.setAdapter(adapter2);



Button button7 = findViewById(R.id.button7);
button7.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        EditText place_name=(EditText)findViewById(R.id.editText3);
        nearby= spinner.getSelectedItem().toString();

        String place = place_name.getText().toString();
        String county = spinner2.getSelectedItem().toString().trim();

        Log.d("selected_position", String.valueOf(spinner2.getSelectedItemPosition()));

        if(place.length()<2 && spinner2.getSelectedItemPosition()==0)
        {
            Toast.makeText(SearchLocbyType.this, "Please enter a location or select your county", Toast.LENGTH_SHORT).show();
        }
        else if(spinner2.getSelectedItemPosition()!=0 && place.length()>2)
        {
            Toast.makeText(SearchLocbyType.this, "Select either county or place name", Toast.LENGTH_SHORT).show();
        }
        else if(place.length()>2)
            return_cordinates(place_name.getText().toString());
        else return_cordinates(county);

    }
});
        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText place_name=(EditText)findViewById(R.id.editText3);
                nearby= spinner.getSelectedItem().toString();

               String place = place_name.getText().toString();
               String county = spinner2.getSelectedItem().toString().trim();

               Log.d("selected_position", String.valueOf(spinner2.getSelectedItemPosition()));

               if(place.length()<2 && spinner2.getSelectedItemPosition()==0)
               {
                   Toast.makeText(SearchLocbyType.this, "Please enter a location or select your county", Toast.LENGTH_SHORT).show();
               }
               else if(spinner2.getSelectedItemPosition()!=0 && place.length()>2)
               {
                   Toast.makeText(SearchLocbyType.this, "Select either county or place name", Toast.LENGTH_SHORT).show();
               }
               else if(place.length()>2)
                   return_cordinates(place_name.getText().toString());
               else return_cordinates(county);

            }
        });


        listView =(ListView)findViewById(R.id.listview);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);

              //  Toast.makeText(SearchLocbyType.this, "", Toast.LENGTH_SHORT).show();

                /*
                employees.put("place_name", placeName);
                    employees.put("lat", String.valueOf(lat));
                    employees.put("distance", String.valueOf(final_distance));
                    employees.put("lng", String.valueOf(lng));
                    employees.put("latlng", String.valueOf(lng)+","+String.valueOf(lat));
                    employees.put("vicinity",vicinity);
                    employees.put("type",nearby);
                 */


                String latlat [] = map.get("latlng").split(",");

                String lattt = latlat[0];
                String lon = latlat[1];

                ltln = new LatLng(Double.parseDouble(lon),Double.parseDouble(lattt));



                startActivity(new Intent(SearchLocbyType.this, AfterSearch.class)
                        .putExtra("place_name",map.get("place_name"))
                        .putExtra("Place_id","")
                        .putExtra("cordinates", map.get("latlang"))
                        .putExtra("address", "")
                        .putExtra("Attributions", ""));
//                Intent intent = new Intent(SearchLocbyType.this, MoreDetails.class);
//                intent.putExtra("place_name", map.get("place_name"));
//                startActivity(intent);

            }
        });




    }

    public void return_weather(final String location)
    {


        Function.placeIdTask asyncTask =new Function.placeIdTask(new Function.AsyncResponse()
        {
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String sun_rise) {
//
//                cityField.setText(weather_city);
//                updatedField.setText(weather_updatedOn);
//                detailsField.setText(weather_description);
//                currentTemperatureField.setText(weather_temperature);
//                humidity_field.setText("Humidity: "+weather_humidity);
//                pressure_field.setText("Pressure: "+weather_pressure);
//                weatherIcon.setText(Html.fromHtml(weather_iconText));

                new AlertDialog.Builder(SearchLocbyType.this)
                        .setTitle("Weather for "+location)
                        .setMessage("City "+weather_city+"\nUpdated on "+weather_updatedOn+"\n"+"weather_temperature"+weather_temperature
                        +"\nweather_description"+weather_description)
                .setNegativeButton("okay", null).show();

            }
        });
        asyncTask.execute(String.valueOf(latitude), String.valueOf(longitude)); //  asyncTask.execute("Latitude", "Longitude")

    }
    public void return_cordinates(final String adress) {


         class LongOperation extends AsyncTask<String, Void, String> {
             final ProgressDialog progressDialog = new ProgressDialog(SearchLocbyType.this);
             String url;
             @Override
             protected void onPreExecute() {


                 progressDialog.setMessage("Fetching coordinates for "+ adress);
                 progressDialog.show();
                 super.onPreExecute();
             }

             @Override
            protected String doInBackground(String... params)
             {


                 Geocoder geocoder = new Geocoder(SearchLocbyType.this);
                 List<Address> addresses;
                 try {
                     addresses = geocoder.getFromLocationName(adress, 1);
                     if (addresses.size() > 0)
                     {
                         latitude = addresses.get(0).getLatitude();
                         longitude = addresses.get(0).getLongitude();
                          url = getUrl(latitude, longitude, nearby);

                     }
                     else
                     {

                     }


                 } catch (IOException e) {
                     e.printStackTrace();
                 }

                return "Executed";
            }

            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
                fetchdata(url);
                return_weather(adress);

            }
        }

        new LongOperation().execute();






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
