package com.example.chargeme;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.nio.channels.ClosedChannelException;
import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    private ArrayList<LocationResponse> mLocationList;
    public static class LocationViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView mIconView;
        public TextView mTitle;
        public TextView mAddress;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            mIconView = itemView.findViewById(R.id.icon);
            mTitle = itemView.findViewById(R.id.txtTitle);
            mAddress = itemView.findViewById(R.id.txtAddress);
        }

    }

    public LocationAdapter(ArrayList<LocationResponse> locationList){
        mLocationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View locationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent,false);
        LocationViewHolder lvh = new LocationViewHolder(locationView);
        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        LocationResponse currentItem = mLocationList.get(position);

        holder.mIconView.setImageResource(currentItem.getmIconResource());
        holder.mTitle.setText(currentItem.getTitle());
        holder.mAddress.setText(currentItem.getAddress());
    }

    @Override
    public int getItemCount() {
        return mLocationList.size();
    }
}
