package com.example.chargeme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

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
    private TextView txtresponse;
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private String url = "https://api.openchargemap.io/v3/poi/?output=json&latitude=-36.6582&longitude=174.7156&maxresults=4";

    private RecyclerView lRecyclerView;
    private RecyclerView.Adapter locationAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<LocationResponse> locationList = new ArrayList<>();
        //ArrayList<AddressInfo> addressList = new ArrayList<>();
        btnRequest = (Button) findViewById(R.id.buttonRequest);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                sendAndRequestResponse();
            }
        });
        /*int Size = addList.size();
        for (int i = 0; i < addList.size(); i++)
        {

        }*/
        // ArrayList<LocationResponse> locationList = new ArrayList<>();
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Add 1"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 2", "Add 2"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 3", "Add 3"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Add 1"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 2", "Add 2"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 3", "Add 3"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 1", "Add 1"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 2", "Add 2"));
        locationList.add(new LocationResponse(R.drawable.ic_ev_station, "Title 3", "Add 3"));

        lRecyclerView = findViewById(R.id.recLocation);
        lRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        locationAdapter = new LocationAdapter(locationList);

        lRecyclerView.setLayoutManager(mLayoutManager);
        lRecyclerView.setAdapter(locationAdapter);
    }

    private AddressInfo sendAndRequestResponse() {
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
                    for (int j=0; j < jsonObj.length(); j++){
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

                            newAdd.getAddress().add(new AddressInfo(title,addressline1,lat,lon));
                            //list.add(ad.getString("Title"));

                            HashMap<String, String> address = new HashMap<>();
                            address.put("Title",title);
                            address.put("AddressLine1",addressline1);
                            address.put("Town", town);
                            address.put("Latitude",lat);
                            address.put("Longitude", lon);
                            address.put("Distance", dist);

                            addressList.add(address);
                        //addressInfoArrayList.add(new AddressInfo(title,addressline1,lat,lon));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //txtresponse = (TextView) findViewById(R.id.txtResponse);
                //txtresponse.setText(addressList.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);

        //return addressInfoArrayList;
        return newAdd;
        //return addressList;
    }

}
