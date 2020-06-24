package com.example.chargeme;

public class LocationResponse {
    private int mIconResource;
    private String Title;
    private String Address;

    public LocationResponse(int iconResource, String title, String address) {
        mIconResource = iconResource;
        Title = title;
        Address = address;
    }

    public int getmIconResource()
    {
        return mIconResource;
    }
    public String getTitle()
    {
        return Title;
    }
    public String getAddress()
    {
        return Address;
    }
}