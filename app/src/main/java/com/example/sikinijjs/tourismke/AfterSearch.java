package com.example.sikinijjs.tourismke;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AfterSearch extends AppCompatActivity {
String location_name;
 String url2;
    TextView t4;
    Button btn, btn4;

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
        setContentView(R.layout.activity_after_search);
         t4= (TextView)findViewById(R.id.textView4);
        t4.setText("");
        Intent intent = getIntent();
        location_name = intent.getStringExtra("place_name");
//        String place_id = intent.getStringExtra("place_id");
//        String place_cordinates = intent.getStringExtra("place_cordinates");
//        String address = intent.getStringExtra("address");
        getSupportActionBar().setTitle(location_name);
        String loc=location_name.replaceAll("\\s","");
        final String url="https://en.wikipedia.org/w/api.php?action=opensearch&search="+loc+"&limit=1&format=json";
        fetch_details(url);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        btn=(Button)findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    Uri uri = Uri.parse(url2);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                catch (Exception ex)
                {
                    Toast.makeText(AfterSearch.this, "No data found!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn4=(Button)findViewById(R.id.button4);
        WebView webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.kws.go.ke/latest-News");

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(AfterSearch.this, MoreDetails.class)
                .putExtra("place_name", location_name));

            }
        });



    }



    public void fetch_details (final String url)

    {
        class AddEmployee extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(AfterSearch.this);
                progressDialog.setMessage("Fetching location details...");
                progressDialog.show();

            }

            @Override
            protected String doInBackground(Void... v) {
                RequestHandler rh = new RequestHandler();
                String res = rh.sendGetRequest(url);
                return res;

            }

            @Override
            protected void onPostExecute(final String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
               //Toast.makeText(AfterSearch.this, "result"+s, Toast.LENGTH_LONG).show();


                try {
                    JSONArray jsonArray = new JSONArray(s);

                    String  obj1=jsonArray.getString(0);
                    String obj2=jsonArray.getString(1);
                    String obj3=jsonArray.getString(2);
                    String obj4=jsonArray.getString(3);



                    obj1 = obj1.substring(2, obj1.length()-2);
                    obj2 = obj2.substring(2, obj2.length()-2);
                    obj3 = obj3.substring(2, obj3.length()-2);
                    obj4 = obj4.substring(2, obj4.length()-2);


                    String url = obj4.substring(0, 10);
                    String next=obj4.substring(10, obj4.length());


                    next = next.replace("\\", "");
                    url2=url+next;

                    t4.setText(obj3);






                   // Toast.makeText(AfterSearch.this, obj4, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                }
                catch (Exception e) {

                    t4.setText("No data found!");
                }

            }
        }
        AddEmployee ae = new AddEmployee();
        ae.execute();


    }
}
