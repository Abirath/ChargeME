package com.example.chargeme;

import java.util.ArrayList;
import java.util.List;

public class AddressInfo {
    private String Title;
    private String AddressLine1;
    //private String Town;
    private String Latitude;
    private String Longitude;
    private List<AddressInfo> Address;

    public AddressInfo(String title, String addressLine1, String latitude, String longitude)
    {// String town,
        this.Title = title;
        this.AddressLine1 = addressLine1;
       // this.Town = town;
        this.Latitude = latitude;
        this.Longitude = longitude;

        this.Address =new ArrayList<>();
    }

    public List<AddressInfo> getAddress(){return Address;}

}
