package com.example.myapplication.Course;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.Admin_Dashboard;
import com.example.myapplication.DB.DbHelper;
import com.example.myapplication.R;

import java.util.Calendar;

public class AddCourseActivity extends AppCompatActivity {

    private EditText C_nameEditText, costEditText, startDateEditText, regiEndDateEditText,
            durationEditText, maxNumEditText, locationsEditText;
    private Button addButton;
    private DbHelper dbHelper;
    private EditText CstartDateEditText;
    private EditText CregiEndDateEditText;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        dbHelper = new DbHelper(this);

        C_nameEditText = findViewById(R.id.C_nameEditText);
        costEditText = findViewById(R.id.costEditText);
        startDateEditText = findViewById(R.id.startDateEditText);
        regiEndDateEditText = findViewById(R.id.regiEndDateEditText);
        durationEditText = findViewById(R.id.durationEditText);
        maxNumEditText = findViewById(R.id.maxNumEditText);
        locationsEditText = findViewById(R.id.locationsEditText);
        addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String C_name = C_nameEditText.getText().toString();
                String cost = costEditText.getText().toString();
                String startDate = startDateEditText.getText().toString();
                String regiEndDate = regiEndDateEditText.getText().toString();
                String duration = durationEditText.getText().toString();
                String maxNum = maxNumEditText.getText().toString();
                String locations = locationsEditText.getText().toString();

                if (C_name.equals("") || cost.equals("") || startDate.equals("") ||
                        regiEndDate.equals("") || duration.equals("") || maxNum.equals("") ||locations.equals("")) {
                    Toast.makeText(AddCourseActivity.this, "Please Fill all colloms", Toast.LENGTH_SHORT).show();
                    return;
                }

                long id = dbHelper.addCourse(C_name, cost, startDate, regiEndDate, duration, maxNum, locations);
                if (id != -1) {
                    // Course added successfully

                    // Set empty text for each EditText
                    C_nameEditText.setText("");
                    costEditText.setText("");
                    startDateEditText.setText("");
                    regiEndDateEditText.setText("");
                    durationEditText.setText("");
                    maxNumEditText.setText("");
                    locationsEditText.setText("");
                    Intent intent = new Intent(AddCourseActivity.this, Admin_Dashboard.class);
                    startActivity(intent);

                    Toast.makeText(AddCourseActivity.this, "Add Course Successful", Toast.LENGTH_SHORT).show();

                } else {
                    // Failed to add course
                    Toast.makeText(AddCourseActivity.this, "Failed to add course", Toast.LENGTH_SHORT).show();
                    // You can add further error handling here
                }
            }
        });




        CregiEndDateEditText  = findViewById(R.id.regiEndDateEditText);
        CstartDateEditText = findViewById(R.id.startDateEditText);

        CregiEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }
        });
        CstartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        calendar = Calendar.getInstance();
    }
    private void showDatePickerDialog() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the EditText
                        startDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
    private void showDatePickerDialog2() {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Set the selected date to the EditText
                        regiEndDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
