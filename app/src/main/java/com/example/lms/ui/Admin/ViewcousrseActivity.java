package com.example.lms.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.lms.R;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Course;
import java.util.ArrayList;
import java.util.List;

public class ViewcousrseActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcousrse);

        listView = findViewById(R.id.viewcourse);
        dbHelper = new DatabaseHelper(this);

        // Get list of courses from the database
        List<Course> courseList = dbHelper.getAllCourses();

        // Create a list of strings to hold the course names
        final List<String> courseNames = new ArrayList<>();
        for (Course course : courseList) {
            courseNames.add(course.getName());
        }

        // Set up adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseNames);
        listView.setAdapter(adapter);

        // Set item click listener for the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the clicked course name
                String courseName = courseNames.get(position);

                // Find the corresponding Course object
                Course selectedCourse = null;
                for (Course course : courseList) {
                    if (course.getName().equals(courseName)) {
                        selectedCourse = course;
                        break;
                    }
                }

                // Check if a course was found
                if (selectedCourse != null) {

                    int courseId = selectedCourse.getId();
                    Log.d("ViewcourseActivity", "Selected course ID: " + courseId);

                    // Create an Intent to start the Update activity
                    Intent intent = new Intent(ViewcousrseActivity.this, UpdateActivity.class);
                    // Pass the selected Course object's related data to the Update activity
                    intent.putExtra("course_id", selectedCourse.getId());
                    intent.putExtra("course_name", selectedCourse.getName());
                    intent.putExtra("course_fee", selectedCourse.getFee());
                    intent.putExtra("course_branches", selectedCourse.getBranches());
                    intent.putExtra("course_duration", selectedCourse.getDuration());
                    intent.putExtra("course_StartingDate", selectedCourse.getStartingDate());
                    intent.putExtra("course_registrationClosingDate", selectedCourse.getRegistrationClosingDate());
                    intent.putExtra("course_PublishedDate", selectedCourse.getPublishedDate());
                    intent.putExtra("course_count", selectedCourse.getStudentcount());


                    // Add more extras for other related data if needed
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
