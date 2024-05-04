package com.example.myapplication.Location;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.LocationDatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Display_Location_Activity extends AppCompatActivity {

    ListView locationListView;
    LocationDatabaseHelper databaseHelper;
    EditText nameEditText, latitudeEditText, longitudeEditText;
    Button addLocationButton, viewLocationsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_location);

        databaseHelper = new LocationDatabaseHelper(this);
        locationListView = findViewById(R.id.location_list_view);

        // Display existing locations
        displayLocations();


        // Set a click listener for the ListView items

        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("Range")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                 String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                openLocationInMap(latitude, longitude);
            }
        });
    }





    private void displayLocations() {
        Cursor cursor = databaseHelper.getAllLocations();
        // Create an adapter with the cursor data
        LocationCursorAdapter adapter = new LocationCursorAdapter(this, cursor);
        locationListView.setAdapter(adapter);
    }
    private void openLocationInMap(String latitude, String longitude) {
        Uri locationUri = Uri.parse("geo:" + latitude + "," + longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, locationUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            mapIntent.setData(Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude));
            startActivity(mapIntent);
        }
    }


    public void btn_main(View view) {
        Intent intent = new Intent(Display_Location_Activity.this, MainActivity.class);
        startActivity(intent);
    }
}