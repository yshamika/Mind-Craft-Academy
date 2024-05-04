package com.example.myapplication.DB;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PaymentDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "payment_DB.db";
    private static final int DATABASE_VERSION = 1;

    public PaymentDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE payments (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "cost REAL, CourseName TEXT, UserName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Upgrade logic here
    }

    // Method to add a payment
    public long addPayment(double cost, String courseName, String userName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("cost", cost);
        values.put("CourseName", courseName);
        values.put("UserName", userName);
        long newRowId = db.insert("payments", null, values);
        db.close();
        return newRowId;
    }



}
