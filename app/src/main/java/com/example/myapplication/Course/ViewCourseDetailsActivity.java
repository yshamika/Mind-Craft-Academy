package com.example.myapplication.Course;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DbHelper;
import com.example.myapplication.R;
import com.example.myapplication.User.LoginActivity;

public class ViewCourseDetailsActivity extends AppCompatActivity {

    private TextView courseNameTextView,courseCost,courseDuration,courseStartDate,courseRegiEndDate,courseLocations;
    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_course_details);

        courseNameTextView = findViewById(R.id.courseNameTextView);
        courseCost = findViewById(R.id.courseCost);
        courseDuration = findViewById(R.id.courseDuration);
        courseStartDate = findViewById(R.id.courseStartDate);
        courseLocations = findViewById(R.id.courseLocations);


        // Get the selected course name from the intent
        courseName = getIntent().getStringExtra("courseName");

        // Display details of the selected course
        displayCourseDetails();
    }

    private void displayCourseDetails() {
        // Initialize DbHelper
        DbHelper dbHelper = new DbHelper(this);

        // Get readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define columns you want to retrieve
        String[] projection = {
                DbHelper.COLUMN_ID,
                DbHelper.COLUMN_C_NAME,
                DbHelper.COLUMN_COST,
                DbHelper.COLUMN_START_DATE,
                DbHelper.COLUMN_REGI_END_DATE,
                DbHelper.COLUMN_DURATION,
                DbHelper.COLUMN_MAX_NUM,
                DbHelper.COLUMN_LOCATIONS
        };

        // Define selection and selectionArgs for the WHERE clause
        String selection = DbHelper.COLUMN_C_NAME + " = ?";
        String[] selectionArgs = {courseName};

        // Query the database to get details of the selected course
        Cursor cursor = db.query(
                dbHelper.getTableName(),   // Use the public method to retrieve the table name
                projection,            // The array of columns to return (null means all)
                selection,             // The columns for the WHERE clause
                selectionArgs,         // The values for the WHERE clause
                null,                  // don't group the rows
                null,                  // don't filter by row groups
                null                   // The sort order
        );


        // Display course details
        if (cursor != null && cursor.moveToFirst()) {
            long courseId = cursor.getLong(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_C_NAME));
            String cost = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COST));
            String startDate = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_START_DATE));
            String regiEndDate = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_REGI_END_DATE));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_DURATION));
            String maxNum = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MAX_NUM));
            String locations = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_LOCATIONS));

            // Construct course details string
            StringBuilder courseName = new StringBuilder();
            courseName.append(name);

            StringBuilder courseCostB = new StringBuilder();
            courseCostB.append("Cost :").append(cost);

            StringBuilder courseDurationB = new StringBuilder();
            courseDurationB.append("Duration :").append(duration).append(" month");

            StringBuilder courseStartDateB = new StringBuilder();
            courseStartDateB.append("StartDate :").append(startDate);

            StringBuilder courseLocationsB = new StringBuilder();
            courseLocationsB.append("Location :").append(locations);

            // Set the course details in TextView

            courseNameTextView.setText(courseName.toString());
            courseCost.setText(courseCostB.toString());
            courseDuration.setText(courseDurationB.toString());
            courseStartDate.setText(courseStartDateB.toString());
            courseLocations.setText(courseLocationsB.toString());
        }


        // Close the cursor
        if (cursor != null)
            cursor.close();
        }

/*send data to UserDashbord     */
    public void registerBtn(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        // Get the cost and max number of students from the database
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                DbHelper.COLUMN_COST,
                DbHelper.COLUMN_MAX_NUM
        };

        String selection = DbHelper.COLUMN_C_NAME + " = ?";
        String[] selectionArgs = {courseName};

        Cursor cursor = db.query(
                dbHelper.getTableName(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Extract cost and maxNum from the cursor
        String cost = "";
        String maxNum = "";
        if (cursor != null && cursor.moveToFirst()) {
            cost = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_COST));
            maxNum = cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.COLUMN_MAX_NUM));
            cursor.close();
        }

        // Put courseName, cost, and maxNum as extras in the Intent
        intent.putExtra("courseName", courseName);
        intent.putExtra("cost", cost);
        intent.putExtra("check", "0");
        intent.putExtra("maxNum", maxNum);

        // Start the User_DashboardActivity with the Intent
        startActivity(intent);
    }



}
