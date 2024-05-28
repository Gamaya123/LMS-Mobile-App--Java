package com.example.lms.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lms.R;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Course;

public class UpdateActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Retrieve data from Intent extras
        int courseId = getIntent().getIntExtra("course_id", -1);
        String courseName = getIntent().getStringExtra("course_name");
        String courseFee = getIntent().getStringExtra("course_fee");
        String courseBranches = getIntent().getStringExtra("course_branches");
        String courseDuration = getIntent().getStringExtra("course_duration");
        String courseStartingDate = getIntent().getStringExtra("course_StartingDate");
        String courseClosingDate = getIntent().getStringExtra("course_registrationClosingDate");
        String coursePublishedDate = getIntent().getStringExtra("course_PublishedDate");
        String courseStcount = getIntent().getStringExtra("course_count");

        // Populate EditText fields with the received data
        EditText courseIdEditText = findViewById(R.id.cid);
        EditText courseFeeEditText = findViewById(R.id.courseFee);
        EditText branchesEditText = findViewById(R.id.branches);
        EditText durationEditText = findViewById(R.id.duration_update);
        EditText startingDateEditText = findViewById(R.id.startingDate_update);
        EditText publishedDateEditText = findViewById(R.id.publishedDate_update);
        EditText closingDateEditText = findViewById(R.id.registrationClosingDate_update);
        EditText stcountEditText = findViewById(R.id.stcountupdate);

        courseIdEditText.setText(String.valueOf(courseName));  // Course name in courseIdEditText?
        courseFeeEditText.setText(String.valueOf(courseFee));
        branchesEditText.setText(String.valueOf(courseBranches));
        durationEditText.setText(String.valueOf(courseDuration));
        startingDateEditText.setText(courseStartingDate);
        publishedDateEditText.setText(coursePublishedDate);
        closingDateEditText.setText(courseClosingDate);  // Closing date in stcountEditText?
        stcountEditText.setText(courseStcount);

        // Update Button click listener
        findViewById(R.id.updatebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve updated values from EditText fields
                String updatedCourseName = courseIdEditText.getText().toString();
                String updatedCourseFee = courseFeeEditText.getText().toString();
                String updatedCourseBranches = branchesEditText.getText().toString();
                String updatedCourseDuration = durationEditText.getText().toString();
                String updatedCourseStartingDate = startingDateEditText.getText().toString();
                String updatedCoursePublishedDate = publishedDateEditText.getText().toString();
                String updatedCourseClosingDate = closingDateEditText.getText().toString();
                String stcounts = stcountEditText.getText().toString();


                // Create a Course object with the updated values
                Course updatedCourse = new Course();
                updatedCourse.setId(courseId); // Assuming courseId is available
                updatedCourse.setName(updatedCourseName);
                updatedCourse.setFee(updatedCourseFee);
                updatedCourse.setBranches(updatedCourseBranches);
                updatedCourse.setDuration(updatedCourseDuration);
                updatedCourse.setStartingDate(updatedCourseStartingDate);
                updatedCourse.setPublishedDate(updatedCoursePublishedDate);
                updatedCourse.setRegistrationClosingDate(updatedCourseClosingDate);
                updatedCourse.setStudentcount(stcounts);

                DatabaseHelper dbHelper = new DatabaseHelper(UpdateActivity.this);
                // Perform update operation using DatabaseHelper
                dbHelper.updateCourse(updatedCourse);

                // Close the dbHelper instance after use
                dbHelper.close();

                // Display a toast message indicating successful update
                Toast.makeText(UpdateActivity.this, "Course updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // Delete Button click listener
        findViewById(R.id.delete_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of DatabaseHelper
                DatabaseHelper dbHelper = new DatabaseHelper(UpdateActivity.this);

                // Perform delete operation using dbHelper instance
                dbHelper.deleteCourse(courseId);

                // Close the dbHelper instance after use
                dbHelper.close();

                // Display a toast message indicating successful deletion
                Toast.makeText(UpdateActivity.this, "Course deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
