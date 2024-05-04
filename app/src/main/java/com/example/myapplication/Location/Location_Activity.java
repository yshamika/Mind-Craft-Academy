package com.example.myapplication.Location;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.LocationDatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Location_Activity extends AppCompatActivity {

    ListView locationListView;
    LocationDatabaseHelper databaseHelper;
    EditText nameEditText, latitudeEditText, longitudeEditText;
    Button addLocationButton, viewLocationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        databaseHelper = new LocationDatabaseHelper(this);
        locationListView = findViewById(R.id.location_list_view);
        nameEditText = findViewById(R.id.editTextName);
        latitudeEditText = findViewById(R.id.editTextLatitude);
        longitudeEditText = findViewById(R.id.editTextLongitude);
        addLocationButton = findViewById(R.id.buttonAddLocation);

        // Display existing locations
        displayLocations();

        // Add Location button click listener
        addLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });

        // Set a click listener for View Location
        locationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                @SuppressLint("Range") String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                @SuppressLint("Range") String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
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

    //Add a New Location
    private void addLocation() {
        String name = nameEditText.getText().toString().trim();
        String latitude = latitudeEditText.getText().toString().trim();
        String longitude = longitudeEditText.getText().toString().trim();

        //cheching is that value is empthy or not
        if (name.isEmpty() || latitude.isEmpty() || longitude.isEmpty()) {
            //make Erro massage
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("latitude", latitude);
        values.put("longitude", longitude);

        long newRowId = databaseHelper.getWritableDatabase().insert("locations", null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Location added successfully", Toast.LENGTH_SHORT).show();
            // Clear EditText fields after successful addition
            nameEditText.getText().clear();
            latitudeEditText.getText().clear();
            longitudeEditText.getText().clear();
            // Refresh the ListView with the updated data
            displayLocations();
        } else {
            Toast.makeText(this, "Error adding location", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the ListView when the activity is resumed
        displayLocations();
    }

    public void btn_main(View view) {
        Intent intent = new Intent(Location_Activity.this, MainActivity.class);
        startActivity(intent);
    }
}
