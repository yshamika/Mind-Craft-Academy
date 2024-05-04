package com.example.myapplication.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Admin.Admin_login;
import com.example.myapplication.DB.DatabaseHelper;
import com.example.myapplication.R;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button loginButton;
    String OTP;
    private DatabaseHelper databaseHelper;
    private boolean passwordShowing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);





            //get Couser details
            Intent i = getIntent();
            String courseName = i.getStringExtra("courseName");
            String cost = i.getStringExtra("cost");
            String maxNum = i.getStringExtra("maxNum");
            String check = i.getStringExtra("check");

            TextView adminLoginTextView = findViewById(R.id.Admin_login);
            TextView registerView = findViewById(R.id.registerButton);
            TextView loginView = findViewById(R.id.loginButton);
            TextView ToAdminView = findViewById(R.id.Admin_login);


            if(check.equals("1")){
                // Hide the Admin_login TextView
                adminLoginTextView.setVisibility(View.VISIBLE);
            }
            else{
                adminLoginTextView.setVisibility(View.INVISIBLE);
            }
            // Set click Admin
            ToAdminView.setOnClickListener(view -> {
                Intent intent = new Intent(LoginActivity.this, Admin_login.class);
                startActivity(intent);
            });

            // Set click listener for the TextView
            registerView.setOnClickListener(view -> {
                // Handle the click event here
                Intent intent = new Intent(LoginActivity.this, UserRegisterActivity.class);
                intent.putExtra("courseName", courseName);
                intent.putExtra("cost", cost);
                intent.putExtra("check", check);
                intent.putExtra("maxNum", maxNum);
                startActivity(intent);
            });


            // Set click listener for the TextView
            loginView.setOnClickListener(view -> {
                DatabaseHelper dbHelper = new DatabaseHelper(LoginActivity.this);

                emailEditText = findViewById(R.id.EmailEditText);
                String email = emailEditText.getText().toString();
                boolean emailExists = dbHelper.checkEmailExists(email);

                if (emailExists) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                emailEditText = findViewById(R.id.EmailEditText);
                                // Get the text from the EditText and convert it to a String
                                String email = emailEditText.getText().toString();
                                String stringSenderEmail = "shamikayesith47@gmail.com";
                                String stringReceiverEmail = email;
                                String stringPasswordSenderEmail = "gklc gfyn gmdr zecp";

                                String stringHost = "smtp.gmail.com";

                                Properties properties = System.getProperties();

                                properties.put("mail.smtp.host", stringHost);
                                properties.put("mail.smtp.port", "465");
                                properties.put("mail.smtp.ssl.enable", "true");
                                properties.put("mail.smtp.auth", "true");

                                Session session = Session.getInstance(properties, new Authenticator() {
                                    @Override
                                    protected PasswordAuthentication getPasswordAuthentication() {
                                        return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                                    }
                                });

                                // Generate a random 4-digit number
                                Random random = new Random();
                                int min = 100000; // Minimum 6-digit number
                                int max = 999999; // Maximum 6-digit number
                                int randomNumber = random.nextInt(max - min + 1) + min;

                                // Retrieve COLUMN_NIC based on the entered email
                                String nic = dbHelper.getNicByEmail(email);

                                //get Couser details
                                Intent i = getIntent();
                                String courseName = i.getStringExtra("courseName");
                                String cost = i.getStringExtra("cost");
                                String maxNum = i.getStringExtra("maxNum");
                                String check = i.getStringExtra("check");

                                emailEditText = findViewById(R.id.EmailEditText);

                                Intent intent = new Intent(LoginActivity.this, User_login_verifiy_Activity.class);
                                intent.putExtra("courseName", courseName);
                                intent.putExtra("cost", cost);
                                intent.putExtra("maxNum", maxNum);
                                intent.putExtra("email", email);
                                intent.putExtra("nic", nic);
                                intent.putExtra("check", check);
                                intent.putExtra("OTP", String.valueOf(randomNumber));

                                startActivity(intent);


                                MimeMessage mimeMessage = new MimeMessage(session);
                                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

                                mimeMessage.setSubject("Verify your email address");
                                mimeMessage.setText("Verify your email address  \n\n Here is your 6-character code to verify your email address:\n" +randomNumber+ "\n\nEnter the above come and verify your account");

                                Transport.send(mimeMessage);

                            } catch (AddressException e) {
                                e.printStackTrace();
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                    thread.start();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
                    emailEditText.setText("");

                }
            });






    }
}
