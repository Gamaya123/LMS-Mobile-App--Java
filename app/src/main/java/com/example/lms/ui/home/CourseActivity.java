package com.example.lms.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Course;
import com.example.lms.ui.register.RegisterActivity;

public class CourseActivity extends AppCompatActivity {
    // Retrieve data passed from the intent


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

//        String userEmail = getEmailFromSharedPreferences();



        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String courseId = extras.getString("course_id");
            String courseName = extras.getString("course_name");
            String courseFee = extras.getString("course_fee");
            String courseBranches = extras.getString("course_branches");
            String courseDuration = extras.getString("course_duration");
            String courseStartingDate = extras.getString("course_StartingDate");
            String courseClosingDate = extras.getString("course_ClosingDate");
            String coursePublishedDate = extras.getString("course_PublishedDate");





            // Set data to TextViews

            TextView textViewCourseName = findViewById(R.id.editTextCourseName);
            TextView textViewCourseFee = findViewById(R.id.editTextCourseFee);
            TextView textViewCourseBranches = findViewById(R.id.editTextBranches);
            TextView textViewCourseDuration = findViewById(R.id.editTextDuration);
            TextView textViewCourseStartingDate = findViewById(R.id.editTextStartingOn);
            TextView textViewCourseClosingDate = findViewById(R.id.editTextRegistrationClosingDate);
            TextView textViewCoursePublishedDate = findViewById(R.id.editTextPublishedOn);


            textViewCourseName.setText(courseName);
            textViewCourseFee.setText(courseFee);
            textViewCourseBranches.setText(courseBranches);
            textViewCourseDuration.setText(courseDuration);
            textViewCourseStartingDate.setText(courseStartingDate);
            textViewCourseClosingDate.setText(courseClosingDate);
            textViewCoursePublishedDate.setText(coursePublishedDate);
        }

        Button buttonEnroll = findViewById(R.id.buttonEnroll);

        // Set onClickListener for the Enroll button
        buttonEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if user is logged in
                boolean isLoggedIn = checkLoginStatus(); // This method should be implemented to check login status

                if (isLoggedIn) {
                    // User is logged in, proceed with enrollment
                    enrollUser();
                } else if (checkLoginStatus2()) {
                    enrollUser();
                } else {
                    showLoginPopup();
                }
            }
        });


//        Student st =new Student();
//        String name = st.getName(); // Retrieve the name of the student
//        Log.d("StudentName", name);



        // Initialize Back button
        Button buttonBack = findViewById(R.id.buttonBack);
        // Set onClickListener for the Back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the activity
                finish();
            }
        });



// Method to clear email from SharedPreferences


    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        clearEmailFromSharedPreferences();
//    }
//
//    // Method to clear email from SharedPreferences
//    private void clearEmailFromSharedPreferences() {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.remove("email");
//        editor.apply();
//    }
//
//    private String getEmailFromSharedPreferences() {
//        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
//        return sharedPreferences.getString("email", "");
//    }

    // Method to check if the user is logged in
    private boolean checkLoginStatus() {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        int status=databaseHelper.getStatus();

        if(status==1){

            return  true;
        }
        return false;
    }

    private boolean checkLoginStatus2() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        return email.isEmpty() ? false : true;
    }




    // Method to enroll the user
    private void enrollUser() {
        // Implement your enrollment logic here
        Toast.makeText(this, "Enrolling user...", Toast.LENGTH_SHORT).show();

        // Retrieve the course fee string from the intent extras
        int currentStudentCount =  getIntent().getIntExtra("current_student_count", 5);
        String courseStudentCountString = getIntent().getStringExtra("course_student_count");
        int courseStudentCount = Integer.parseInt(courseStudentCountString);

        if (currentStudentCount >= courseStudentCount) {
            Toast.makeText(this, "Maximum student count reached for this course", Toast.LENGTH_SHORT).show();

            // Create an intent to start the main activity
            Intent intent = new Intent(CourseActivity.this, MainActivity.class);
            startActivity(intent);

            // Finish the current activity to prevent going back to it with the back button
            finish();
            return;
        }

        String courseFeeString = getIntent().getStringExtra("course_fee");
        String course = getIntent().getStringExtra("course_name");
        Course courses =new Course();

        courses.setName(course);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        databaseHelper.incrementCurrentStudentCount(courses);


            // Parse the course fee string to an integer
            String courseFee=courseFeeString;

            // Create an intent to start the CourseenrollActivity
            Intent intent = new Intent(CourseActivity.this, CourseenrollActivity.class);
            intent.putExtra("course_fee", courseFee);
            intent.putExtra("course_name", course);
            // Start the activity
            startActivity(intent);

    }


    // Method to show a popup message with login and register buttons
    private void showLoginPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to login or register to enroll.")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open login activity or fragment

                        Toast.makeText(getApplicationContext(), "Opening Login Activity", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Register", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open register activity or fragment
                        Intent intent = new Intent(CourseActivity.this, RegisterActivity.class); // Replace CurrentActivity with your actual activity name
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Opening Register Activity", Toast.LENGTH_SHORT).show();
                    }
                });
        // Create the AlertDialog object and show it
        builder.create().show();
    }
}
