package com.example.myapplication.Course;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DbHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

public class ViewAllCourseNamesActivity extends AppCompatActivity {

    private ListView courseNamesListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> courseNamesList;
    private Cursor cursor;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_course_names);

        courseNamesListView = findViewById(R.id.courseNamesListView);
        searchEditText = findViewById(R.id.searchEditText);

        courseNamesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNamesList);
        courseNamesListView.setAdapter(adapter);

        displayAllCourseNames();

        // Set click listener for course names ListView
        courseNamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCourseName = courseNamesList.get(position);

                // Start ViewCourseDetailsActivity and pass the selected course name
                Intent intent = new Intent(ViewAllCourseNamesActivity.this, ViewCourseDetailsActivity.class);
                intent.putExtra("courseName", selectedCourseName);
                startActivity(intent);
            }
        });

        // Add text change listener to EditText for searching
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String query = charSequence.toString();
                if (query.isEmpty()) {
                    displayAllCourseNames();
                } else {
                    filterCourseNames(query);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void displayAllCourseNames() {
        // Initialize DbHelper
        DbHelper dbHelper = new DbHelper(this);

        // Get readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define columns you want to retrieve
        String[] projection = {DbHelper.COLUMN_C_NAME};

        // Query the database to get all course names
        cursor = db.query(
                dbHelper.getTableName(),   // Use the public method to retrieve the table name
                projection,            // The array of columns to return (null means all)
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // don't group the rows
                null,                  // don't filter by row groups
                null                   // The sort order
        );

        // Extract course names from the cursor
        courseNamesList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String courseName = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_C_NAME));
                courseNamesList.add(courseName);
            } while (cursor.moveToNext());
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }

        // Notify the adapter that the dataset has changed
        adapter.notifyDataSetChanged();
    }

    private void filterCourseNames(String query) {
        ArrayList<String> filteredCourseNames = new ArrayList<>();
        for (String courseName : courseNamesList) {
            if (courseName.toLowerCase().contains(query.toLowerCase())) {
                filteredCourseNames.add(courseName);
            }
        }
        adapter.clear();
        adapter.addAll(filteredCourseNames);
        adapter.notifyDataSetChanged();
    }
}
