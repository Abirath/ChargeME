package com.example.chargeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;
    ArrayList<LocationResponse> LocationResponse = new ArrayList<>();
    ArrayList<AddressInfo> addList = new ArrayList<>();

    ArrayList<HashMap<String, String>> addressList;
    ArrayList<AddressInfo> addressInfoArrayList;
    ArrayList<HashMap<String, String>> viewAddressList;
    private int PermissionCode = 1;

    ArrayList<LocationResponse> locationList = new ArrayList<>();
    private TextView txtresponse;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.openchargemap.io/v3/poi/?output=json&latitude=-36.6582&longitude=174.7156&maxresults=10";

    private RecyclerView lRecyclerView;
    private RecyclerView.Adapter locationAdapter;
    private RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "You have already granted permission to location services", Toast.LENGTH_SHORT).show();
            GetLocation();
        } else {
            RequestPermission();
        }
        GetLocation();
        //ArrayList<LocationResponse> locationList = new ArrayList<>();
        //ArrayList<AddressInfo> addressList = new ArrayList<>();

        btnRequest = (Button) findViewById(R.id.buttonRequest);

        btnRequest.setOnClickListener(new View  .OnClickListener() {
            @Override
            public void onClick(View v){
                /*locationList = sendAndRequestResponse();
                locationAdapter = new LocationAdapter(locationList);*/
                OpenMapView();
            }
        });

        locationList = sendAndRequestResponse();
        ArrayList<LocationResponse> locationList = new ArrayList<>();
        //locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Add 1"));
        /*locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 2", "Add 2"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 3", "Add 3"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Add 1"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 2", "Add 2"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 3", "Add 3"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Add 1"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 2", "Add 2"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 3", "Add 3"));*/
        txtresponse = (TextView) findViewById(R.id.txtResponse);
        txtresponse.setText("Your current location: -36.6582, 174.7156");

        if (locationList.size() != 0) {
            lRecyclerView = findViewById(R.id.recLocation);
            lRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            //locationAdapter = new LocationAdapter(locationList);

            lRecyclerView.setLayoutManager(mLayoutManager);
            lRecyclerView.setAdapter(locationAdapter);
        } else {
            locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title ", "Address"));
            lRecyclerView = findViewById(R.id.recLocation);
            lRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            locationAdapter = new LocationAdapter(locationList);

            lRecyclerView.setLayoutManager(mLayoutManager);
            lRecyclerView.setAdapter(locationAdapter);
        }
        GetLocation();
    }

    private void OpenMapView(){
        Intent intent = new Intent(this, MapView.class);
        //intent.putExtra()
        startActivity(intent);
    }
    private ArrayList<LocationResponse> sendAndRequestResponse() {
        addressList = new ArrayList<>();
        final AddressInfo newAdd = null;
        final List<String> list = new ArrayList<>();
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonObj = new JSONArray(response);
                    Integer len = response.length();
                    for (int j = 0; j < jsonObj.length(); j++) {
                        Integer jlen = jsonObj.length();
                        JSONObject jaddressInfo = jsonObj.getJSONObject(j);
                        JSONObject ad = jaddressInfo.getJSONObject("AddressInfo");

                        Integer adLen = jaddressInfo.length();
                        String title = ad.getString("Title");
                        String addressline1 = ad.getString("AddressLine1");
                        String town = ad.getString("Town");
                        String lat = ad.getString("Latitude");
                        String lon = ad.getString("Longitude");
                        String dist = ad.getString("Distance");

                        HashMap<String, String> address = new HashMap<>();
                        address.put("Title", title);
                        address.put("AddressLine1", addressline1);
                        address.put("Town", town);
                        address.put("Latitude", lat);
                        address.put("Longitude", lon);
                        address.put("Distance", dist);

                        addressList.add(address);
                    }
                    for (int i = 0; i < addressList.size(); i++) {
                        HashMap<String, String> viewList = addressList.get(i);
                        locationList.add(new LocationResponse(R.drawable.ic_ev_station, viewList.get("Title"), viewList.get("AddressLine1")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LoadRecyclerView(locationList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

        return locationList;
    }

    public void LoadRecyclerView(ArrayList<LocationResponse> locationList) {
        lRecyclerView = findViewById(R.id.recLocation);
        lRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        locationAdapter = new LocationAdapter(locationList);

        lRecyclerView.setLayoutManager(mLayoutManager);
        lRecyclerView.setAdapter(locationAdapter);
    }

    public void RequestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Required")
                    .setMessage("Permission Required to Access Location")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PermissionCode);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PermissionCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*super.onRequestPermissionsResult(requestCode, permissions, grantResults);*/
        if (requestCode == PermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void GetLocation() {
        //String currentLat = null;
        fusedLocationProviderClient = new FusedLocationProviderClient(this);
        locationRequest = new LocationRequest();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(3000);
        locationRequest.setInterval(5000);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult == null) {
                    Log.d("Location Result", locationResult.toString());
                }

                String currentLat = Double.toString(locationResult.getLastLocation().getLatitude());
                String currentLon = Double.toString(locationResult.getLastLocation().getLongitude());
                txtresponse = (TextView) findViewById(R.id.txtResponse);

                txtresponse.setText("Your current location: "+currentLat+","+currentLon);

            }
        }, getMainLooper());
    }
}
