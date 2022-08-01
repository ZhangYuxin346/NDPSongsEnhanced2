package com.myapplicationdev.android.ndpsongsenhanced;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;

    ArrayList<Songs> versionList;
    public Adapter(Context context, int resource, ArrayList<Songs> objects){
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        versionList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.tvSongs2);
        TextView tvYear = rowView.findViewById(R.id.tvYears2);
        TextView tvSinger = rowView.findViewById(R.id.tvSingers2);

        // Obtain the Android Version information based on the position
        Songs currentVersion = versionList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentVersion.getTitle());
        tvYear.setText(currentVersion.toString());
        tvSinger.setText(currentVersion.getSingers());

        return rowView;
    }
}