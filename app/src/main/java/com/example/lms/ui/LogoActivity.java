package com.example.lms.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.ui.register.LoginActivity;

public class LogoActivity extends AppCompatActivity {

    private Button adminBtn, guestBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        // Initialize buttons
        adminBtn = findViewById(R.id.adminbtn);
        guestBtn = findViewById(R.id.guestbtn);

        // Set click listeners
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear SharedPreferences
                clearSharedPreferences();
                // Navigate to LoginActivity
                Intent intent = new Intent(LogoActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
            }
        });

        guestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear SharedPreferences
                clearSharedPreferences();
                // Navigate to MainActivity
                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the current activity
            }
        });

        // Delayed navigation to MainActivity after 3 seconds
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Clear SharedPreferences
//                clearSharedPreferences();
//                // Start MainActivity
//                Intent intent = new Intent(LogoActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish(); // Optional: Close the current activity
//            }
//        }, 3000); // 3000 milliseconds = 3 seconds
    }

    // Method to clear SharedPreferences
    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
