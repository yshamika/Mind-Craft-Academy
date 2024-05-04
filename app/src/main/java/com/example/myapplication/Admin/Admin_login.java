package com.example.myapplication.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DatabaseHelper;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Admin_login extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText nameEditText;
    private Button loginButton;
    private boolean passwordShowing = false;

    private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        nameEditText = findViewById(R.id.nameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        databaseHelper = new DatabaseHelper(this);

        TextView userView = findViewById(R.id.userView);

        final ImageView passwordIcon = findViewById(R.id.passwordIcon);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();

                if (TextUtils.isEmpty(pass) || TextUtils.isEmpty(name)) {
                    Toast.makeText(Admin_login.this, "Please enter User and Password", Toast.LENGTH_SHORT).show();
                } else {

                    if (name.equals("Admin")&&pass.equals("1234")) {
                        Intent intent = new Intent(Admin_login.this, Admin_Dashboard.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Admin_login.this, "Invalid NIC or name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Set click listener for the TextView
        userView.setOnClickListener(view -> {
            // Handle the click event here
            Intent intent = new Intent(Admin_login.this, MainActivity.class);
            startActivity(intent);
        });


        ///password icon
        passwordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // password show and not show ====================================================
                if(passwordShowing){
                    passwordShowing = false;

                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_off);
                }
                else {
                    passwordShowing = true;

                    passwordEditText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordIcon.setImageResource(R.drawable.password_show);
                }
                nameEditText.setSelection(nameEditText.length());
            }
        });

    }
}