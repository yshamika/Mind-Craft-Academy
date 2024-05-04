package com.example.myapplication.Course;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DbHelper;
import com.example.myapplication.R;

import java.util.ArrayList;


public class DeleteCourseActivity extends AppCompatActivity {

    private ListView courseListView;
    private DbHelper dbHelper;
    private ArrayList<Long> courseIds;
    private ArrayList<String> courseNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        dbHelper = new DbHelper(this);
        courseListView = findViewById(R.id.courseListView);
        courseIds = new ArrayList<>();
        courseNames = new ArrayList<>();

        loadCourses();

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteCourseActivity.this);
                builder.setTitle("Confirm Delete");
                builder.setMessage("Are you sure you want to delete this course?");

                //if click yes
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long courseId = courseIds.get(position);
                        boolean deleted = dbHelper.deleteCourse(courseId);
                        if (deleted) {
                            // Course deleted successfully
                            courseNames.remove(position);
                            courseIds.remove(position);
                            ((ArrayAdapter<String>) courseListView.getAdapter()).notifyDataSetChanged();
                        } else {
                            // Failed to delete course
                            Toast.makeText(DeleteCourseActivity.this, "Failed to delete course", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //if click no
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing, user canceled deletion
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

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
            courseListView.setAdapter(adapter);
        } else {
            Log.e("DeleteCourseActivity", "Cursor is null");
            Toast.makeText(this, "Error loading courses", Toast.LENGTH_SHORT).show();
        }
    }
}
