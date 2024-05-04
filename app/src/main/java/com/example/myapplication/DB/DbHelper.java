package com.example.myapplication.DB;
//user for coures

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Course.Course;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Course_DB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_C_NAME = "Courses";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_C_NAME = "C_name"; // Change access to public
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_REGI_END_DATE = "regi_end_date";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_MAX_NUM = "max_num";
    public static final String COLUMN_LOCATIONS = "locations";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_C_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_C_NAME + " TEXT,"
                + COLUMN_COST + " TEXT,"
                + COLUMN_START_DATE + " TEXT,"
                + COLUMN_REGI_END_DATE + " TEXT,"
                + COLUMN_DURATION + " TEXT,"
                + COLUMN_MAX_NUM + " TEXT,"
                + COLUMN_LOCATIONS + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_C_NAME);
        onCreate(db);
    }


    // Add course
    public long addCourse(String C_name, String cost, String startDate, String regiEndDate,
                          String duration, String maxNum, String locations) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_C_NAME, C_name);
        values.put(COLUMN_COST, cost);
        values.put(COLUMN_START_DATE, startDate);
        values.put(COLUMN_REGI_END_DATE, regiEndDate);
        values.put(COLUMN_DURATION, duration);
        values.put(COLUMN_MAX_NUM, maxNum);
        values.put(COLUMN_LOCATIONS, locations);
        long id = db.insert(TABLE_C_NAME, null, values);
        db.close();
        return id;
    }

    //delete course
    public boolean deleteCourse(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int affectedRows = db.delete(TABLE_C_NAME, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return affectedRows > 0;
    }

    public Cursor getAllCourses() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_C_NAME, null, null, null, null, null, null);
    }



    // Public method to retrieve the table name
    public String getTableName() {
        return TABLE_C_NAME;
    }

}
