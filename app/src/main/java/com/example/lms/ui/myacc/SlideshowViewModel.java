package com.example.lms.ui.myacc;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SlideshowViewModel extends ViewModel {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_SELECT = 2;

    private final MutableLiveData<Bitmap> selectedImageBitmap = new MutableLiveData<>();

    // Method to get the LiveData object for observing the selected image bitmap
    public LiveData<Bitmap> getSelectedImageBitmap() {
        return selectedImageBitmap;
    }

    // Method to handle image selection
    public void selectImage(Activity activity) {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent chooserIntent = Intent.createChooser(pickIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePhotoIntent});

        activity.startActivityForResult(chooserIntent, REQUEST_IMAGE_SELECT);
    }

    // Method to handle the result of image selection
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, Activity activity) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            saveImage(imageBitmap, activity);
            selectedImageBitmap.setValue(imageBitmap);
        } else if (requestCode == REQUEST_IMAGE_SELECT && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImageUri);
                saveImage(imageBitmap, activity);
                selectedImageBitmap.setValue(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to save the selected image to external storage
    private void saveImage(Bitmap bitmap, Activity activity) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + ".jpg";

        File storageDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, imageFileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
