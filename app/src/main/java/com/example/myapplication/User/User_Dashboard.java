package com.example.myapplication.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DB.DatabaseHelper;
import com.example.myapplication.DB.PaymentDBHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;

public class User_Dashboard extends AppCompatActivity {
    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private DatabaseHelper databaseHelper;

    private TextView paymentDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);



        TextView txtWelcome= findViewById(R.id.txtWellcome);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        paymentDetailsTextView = findViewById(R.id.payment_details_text_view);

        Intent i = getIntent();
        String getuserName = i.getStringExtra("nic"); // Add this line to retrieve the userName
        txtWelcome.setText("Welcome " + getuserName);
        //to user details +++++++++++++++++++++++++++++++++++++++++++++++=

        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        recyclerViewUsers.setAdapter(userAdapter);

        databaseHelper = new DatabaseHelper(this);
        ArrayList<User> userList = databaseHelper.getAllUsers();
        userAdapter.setUserList(userList);

                if (!TextUtils.isEmpty(getuserName)) {
                    ArrayList<User> filteredList = databaseHelper.getUsersByNic(getuserName);
                    userAdapter.setUserList(filteredList);
                } else {
                    ArrayList<User> allUsers = databaseHelper.getAllUsers();
                    userAdapter.setUserList(allUsers);
                }






     //to coure details +++++++++++++++++++++++++++++++++++++++++++++++=

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
        String selection = "UserName = ?"; // Update the selection criteria
        String[] selectionArgs = { getuserName }; // Update the selectionArgs

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
            paymentDetails.append("Cost: ").append(cost).append("\n \n");
        }


        cursor.close();
        db.close();

        // Display the payment details
        paymentDetailsTextView.setText(paymentDetails.toString());
    }

    public void btn_main(View view) {
        Intent intent = new Intent(User_Dashboard.this, MainActivity.class);
        startActivity(intent);
    }
}