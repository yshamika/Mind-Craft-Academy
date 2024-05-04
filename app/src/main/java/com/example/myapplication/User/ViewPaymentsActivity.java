package com.example.myapplication.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.PaymentDBHelper;
import com.example.myapplication.R;

public class ViewPaymentsActivity extends AppCompatActivity {

    private TextView paymentDetailsTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_payments);


        paymentDetailsTextView = findViewById(R.id.payment_details_text_view);

        Intent i = getIntent();
        String getcourseName = i.getStringExtra("courseName");



        // Initialize database helper
        PaymentDBHelper dbHelper = new PaymentDBHelper(this);

        // Get readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define columns you want to retrieve
        String[] projection = {
                "_id",
                "cost",
                "CourseName",
                "UserName"
        };

        // Define selection criteria
        String selection = "CourseName = ?";
        String[] selectionArgs = { getcourseName };

        // Query the database
        Cursor cursor = db.query(
                "payments",   // The table to query
                projection,   // The columns to return
                selection,    // The columns for the WHERE clause
                selectionArgs,// The values for the WHERE clause
                null,         // don't group the rows
                null,         // don't filter by row groups
                null          // The sort order
        );

        // Create a StringBuilder to hold the results
        StringBuilder paymentDetails = new StringBuilder();

        // Iterate through the cursor to get results
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            double cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"));
            String courseName = cursor.getString(cursor.getColumnIndexOrThrow("CourseName"));
            String userName = cursor.getString(cursor.getColumnIndexOrThrow("UserName"));

            // Append details to StringBuilder
            paymentDetails.append("Course Name: ").append(courseName).append("\n");
            paymentDetails.append("User NIC: ").append(userName).append("\n");
            paymentDetails.append("Cost: ").append(cost).append("\n\n");

        }

        // Close cursor and database
        cursor.close();
        db.close();

        // Display the payment details
        paymentDetailsTextView.setText(paymentDetails.toString());
    }
}