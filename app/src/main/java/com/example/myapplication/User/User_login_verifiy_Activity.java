package com.example.myapplication.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class User_login_verifiy_Activity extends AppCompatActivity {

    private EditText inputCode1,inputCode2,inputCode3,inputCode4,inputCode5,inputCode6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_verifiy);

        String check="0";
        Intent intentCheck = getIntent();
        check= intentCheck.getStringExtra("check");

        if(check.equals("1")){
            Intent i = getIntent();
            String nic = i.getStringExtra("nic");
            String email = i.getStringExtra("email");
            String OTP = i.getStringExtra("OTP");




            TextView textEmail = findViewById(R.id.textEmail);
            textEmail.setText(""+email);
            //////////////////////////   Email eka dann methanata

            inputCode1 = findViewById(R.id.inputCode1);
            inputCode2 = findViewById(R.id.inputCode2);
            inputCode3 = findViewById(R.id.inputCode3);
            inputCode4 = findViewById(R.id.inputCode4);
            inputCode5 = findViewById(R.id.inputCode5);
            inputCode6 = findViewById(R.id.inputCode6);

            setupOTPInputs();

            final ProgressBar progressBar  = findViewById(R.id.progressBar);
            final Button buttonVerify = findViewById(R.id.buttonVerify);


            buttonVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);
                    buttonVerify.setVisibility(View.INVISIBLE);


                    if(inputCode1.getText().toString().trim().isEmpty()
                            ||inputCode2.getText().toString().trim().isEmpty()
                            ||inputCode3.getText().toString().trim().isEmpty()
                            ||inputCode4.getText().toString().trim().isEmpty()
                            ||inputCode5.getText().toString().trim().isEmpty()
                            ||inputCode6.getText().toString().trim().isEmpty()){
                        Toast.makeText(User_login_verifiy_Activity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonVerify.setVisibility(View.VISIBLE);
                        return;
                    }


                    String code =
                            inputCode1.getText().toString() +
                                    inputCode2.getText().toString() +
                                    inputCode3.getText().toString() +
                                    inputCode4.getText().toString() +
                                    inputCode5.getText().toString() +
                                    inputCode6.getText().toString() ;

                    int intCode=Integer.parseInt(code);
                    int intOTP=Integer.parseInt(OTP);
                    if (intCode == intOTP){

                        Intent intent = new Intent(User_login_verifiy_Activity.this, User_Dashboard.class);
                        intent.putExtra("nic", nic);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(User_login_verifiy_Activity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonVerify.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


        else{
            Intent i = getIntent();
            String courseName = i.getStringExtra("courseName");
            String cost = i.getStringExtra("cost");
            String maxNum = i.getStringExtra("maxNum");
            String nic = i.getStringExtra("nic");
            String email = i.getStringExtra("email");
            String OTP = i.getStringExtra("OTP");




            TextView textEmail = findViewById(R.id.textEmail);
            textEmail.setText(""+email);
            //////////////////////////   Email eka dann methanata

            inputCode1 = findViewById(R.id.inputCode1);
            inputCode2 = findViewById(R.id.inputCode2);
            inputCode3 = findViewById(R.id.inputCode3);
            inputCode4 = findViewById(R.id.inputCode4);
            inputCode5 = findViewById(R.id.inputCode5);
            inputCode6 = findViewById(R.id.inputCode6);

            setupOTPInputs();

            final ProgressBar progressBar  = findViewById(R.id.progressBar);
            final Button buttonVerify = findViewById(R.id.buttonVerify);


            buttonVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar.setVisibility(View.VISIBLE);
                    buttonVerify.setVisibility(View.INVISIBLE);


                    if(inputCode1.getText().toString().trim().isEmpty()
                            ||inputCode2.getText().toString().trim().isEmpty()
                            ||inputCode3.getText().toString().trim().isEmpty()
                            ||inputCode4.getText().toString().trim().isEmpty()
                            ||inputCode5.getText().toString().trim().isEmpty()
                            ||inputCode6.getText().toString().trim().isEmpty()){
                        Toast.makeText(User_login_verifiy_Activity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonVerify.setVisibility(View.VISIBLE);
                        return;
                    }


                    String code =
                            inputCode1.getText().toString() +
                                    inputCode2.getText().toString() +
                                    inputCode3.getText().toString() +
                                    inputCode4.getText().toString() +
                                    inputCode5.getText().toString() +
                                    inputCode6.getText().toString() ;

                    int intCode=Integer.parseInt(code);
                    int intOTP=Integer.parseInt(OTP);
                    if (intCode == intOTP){

                        Intent intent = new Intent(User_login_verifiy_Activity.this, AddPaymentActivity.class);
                        intent.putExtra("courseName", courseName);
                        intent.putExtra("cost", cost);
                        intent.putExtra("maxNum", maxNum);
                        intent.putExtra("nic", nic);
                        intent.putExtra("email", email);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(User_login_verifiy_Activity.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        buttonVerify.setVisibility(View.VISIBLE);
                    }
                }
            });

        }



    }

    private void setupOTPInputs(){
        inputCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        inputCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    inputCode6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void Resend(View view) {
        Intent intent = new Intent(User_login_verifiy_Activity.this, LoginActivity.class);
        startActivity(intent);
    }
}



