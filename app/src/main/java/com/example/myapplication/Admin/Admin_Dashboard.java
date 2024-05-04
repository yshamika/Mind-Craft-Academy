package com.example.myapplication.Admin;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Course.AddCourseActivity;
import com.example.myapplication.DB.DbHelper;
import com.example.myapplication.Location.Location_Activity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.User.UserViewActivity;
import com.example.myapplication.User.ViewPaymentsActivity;

import java.util.ArrayList;

public class  Admin_Dashboard extends AppCompatActivity {

    private ListView courseListView;
    private DbHelper dbHelper;
    private ArrayList<Long> courseIds;
    private ArrayList<String> courseNames;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        dbHelper = new DbHelper(this);
        courseListView = findViewById(R.id.courseListView);
        courseIds = new ArrayList<>();
        courseNames = new ArrayList<>();

        loadCourses();

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCourseName = courseNames.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Dashboard.this);
                builder.setTitle("Confirm Action");
                builder.setMessage("What would you like to do with this course?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder deleteConfirmBuilder = new AlertDialog.Builder(Admin_Dashboard.this);
                        deleteConfirmBuilder.setTitle("Confirm Delete");
                        deleteConfirmBuilder.setMessage("Are you sure you want to delete this course?");
                        deleteConfirmBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                long courseId = courseIds.get(position);
                                boolean deleted = dbHelper.deleteCourse(courseId);
                                if (deleted) {
                                    // Course deleted successfully
                                    courseNames.remove(position);
                                    courseIds.remove(position);
                                    adapter.notifyDataSetChanged();
                                    Intent intent = new Intent(Admin_Dashboard.this, Admin_Dashboard.class);
                                    startActivity(intent);
                                } else {
                                    // Failed to delete course
                                    Toast.makeText(Admin_Dashboard.this, "Failed to delete course", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        deleteConfirmBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Do nothing, user canceled deletion
                            }
                        });
                        AlertDialog deleteConfirmDialog = deleteConfirmBuilder.create();
                        deleteConfirmDialog.show();
                    }
                });
                builder.setNegativeButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // View action, you can add your view logic here
                        Toast.makeText(Admin_Dashboard.this, "loading", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Admin_Dashboard.this, ViewPaymentsActivity.class);
                        intent.putExtra("courseName", selectedCourseName);
                        startActivity(intent);
                    }
                });
                builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, user canceled action
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }


    @SuppressLint("Range")
    private void loadCourses() {
        Cursor cursor = dbHelper.getAllCourses();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    long id = cursor.getLong(cursor.getColumnIndex(DbHelper.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_C_NAME));
                    courseIds.add(id);
                    courseNames.add(name);
                } while (cursor.moveToNext());
            } else {
                Log.e("DeleteCourseActivity", "Cursor is empty");
                Toast.makeText(this, "No courses found", Toast.LENGTH_SHORT).show();
            }
            cursor.close();

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
            courseListView.setAdapter(adapter);
        } else {
            Log.e("DeleteCourseActivity", "Cursor is null");
            Toast.makeText(this, "Error loading courses", Toast.LENGTH_SHORT).show();
        }
    }

    private void filter(String text) {
        ArrayList<String> filteredList = new ArrayList<>();
        for (String name : courseNames) {
            if (name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(name);
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        courseListView.setAdapter(adapter);
    }
    public void SeeUsers(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, UserViewActivity.class);
        startActivity(intent);
    }
    public void Addcoures(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, AddCourseActivity.class);
        startActivity(intent);
    }


    public void btn_main(View view) {

        Intent intent = new Intent(Admin_Dashboard.this, MainActivity.class);
        startActivity(intent);
    }

    public void Location(View view) {
        Intent intent = new Intent(Admin_Dashboard.this, Location_Activity.class);
        startActivity(intent);
    }
}