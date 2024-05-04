package com.example.myapplication.DB;

import com.example.myapplication.User.User;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.User.User;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "User_table";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_MOBILE = "mobileNo";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "livingCity";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_IMAGE = "image";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_NIC + " TEXT PRIMARY KEY," +
            COLUMN_NAME + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_MOBILE + " TEXT," +
            COLUMN_DOB + " TEXT," +
            COLUMN_ADDRESS + " TEXT," +
            COLUMN_CITY + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_IMAGE + " BLOB)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NIC, user.getNic());
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_MOBILE, user.getMobileNo());
        values.put(COLUMN_DOB, user.getDob());
        values.put(COLUMN_ADDRESS, user.getAddress());
        values.put(COLUMN_CITY, user.getLivingCity());
        values.put(COLUMN_GENDER, user.getGender());
        values.put(COLUMN_IMAGE, user.getImage());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }

    @SuppressLint("Range")
    public User getUserByNic(String nic) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, COLUMN_NIC + " = ?", new String[]{nic},
                null, null, null);
        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndex(COLUMN_NIC)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_DOB)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_CITY)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)),
                    cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE))
            );
            cursor.close();
        }
        db.close();
        return user;
    }



    // Get all users
    @SuppressLint("Range")
    public ArrayList<User> getAllUsers() {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {

                String nic = cursor.getString(cursor.getColumnIndex(COLUMN_NIC));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String mobileNo = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE));
                String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                String livingCity = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
               byte[] image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                userList.add(new User(nic, name, email, mobileNo, dob, address, livingCity, gender, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Get users by NIC
    public ArrayList<User> getUsersByNic(String nic) {
        ArrayList<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_NIC, COLUMN_NAME, COLUMN_EMAIL, COLUMN_MOBILE,
                COLUMN_DOB, COLUMN_ADDRESS, COLUMN_CITY, COLUMN_GENDER, COLUMN_IMAGE
        };
        String selection = COLUMN_NIC + " = ?";
        String[] selectionArgs = {nic};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                @SuppressLint("Range") String mobileNo = cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE));
                @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DOB));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                @SuppressLint("Range") String livingCity = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE));
                userList.add(new User(nic, name, email, mobileNo, dob, address, livingCity, gender, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    // Check if user exists by NIC and name
    public boolean checkUser(String nic, String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_NIC};
        String selection = COLUMN_NIC + " = ?" + " AND " + COLUMN_NAME + " = ?";
        String[] selectionArgs = {nic, name};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }

    @SuppressLint("Range")
    public String getNicByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String nic = null;
        String[] columns = {COLUMN_NIC};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            nic = cursor.getString(cursor.getColumnIndex(COLUMN_NIC));
            cursor.close();
        }
        db.close();
        return nic;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean emailExists = false;
        String[] columns = {COLUMN_EMAIL};
        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            emailExists = true;
            cursor.close();
        }
        db.close();
        return emailExists;
    }



}
