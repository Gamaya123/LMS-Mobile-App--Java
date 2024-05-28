package com.example.lms.ui.myacc;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.lms.databinding.FragmentSlideshowBinding;
import com.example.lms.ui.Database.DatabaseHelper;
import com.example.lms.ui.Database.Model.Enrollment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private SlideshowViewModel slideshowViewModel;
    private ImageView imageView;
    private SharedPreferences sharedPreferences;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 2;
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private static final String IMAGE_KEY = "image_key";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        imageView = binding.imageView3;
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        // Handle button click to select image
        binding.btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        // Load previously selected image, if any
        loadImageFromSharedPreferences();
        // Initialize TextViews
        TextView textViewName = binding.textViewName;
        TextView textViewTelephone = binding.textViewTelephone;
        TextView textViewAddress = binding.textViewAddress;
        TextView textViewEmail = binding.textViewEmail;
        TextView textViewCourses = binding.textViewCourses;
        TextView textViewRegistered = binding.textViewRegistered;

        // Get email from SharedPreferences or any other source
        String email = "janidupasan2@gmail.com"; // Replace this with your email retrieval logic

        // Fetch data from the database
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        List<Enrollment> enrollments = dbHelper.getdetailsByEmail(email);

        // Set data to TextViews if enrollments list is not empty
        if (!enrollments.isEmpty()) {
            Enrollment enrollment = enrollments.get(0); // Assuming only one enrollment for the user
            textViewEmail.setText(enrollment.getStudentEmail());
            textViewCourses.setText(enrollment.getEnrolledCourse());
            textViewRegistered.setText(enrollment.getEnrollmentDate());
        }

        return root;
    }

    // Method to open the gallery for image selection
    private void openGallery() {
        // Create an Intent to select an image from the device's gallery
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");

        // Create an Intent to capture an image from the camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Create a chooser Intent to allow the user to select either option
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        // Start the chooser Intent
        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
    }

    // Method to save the image to SharedPreferences
    private void saveImageToSharedPreferences(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] byteArray = baos.toByteArray();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_KEY, Base64.encodeToString(byteArray, Base64.DEFAULT));
        editor.apply();
    }

    // Method to load the image from SharedPreferences
    private void loadImageFromSharedPreferences() {
        String encodedImage = sharedPreferences.getString(IMAGE_KEY, null);
        if (encodedImage != null) {
            byte[] byteArray = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST) {
                if (data != null) {
                    // If the user selected an image from the gallery
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
                        imageView.setImageBitmap(bitmap);
                        // Save the selected image to SharedPreferences
                        saveImageToSharedPreferences(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == CAMERA_REQUEST) {
                // If the user captured an image from the camera
                if (data != null && data.getExtras() != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    // Save the captured image to SharedPreferences
                    saveImageToSharedPreferences(bitmap);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
