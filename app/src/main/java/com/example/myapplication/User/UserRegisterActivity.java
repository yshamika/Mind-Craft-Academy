package com.example.myapplication.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DB.DatabaseHelper;
import com.example.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UserRegisterActivity extends AppCompatActivity {

    EditText nicEditText, nameEditText, emailEditText, mobileEditText, dobEditText, addressEditText,
            cityEditText, genderEditText, searchNicEditText;
    Button loginButton, registerButton;
    ImageView addImageButton;
    ImageView imageView;
    DatabaseHelper databaseHelper;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//get Couser details


        setContentView(R.layout.activity_user_register);


        nicEditText = findViewById(R.id.nicEditText);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        dobEditText = findViewById(R.id.dobEditText);
        addressEditText = findViewById(R.id.addressEditText);
        cityEditText = findViewById(R.id.cityEditText);
        genderEditText = findViewById(R.id.genderEditText);
        registerButton = findViewById(R.id.registerButton);
        addImageButton = findViewById(R.id.imageView);
        imageView = findViewById(R.id.imageView);
        databaseHelper = new DatabaseHelper(this);

        addImageButton.setOnClickListener(v -> selectImage());

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } else if (options[item].equals("Choose from Gallery")) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                imageView.setImageBitmap(imageBitmap);
            }
        }
    }

    private byte[] getImageBytes() {
        if (imageUri != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                return stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void registerUser() {
        String nic = nicEditText.getText().toString().trim();
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String mobile = mobileEditText.getText().toString().trim();
        String dob = dobEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String city = cityEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();

        byte[] imageBytes = getImageBytes();

        DatabaseHelper dbHelper = new DatabaseHelper(UserRegisterActivity.this);

        boolean emailExists = dbHelper.checkEmailExists(email);

        if (emailExists) {
            Toast.makeText(UserRegisterActivity.this, "This Email is Already Exists", Toast.LENGTH_SHORT).show();
        }
        else {
            if (nic.equals("") || name.equals("") || dob.equals("")) {
                Toast.makeText(UserRegisterActivity.this, "Please enter NIC, Name, and DOB", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(nic, name, email, mobile, dob, address, city, gender, imageBytes);
            long result = databaseHelper.addUser(user);
            if (result > 0) {


                if (TextUtils.isEmpty(nic) || TextUtils.isEmpty(name)) {
                    Toast.makeText(UserRegisterActivity.this, "Please enter NIC and name", Toast.LENGTH_SHORT).show();
                } else {

                    if (databaseHelper.checkUser(nic, name)) {
                        Intent intentCheck = getIntent();
                        String check = intentCheck.getStringExtra("check");

                        // send Email

                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                try {
                                    String stringSenderEmail = "shamikayesith47@gmail.com";
                                    String stringReceiverEmail = email;
                                    String stringPasswordSenderEmail = "gklc gfyn gmdr zecp";

                                    String stringHost = "smtp.gmail.com";

                                    Properties properties = System.getProperties();

                                    properties.put("mail.smtp.host", stringHost);
                                    properties.put("mail.smtp.port", "465");
                                    properties.put("mail.smtp.ssl.enable", "true");
                                    properties.put("mail.smtp.auth", "true");

                                    javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                                        @Override
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                                        }
                                    });

                                    MimeMessage mimeMessage = new MimeMessage(session);
                                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

                                    mimeMessage.setSubject("MindCraft Academy");
                                    mimeMessage.setText("Welcome to MindCraft Academy,\n\n Success full register \n Use :"+email+ "\n to login \n\n Thank you choosing us..!");

                                    Transport.send(mimeMessage);

                                } catch (AddressException e) {
                                    e.printStackTrace();
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        thread.start();

                        ////////////////////

                        if(check.equals("1")){
                            Intent intent = new Intent(UserRegisterActivity.this, User_Dashboard.class);
                            intent.putExtra("nic", nic);
                            startActivity(intent);
                        }
                        else {
                            Intent i = getIntent();
                            String courseName = i.getStringExtra("courseName");
                            String cost = i.getStringExtra("cost");

                            String maxNum = i.getStringExtra("maxNum");

                            // Set empty text for each EditText
                            nicEditText.setText("");
                            nameEditText.setText("");
                            emailEditText.setText("");
                            mobileEditText.setText("");
                            dobEditText.setText("");
                            addressEditText.setText("");
                            cityEditText.setText("");
                            genderEditText.setText("");

                            Intent intent = new Intent(UserRegisterActivity.this, AddPaymentActivity.class);
                            intent.putExtra("courseName", courseName);
                            intent.putExtra("cost", cost);
                            intent.putExtra("maxNum", maxNum);
                            intent.putExtra("nic", nic);
                            intent.putExtra("check", check);
                            startActivity(intent);
                        }




                    } else {
                        Toast.makeText(UserRegisterActivity.this, "Invalid NIC or name", Toast.LENGTH_SHORT).show();
                    }
                }


            }
            else {
                Toast.makeText(UserRegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
            }
        }




    }


    public void onSignUpButtonClick(View view) {
        Intent intentCheck = getIntent();
        String check = intentCheck.getStringExtra("check");

        if(check.equals("1")){
            Intent intent = new Intent(UserRegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            Intent i = getIntent();
            String courseName = i.getStringExtra("courseName");
            String cost = i.getStringExtra("cost");
            String maxNum = i.getStringExtra("maxNum");

            // Handle the click event here
            Intent intent = new Intent(UserRegisterActivity.this, LoginActivity.class);
            intent.putExtra("courseName", courseName);
            intent.putExtra("cost", cost);
            intent.putExtra("maxNum", maxNum);
            intent.putExtra("check", check);
            startActivity(intent);
        }
    }
}
