package com.example.lms.ui.register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.ui.Admin.AdminActivity;

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
    private EditText confirmCodeEditText;

    private Button loginButton, sendCodeButton;

    private int sentCode;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize EditText fields
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        confirmCodeEditText = findViewById(R.id.editTextNumber);

        // Initialize buttons with their respective view IDs
        loginButton = findViewById(R.id.loginbtn); // Assuming this is the ID of your login button
        sendCodeButton = findViewById(R.id.sendcode);

        // Set click listener for the send code button
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateCodeAndSendEmail();
            }
        });

        // Set click listener for the login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateConfirmationCode();
            }
        });
    }

    // Method to generate random code and send email
    public void generateCodeAndSendEmail() {
        // Generate a random 6-digit code
        int randomCode = generateRandomCode();

        // Set the generated code to the confirm code EditText
        // Save the generated code
        sentCode = randomCode;

        // Save the email to SharedPreferences
        String userEmail = emailEditText.getText().toString().trim();
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", userEmail);
        editor.apply();

        // Send the generated code to the email using AsyncTask
        new SendEmailTask().execute(randomCode);
    }

    // Method to generate a random 6-digit code
    private int generateRandomCode() {
        Random random = new Random();
        // Generate a random integer between 100000 and 999999 (inclusive)
        return random.nextInt(900000) + 100000;
    }



    // AsyncTask to send email in background
    private class SendEmailTask extends AsyncTask<Integer, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Integer... params) {
            int code = params[0];
            String userEmail = emailEditText.getText().toString().trim();
            String stringSenderEmail = "poornainvest0@gmail.com";
            String stringReceiverEmail = userEmail; // Retrieve email from SharedPreferences
            String stringPasswordSenderEmail = "ksrh burq rvbj hxbh";

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

            try {
                MimeMessage mimeMessage = new MimeMessage(session);
                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

                mimeMessage.setSubject("Your Confirmation Code");
                mimeMessage.setText("Your confirmation code is: " + code);

                Transport.send(mimeMessage);

                return true;

            } catch (AddressException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(LoginActivity.this, "Code Sent Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Failed to send code", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to validate confirmation code
    private void validateConfirmationCode() {

        String enteredCode = confirmCodeEditText.getText().toString();
        String userEmail = emailEditText.getText().toString().trim();

        if(userEmail.equals("janidupasan2@gmail.com") && Integer.parseInt(enteredCode) == sentCode){
            navigateToAdminActivity();
        }
        else if (!enteredCode.isEmpty() && Integer.parseInt(enteredCode) == sentCode) {
            // Confirmation code matches
            navigateToMainActivity();
        } else {
            // Confirmation code does not match
            Toast.makeText(this, "Invalid confirmation code", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to navigate to MainActivity
    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish LoginActivity so user cannot go back
    }

    private void navigateToAdminActivity() {
        Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
        startActivity(intent);
        finish(); // Optional: Finish LoginActivity so user cannot go back
    }
}
