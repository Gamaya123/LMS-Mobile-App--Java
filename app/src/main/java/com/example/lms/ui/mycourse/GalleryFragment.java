package com.example.lms.ui.mycourse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.R;
import com.example.lms.databinding.FragmentGalleryBinding;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleryViewModel galleryViewModel;
    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;

        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(requireContext());

        // Get the ViewModel
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        // Observe the data from the ViewModel
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Get enrollments from the database
        List<Enrollment> enrollments = databaseHelper.getEnrollmentsByEmail("poornamalinhara53@gmail.com");
        ArrayList<String> courseList = new ArrayList<>();

        // Populate the course list from enrollments
        for (Enrollment enrollment : enrollments) {
            courseList.add(enrollment.getEnrolledCourse());
        }

        // Create an ArrayAdapter to bind the data to the ListView
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, courseList);

        // Find the ListView and set the adapter
        ListView listView = root.findViewById(R.id.couserlist);
        listView.setAdapter(adapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
