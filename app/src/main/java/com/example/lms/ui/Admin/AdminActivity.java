package com.example.lms.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.lms.R;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Student;
import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private Button createCourseButton,viewCourseButton; // Button to navigate to CreatecourseActivity

    private DatabaseHelper dbHelper; // Database helper instance

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        listView = findViewById(R.id.courseListView); // Assuming you have a ListView with id 'courseListView' in your activity_admin layout
        createCourseButton = findViewById(R.id.CreateCourseButton); // Assuming you have a Button with id 'CreateCourseButton' in your activity_admin layout
        viewCourseButton = findViewById(R.id.viewCoursesButton);

        dbHelper = new DatabaseHelper(this); // Initialize the DatabaseHelper

        // Load student data from the database
        List<Student> studentList = dbHelper.getAllStudents();

        // Create a list of strings to hold the formatted student data (name and email)
        List<String> formattedData = new ArrayList<>();
        for (Student student : studentList) {
            formattedData.add(student.getName() + " - " + student.getEmail());
        }

        // Create adapter and set it to ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, formattedData);
        listView.setAdapter(adapter);

        // Set OnClickListener for the create course button
        createCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreatecourseActivity
                Intent intent = new Intent(AdminActivity.this, CreatecourseActivity.class);
                startActivity(intent);
            }
        });


        viewCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CreatecourseActivity
                Intent intent = new Intent(AdminActivity.this, ViewcousrseActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected student from the list
                Student selectedStudent = studentList.get(position);

                // Create an intent to navigate to the ViewDetailsActivity
                Intent intent = new Intent(AdminActivity.this, ViewdetailsActivity.class);

                // Pass all the selected student's data to the ViewDetailsActivity using putExtra
                intent.putExtra("studentId", selectedStudent.getId());
                intent.putExtra("studentName", selectedStudent.getName());
                intent.putExtra("studentAddress", selectedStudent.getAddress());
                intent.putExtra("studentCity", selectedStudent.getCity());
                intent.putExtra("studentDob", selectedStudent.getDob());
                intent.putExtra("studentNic", selectedStudent.getNic());
                intent.putExtra("studentEmail", selectedStudent.getEmail());
                intent.putExtra("studentGender", selectedStudent.getGender());
                intent.putExtra("studentMobile", selectedStudent.getMobile());

                // Start the ViewDetailsActivity
                startActivity(intent);
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
