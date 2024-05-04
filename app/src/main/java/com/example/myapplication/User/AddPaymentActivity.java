package com.example.myapplication.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import  com.example.myapplication.DB.PaymentDBHelper;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.MainActivity;
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


public class AddPaymentActivity extends AppCompatActivity {
    private EditText editTextCost;
    private EditText editTextCourseName;
    private EditText editTextUserName;
    private PaymentDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        dbHelper = new PaymentDBHelper(this);

    }
    public void yes_btn(View view) {
        Intent i = getIntent();
        String courseName = i.getStringExtra("courseName");
        String costText = i.getStringExtra("cost");
        String maxNum = i.getStringExtra("maxNum");
        String userName = i.getStringExtra("nic");

        double Scost = Double.parseDouble(costText);
        double newCost = 0;


        EditText promotion_codes = findViewById(R.id.promotion_codes);
        String code = promotion_codes.getText().toString();

        if (code.isEmpty()) {
            Toast.makeText(this, "Please enter a promotion code", Toast.LENGTH_SHORT).show();
        }
        else{
            if(code.equals("M563432")){
                //25
                newCost= Scost * 0.25;
            }
            else if (code.equals("S663435")) {
                //40
                newCost= Scost * 0.4;
            }
            else if (code.equals("L763434")) {
                //60
                newCost= Scost * 0.6;
            }
            else{
                Toast.makeText(AddPaymentActivity.this, "Please Valid promotion codes", Toast.LENGTH_SHORT).show();
                promotion_codes.setText("");
            }

            showConfirmationPopup(newCost, courseName, userName);
        }


    }

    public void No_btn(View view) {

        Intent i = getIntent();
        String courseName = i.getStringExtra("courseName");
        String costText = i.getStringExtra("cost");
        String maxNum = i.getStringExtra("maxNum");
        String userName = i.getStringExtra("nic");
        String email = i.getStringExtra("email");


        double newCost = Double.parseDouble(costText);

        showConfirmationPopup(newCost, courseName, userName);
    }




    // Create a method to show the custom popup message
    private void showConfirmationPopup(final double newCost, final String courseName, final String userName) {
        // Create a dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Set the message
        builder.setMessage("Course Cost: RS." + newCost + "\n\nAre you sure you want to Register ?");
        // Set the positive button (OK button)

        builder.setPositiveButton("Register", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                long newRowId = dbHelper.addPayment(newCost, courseName, userName);
                // send Email

                Thread thread = new Thread(new Runnable() {
                    Intent i = getIntent();
                    String courseName = i.getStringExtra("courseName");
                    String costText = i.getStringExtra("cost");
                    String maxNum = i.getStringExtra("maxNum");
                    String userName = i.getStringExtra("nic");
                    String email = i.getStringExtra("email");
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
                            mimeMessage.setText("Welcome to MindCraft Academy,\n\n Success full register \n Course Name :"+courseName+"\n Price :"+costText+"\n\n Thank you choosing us..!");

                            Transport.send(mimeMessage);

                        } catch (AddressException e) {
                            e.printStackTrace();
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();


                // go user dashboard
                Intent intent = new Intent(AddPaymentActivity.this, User_Dashboard.class);
                intent.putExtra("nic", userName);
                startActivity(intent);

            }
        });
        // Set the negative button (Cancel button)
        builder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Navigate to User Dashboard activity
                Intent intent = new Intent(AddPaymentActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}



