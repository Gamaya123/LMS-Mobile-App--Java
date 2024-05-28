package com.example.lms.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lms.R;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Course;

public class CreatecourseActivity extends AppCompatActivity {

    EditText editTextCourseName, editTextCourseFee, editTextBranches, editTextDuration,
            editTextStartingDate, editTextPublishedDate, editTextRegistrationClosingDate,editTextstcount;
    Button addButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcourse);

        editTextCourseName = findViewById(R.id.cname);
        editTextCourseFee = findViewById(R.id.courseFee);
        editTextBranches = findViewById(R.id.branches);
        editTextDuration = findViewById(R.id.duration);
        editTextStartingDate = findViewById(R.id.startingDate);
        editTextPublishedDate = findViewById(R.id.publishedDate);
        editTextRegistrationClosingDate = findViewById(R.id.registrationClosingDate);
        editTextstcount = findViewById(R.id.stcount);
        addButton = findViewById(R.id.addbtn);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from EditText fields
                String courseName = editTextCourseName.getText().toString().trim();
                String courseFee = editTextCourseFee.getText().toString().trim();
                String branches = editTextBranches.getText().toString().trim();
                String duration = editTextDuration.getText().toString().trim();
                String startingDate = editTextStartingDate.getText().toString().trim();
                String publishedDate = editTextPublishedDate.getText().toString().trim();
                String registrationClosingDate = editTextRegistrationClosingDate.getText().toString().trim();
                String stcont = editTextstcount.getText().toString().trim();



                // Create a Course object
                Course course = new Course();
                course.setName(courseName);
                course.setFee(courseFee);
                course.setBranches(branches);
                course.setDuration(duration);
                course.setStartingDate(startingDate);
                course.setPublishedDate(publishedDate);
                course.setRegistrationClosingDate(registrationClosingDate);
                course.setStudentcount(stcont);


                // Insert the Course object into the database
                DatabaseHelper databaseHelper = new DatabaseHelper(CreatecourseActivity.this);
                databaseHelper.createCourse(course);
                Toast.makeText(CreatecourseActivity.this, "Course created successfully", Toast.LENGTH_SHORT).show();


                // Optionally, you can display a message indicating successful course creation
                // or navigate to another activity
            }
        });
    }
}
