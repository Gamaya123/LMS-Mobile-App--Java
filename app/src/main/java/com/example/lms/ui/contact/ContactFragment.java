package com.example.lms.ui.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lms.R;

import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Enrollment;

import java.util.List;

public class ContactFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.contact_menu, container, false);

        // Initialize TextViews
        TextView textViewName = root.findViewById(R.id.textViewName);
        TextView textViewTelephone = root.findViewById(R.id.textViewTelephone);
        TextView textViewAddress = root.findViewById(R.id.textViewAddress);
        TextView textViewEmail = root.findViewById(R.id.textViewEmail);

        // Get email from SharedPreferences or any other source
        String email = "janidupasan2@gmail.com"; // Replace this with your email retrieval logic

        // Fetch data from the database
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        List<Enrollment> enrollments = dbHelper.getdetailsByEmail(email);

        // Set data to TextViews if enrollments list is not empty

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
