package com.example.myapplication.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Admin.Admin_Dashboard;
import com.example.myapplication.DB.DatabaseHelper;
import com.example.myapplication.R;

import java.util.ArrayList;

public class UserViewActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private DatabaseHelper databaseHelper;
    private EditText searchNicEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        searchNicEditText = findViewById(R.id.searchNicEditText);


        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this);
        recyclerViewUsers.setAdapter(userAdapter);

        databaseHelper = new DatabaseHelper(this);
        ArrayList<User> userList = databaseHelper.getAllUsers();
        userAdapter.setUserList(userList);

        searchNicEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchNic = s.toString().trim();
                if (!TextUtils.isEmpty(searchNic)) {
                    ArrayList<User> filteredList = databaseHelper.getUsersByNic(searchNic);
                    userAdapter.setUserList(filteredList);
                } else {
                    ArrayList<User> allUsers = databaseHelper.getAllUsers();
                    userAdapter.setUserList(allUsers);
                }
            }
        });
    }

    public void btn_main(View view) {
        Intent intent = new Intent(UserViewActivity.this, Admin_Dashboard.class);
        startActivity(intent);
    }
}
