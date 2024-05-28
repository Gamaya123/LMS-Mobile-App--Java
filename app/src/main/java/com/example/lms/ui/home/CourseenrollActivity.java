package com.example.lms.ui.home;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Enrollment;
import com.example.lms.ui.register.RegisterActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

public class CourseenrollActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText code, location;
    private DatabaseHelper databaseHelper;
    private float fee;
    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courseenroll);

        // Initialize the EditText fields
        code = findViewById(R.id.editTextText5);
        location = findViewById(R.id.editTextLocation);
        String branch = location.getText().toString();

        getLocation();
        String cfeeString = getIntent().getStringExtra("course_fee");
        String course = getIntent().getStringExtra("course_name");
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        int cfeeValue = Integer.parseInt(cfeeString);

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Set the email to the EditText


        location.setText(email);
        // Get the current time
        String currentTime = getCurrentTime();

        // Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Handle submit button click
        Button submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputCode = code.getText().toString();
                if ("M563432".equals(inputCode)) {
                    fee = (float) (cfeeValue * 0.25);
                    String feeMessage = "Discount is: " + fee;
                    Toast.makeText(CourseenrollActivity.this, feeMessage, Toast.LENGTH_SHORT).show(); // Display fee message
                } else if ("S663435".equals(inputCode)) {
                    fee = (float) (cfeeValue * 0.40);
                    String feeMessage = "Discount is: " + fee;
                    Toast.makeText(CourseenrollActivity.this, feeMessage, Toast.LENGTH_SHORT).show(); // Display fee message
                } else if ("L763434".equals(inputCode)) {
                    fee = (float) (cfeeValue * 0.60);
                    String feeMessage = "Discount is: " + fee;
                    Toast.makeText(CourseenrollActivity.this, feeMessage, Toast.LENGTH_SHORT).show(); // Display fee message
                }

                // Update the student's record in the database
                updateStudentRecord(email, course, branch, currentTime);

                // Send email
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendEmail(email, course);

                    }
                }).start();

                // Show alert message
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseenrollActivity.this);
                builder.setMessage("Course Enrolled  successfully!")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Navigate to MainActivity
                                Intent intent = new Intent(CourseenrollActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Optional: Finish this activity to remove it from the back stack
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers for locations
        addMarkersForLocations();

        // Optionally, customize the map further
        // For example, you can set the map type and move the camera
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(6.9271, 79.8612), 7)); // Move the camera to Sri Lanka with zoom level 7
    }

    // Method to add markers for locations
    private void addMarkersForLocations() {
        // Example: Add a marker for Kottawa
        LatLng kottawa = new LatLng(6.8473, 79.9988); // Coordinates for Kottawa
        mMap.addMarker(new MarkerOptions().position(kottawa).title("Kottawa"));

        // Example: Add a marker for Colombo
        LatLng colombo = new LatLng(6.9271, 79.8612); // Coordinates for Colombo
        mMap.addMarker(new MarkerOptions().position(colombo).title("Colombo"));

        // Example: Add a marker for Homagama
        LatLng homagama = new LatLng(6.8402, 80.0030); // Coordinates for Homagama
        mMap.addMarker(new MarkerOptions().position(homagama).title("Homagama"));

        // Example: Add a marker for Galle
        LatLng galle = new LatLng(6.0535, 80.2210); // Coordinates for Galle
        mMap.addMarker(new MarkerOptions().position(galle).title("Galle"));
    }

    private void getLocation() {
        // Check if the user has granted location permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Get the location
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng currentLocation = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(currentLocation).title("Your Location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f)); // Zoom level 15
                } else {
                    Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            // Request location permissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    // Method to handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the location
                getLocation();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Method to send an email
    private void sendEmail(String email, String Course) {
        String stringSenderEmail = "poornainvest0@gmail.com";
        String stringReceiverEmail = email;
        String stringPasswordSenderEmail = "ksrh burq rvbj hxbh";

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

        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Subject: ICT Insitute");
            mimeMessage.setText("Hello Dear, \n\nYou are Successfully enrolled to the " + Course + "course. \n\nGood Luck!\n");

            Transport.send(mimeMessage);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(CourseenrollActivity.this, "Email Sent Successfully", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Method to update student record in the database
    private void updateStudentRecord(String email, String course, String location, String currentTime) {
        Enrollment enrollment = new Enrollment();

        enrollment.setStudentEmail(email);
        enrollment.setEnrolledCourse(course);
        enrollment.setEnrolledBranch(location);
        enrollment.setEnrollmentDate(currentTime);

        DatabaseHelper databaseHelper = new DatabaseHelper(CourseenrollActivity.this);
        databaseHelper.enrollCourse(enrollment);
        Log.d("CourseEnrollActivity", "Student record updated successfully");
        // Optionally, you can display a message to the user indicating that the record has been updated.
    }

    // Method to get current time in the format "yyyy-MM-dd HH:mm:ss"
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
}
