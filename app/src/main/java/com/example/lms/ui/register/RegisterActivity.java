package com.example.lms.ui.register;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lms.MainActivity;
import com.example.lms.R;
import com.example.lms.ui.Admin.AdminActivity;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Student;
import com.example.lms.ui.home.CourseActivity;
import com.example.lms.ui.home.CourseenrollActivity;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextName, editTextAddress, editTextCity, editTextDob, editTextNic, editTextEmail, editTextMobile;
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale, radioButtonFemale, radioButtonOther;
    Button registerButton, loginButton;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextCity = findViewById(R.id.editTextLivingCity);
        editTextDob = findViewById(R.id.editTextDateOfBirth);
        editTextNic = findViewById(R.id.editTextNIC);
        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextMobile = findViewById(R.id.editTextMobilePhoneNumber);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioButtonOther = findViewById(R.id.radioButtonOther);

        registerButton = findViewById(R.id.buttonRegister);
        loginButton = findViewById(R.id.buttonLogin);

        // Set OnClickListener for Date of Birth EditText
        editTextDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String city = editTextCity.getText().toString().trim();
                String dob = editTextDob.getText().toString().trim();
                String nic = editTextNic.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String mobile = editTextMobile.getText().toString().trim();

                String gender = "";
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                if(selectedId == radioButtonMale.getId()) {
                    gender = "Male";
                } else if(selectedId == radioButtonFemale.getId()) {
                    gender = "Female";
                } else if(selectedId == radioButtonOther.getId()) {
                    gender = "Other";
                }

                Student student = new Student();
                student.setName(name);
                student.setAddress(address);
                student.setCity(city);
                student.setDob(dob);
                student.setNic(nic);
                student.setEmail(email);
                student.setGender(gender);
                student.setMobile(mobile);

                DatabaseHelper databaseHelper = new DatabaseHelper(RegisterActivity.this);
                databaseHelper.createStudent(student);

                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                // Save email to SharedPreferences

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);

                startActivity(intent);
            }
        });
    }
    // Method to show DatePickerDialog
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDob.setText(date);
                    }
                },
                year,
                month,
                dayOfMonth
        );
        datePickerDialog.show();
    }
}

