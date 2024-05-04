package com.example.myapplication.Location;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class LocationCursorAdapter extends CursorAdapter {

    public LocationCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for each row
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Set the name and coordinates in the TextViews
        TextView nameTextView = view.findViewById(android.R.id.text1);
        TextView coordinatesTextView = view.findViewById(android.R.id.text2);

        String name = cursor.getString(cursor.getColumnIndex("name"));

        nameTextView.setText(name);
        coordinatesTextView.setText("View Branch");
    }
}
