package com.example.lms.ui.home;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Course;


import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<String>> itemList;
    private ArrayAdapter<String> adapter;
    private List<Course> courseList;

    public HomeViewModel() {
        itemList = new MutableLiveData<>();
    }

    public void loadData(Context context, ListView listView) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        courseList = dbHelper.getAllCourses();

        // Create a list of strings to hold the course names
        final List<String> courseNames = new ArrayList<>();
        for (Course course : courseList) {
            courseNames.add(course.getName());
        }

        // Set up adapter for the ListView
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, courseNames);
        listView.setAdapter(adapter);

        // Set click listener on ListView items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course selectedCourse = courseList.get(position);
                if (selectedCourse != null) {
                    // Create an Intent to start the Update activity
                    Intent intent = new Intent(context,CourseActivity.class);
                    // Pass the selected Course object's related data to the Update activity
                    intent.putExtra("course_id", selectedCourse.getId());
                    intent.putExtra("course_name", selectedCourse.getName());
                    intent.putExtra("course_fee", selectedCourse.getFee());
                    intent.putExtra("course_branches", selectedCourse.getBranches());
                    intent.putExtra("course_duration", selectedCourse.getDuration());
                    intent.putExtra("course_StartingDate", selectedCourse.getStartingDate());
                    intent.putExtra("course_ClosingDate", selectedCourse.getRegistrationClosingDate());
                    intent.putExtra("course_PublishedDate", selectedCourse.getPublishedDate());
                    intent.putExtra("course_student_count",selectedCourse.getStudentcount());
                    intent.putExtra("current_student_count",selectedCourse.getCurrentstudentcount());

                    // Start the Update activity
                    context.startActivity(intent);
                }
            }
        });

        // Set the list of course names to the LiveData
        itemList.setValue(courseNames);
    }

    public LiveData<List<String>> getItemList() {
        return itemList;
    }
}
