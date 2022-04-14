package com.example.googlemapsactivity;


import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.googlemapsactivity.databinding.ActivityMapsBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;


import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Call;
import okhttp3.Response;
import okhttp3.HttpUrl;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Location stuff here
        private FusedLocationProviderClient fusedLocationClient;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                        }
                    }
                });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void buttonPush(View view) {

        //Do this next bit in a class

        final String PLACE_BASE_URL =
                "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";
        final String INPUT_PARAM = "input";
        final String INPUTTYPE_PARAM = "inputtype";
        final String FIELDS_PARAM = "fields";
        final String KEY_PARAM = "key";

        Uri builtUri = Uri.parse(PLACE_BASE_URL)
                .buildUpon()
                .appendQueryParameter(INPUT_PARAM, "nearby coffee shop")
                .appendQueryParameter(INPUTTYPE_PARAM, "textquery")
                .appendQueryParameter(FIELDS_PARAM, "formatted_address,name,rating,opening_hours,geometry")
                .appendQueryParameter(KEY_PARAM, "AIzaSyAKVTNcq8VeViWRo4zoUKYTffb7McmkR64")
                .build();


        URL apiRequestUrl;
        try {
            apiRequestUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            // Do something
            return;
        }
        System.out.println(builtUri.toString());
        System.out.println(builtUri.toString() == "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=Museum%20of%20Contemporary%20Art%20Australia&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Copening_hours%2Cgeometry&key=AIzaSyAKVTNcq8VeViWRo4zoUKYTffb7McmkR64");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(apiRequestUrl)
                .method("GET", null)
                .build();


        Callback callback = new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    System.out.println(responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                } catch (JSONException e) {
                    // Do something
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
              // Do something
            }
        };


        client.newCall(request).enqueue(callback);
        updateTextMessage();
    }
    private void updateTextMessage(){
        System.out.println("Button successfully pressed");
        final TextView changingText = (TextView) findViewById(R.id.text_to_change);


        /* client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject json = new JSONObject(myResponse);
                            txtString.setText(json.getJSONObject("data").getString("first_name")+ " "+json.getJSONObject("data").getString("last_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });


         */
        changingText.setText("Hello");
    }
}