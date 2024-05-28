package com.example.lms.ui.Admin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import com.example.lms.R;

public class ViewdetailsActivity extends AppCompatActivity {

    private EditText editTextName, editTextEmail, editTextStartDate, editTextCourseName, editTextEndDate, editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdetails);

        // Initialize EditText fields
        editTextName = findViewById(R.id.editTextNameuser);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextStartDate = findViewById(R.id.editStartDate);
        editTextCourseName = findViewById(R.id.editTextCourseName);
        editTextEndDate = findViewById(R.id.editTextText4);
        editTextPhone = findViewById(R.id.editTextPhone);

        // Get intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            // Extract data from extras
            String name = extras.getString("studentName");
            String email = extras.getString("studentEmail");
            String NIC  = extras.getString("studentNic");
            String Gender = extras.getString("studentGender");
            String Address = extras.getString("studentAddress");
            String phone = extras.getString("studentMobile");

            // Set data to EditText fields
            editTextName.setText(name);
            editTextEmail.setText(email);
            editTextStartDate.setText(Gender);
            editTextCourseName.setText(Address);
            editTextEndDate.setText(phone);
            editTextPhone.setText(NIC);
        }
    }
}
