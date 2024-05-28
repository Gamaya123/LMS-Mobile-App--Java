package com.example.lms.ui.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lms.ui.Database.Model.Course;
import com.example.lms.ui.Database.Model.Enrollment;
import com.example.lms.ui.Database.Model.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database name and version
    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 12;

    // Table names and columns
    //-----------------student table----------------------
    private static final String TABLE_STUDENTS = "Students";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_CITY = "city";
    private static final String COLUMN_DOB = "dob";
    private static final String COLUMN_NIC = "nic";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_MOBILE = "mobile";

    private static final String COLUMN_COURSE = "Course";
    private static final String COLUMN_Branch = "Branch";

    private static final String COLUMN_REGISTER = "Rdate";

    //--------------------New course table
    private static final String TABLE_NEW_COURSES = "NewCourses";

    private static final String COLUMN_COURSE_ID = "course_id";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_FEE = "course_fee";
    private static final String COLUMN_BRANCHES = "branches";
    private static final String COLUMN_DURATION = "duration";
    private static final String COLUMN_STARTING_DATE = "starting_date";
    private static final String COLUMN_PUBLISHED_DATE = "published_date";
    private static final String COLUMN_REGISTRATION_CLOSING_DATE = "registration_closing_date";

    private static final String COLUMN_STUDENT_COUNT = "student_count";
    private static final String COLUMN_CURRENT_STUDENT_COUNT = "current_student_count";

    //------------enrollments--------------------------------------------
    private static final String TABLE_ENROLLMENTS = "Enrollments";
    private static final String COLUMN_ENROLLMENT_ID = "enrollment_id";
    private static final String COLUMN_STUDENT_EMAIL = "student_email";
    private static final String COLUMN_ENROLLED_COURSE = "enrolled_course";
    private static final String COLUMN_ENROLLED_BRANCH = "enrolled_branch";
    private static final String COLUMN_ENROLLMENT_DATE = "enrollment_date";



    private static int loginStatus = 0;
    // Create table SQL statements
     private static final String CREATE_TABLE_ENROLLMENTS = "CREATE TABLE " + TABLE_ENROLLMENTS + " (" +
                COLUMN_ENROLLMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_STUDENT_EMAIL + " TEXT," +
                COLUMN_ENROLLED_COURSE + " TEXT," +
                COLUMN_ENROLLED_BRANCH + " TEXT," +
                COLUMN_ENROLLMENT_DATE + " TEXT" +
                ")";


    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NAME + " TEXT," +
            COLUMN_ADDRESS + " TEXT," +
            COLUMN_CITY + " TEXT," +
            COLUMN_DOB + " TEXT," +
            COLUMN_NIC + " TEXT," +
            COLUMN_EMAIL + " TEXT," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_MOBILE + " TEXT," +
            COLUMN_COURSE + " TEXT," +
            COLUMN_Branch + " TEXT," +
            COLUMN_REGISTER + " TEXT" +

            ")";

    private static final String CREATE_TABLE_NEW_COURSES = "CREATE TABLE " + TABLE_NEW_COURSES + " (" +
            COLUMN_COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_COURSE_NAME + " TEXT," +
            COLUMN_COURSE_FEE + " TEXT," +
            COLUMN_BRANCHES + " TEXT," +
            COLUMN_DURATION + " TEXT," +
            COLUMN_STARTING_DATE + " TEXT," +
            COLUMN_PUBLISHED_DATE + " TEXT," +
            COLUMN_REGISTRATION_CLOSING_DATE + " TEXT," +
            COLUMN_STUDENT_COUNT + " TEXT," + // Add space before INTEGER
            COLUMN_CURRENT_STUDENT_COUNT + " INTEGER DEFAULT 0" +
            ")";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL(CREATE_TABLE_STUDENTS);
        db.execSQL(CREATE_TABLE_NEW_COURSES); // Use the new table name
        db.execSQL(CREATE_TABLE_ENROLLMENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEW_COURSES); // Use the new table name
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENROLLMENTS); // Use the new table name

        // Create tables again
        onCreate(db);
    }

    // Method to add a new student record
    public void createStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, student.getName());
        values.put(COLUMN_ADDRESS, student.getAddress());
        values.put(COLUMN_CITY, student.getCity());
        values.put(COLUMN_DOB, student.getDob());
        values.put(COLUMN_NIC, student.getNic());
        values.put(COLUMN_EMAIL, student.getEmail());
        values.put(COLUMN_GENDER, student.getGender());
        values.put(COLUMN_MOBILE, student.getMobile());

        // Inserting Row
        db.insert(TABLE_STUDENTS, null, values);

        setStatus("exist");

        db.close(); // Closing database connection
    }


    public static void setStatus(String status){
        if (status.equals("exist")) {
            loginStatus = 1;
        }

    }

    // Method to get the login status
    public static int getStatus(){
        return loginStatus;
    }




    @SuppressLint("Range")
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                student.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                student.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                student.setCity(cursor.getString(cursor.getColumnIndex(COLUMN_CITY)));
                student.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));
                student.setNic(cursor.getString(cursor.getColumnIndex(COLUMN_NIC)));
                student.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                student.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER)));
                student.setMobile(cursor.getString(cursor.getColumnIndex(COLUMN_MOBILE)));


                // Adding student to list
                students.add(student);
            } while (cursor.moveToNext());
        }

        // Close cursor and return students list
        cursor.close();
        return students;
    }

    // Method to add a new course record
    public void createCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, course.getName());
        values.put(COLUMN_COURSE_FEE, course.getFee());
        values.put(COLUMN_BRANCHES, course.getBranches());
        values.put(COLUMN_DURATION, course.getDuration());
        values.put(COLUMN_STARTING_DATE, course.getStartingDate());
        values.put(COLUMN_PUBLISHED_DATE, course.getPublishedDate());
        values.put(COLUMN_REGISTRATION_CLOSING_DATE, course.getRegistrationClosingDate());
        values.put(COLUMN_STUDENT_COUNT, course.getStudentcount());

        // Inserting Row
        db.insert(TABLE_NEW_COURSES, null, values);
        db.close(); // Closing database connection
    }


    @SuppressLint("Range")
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NEW_COURSES, null);
        if (cursor.moveToFirst()) {
            do {
                Course course = new Course();
                course.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_COURSE_ID)));
                course.setName(cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_NAME)));
                course.setFee(cursor.getString(cursor.getColumnIndex(COLUMN_COURSE_FEE)));
                course.setBranches(cursor.getString(cursor.getColumnIndex(COLUMN_BRANCHES)));
                course.setDuration(cursor.getString(cursor.getColumnIndex(COLUMN_DURATION)));
                course.setStartingDate(cursor.getString(cursor.getColumnIndex(COLUMN_STARTING_DATE)));
                course.setPublishedDate(cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHED_DATE)));
                course.setRegistrationClosingDate(cursor.getString(cursor.getColumnIndex(COLUMN_REGISTRATION_CLOSING_DATE)));
                course.setStudentcount(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_COUNT)));
                course.setCurrentstudentcount(cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_STUDENT_COUNT)));
                // Add other attributes similarly
                courses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courses;
    }

    @SuppressLint("Range")
    public List<String> getEnrolledCoursesByEmail(String email) {
        List<String> enrolledCourses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_ENROLLED_COURSE + " FROM " + TABLE_ENROLLMENTS + " WHERE " + COLUMN_STUDENT_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            do {
                String enrolledCourse = cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLED_COURSE));
                enrolledCourses.add(enrolledCourse);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return enrolledCourses;
    }


    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, course.getName());
        values.put(COLUMN_COURSE_FEE, course.getFee());
        values.put(COLUMN_BRANCHES, course.getBranches());
        values.put(COLUMN_DURATION, course.getDuration());
        values.put(COLUMN_STARTING_DATE, course.getStartingDate());
        values.put(COLUMN_PUBLISHED_DATE, course.getPublishedDate());
        values.put(COLUMN_REGISTRATION_CLOSING_DATE, course.getRegistrationClosingDate());
        values.put(COLUMN_STUDENT_COUNT, course.getStudentcount());

        // Update the course record
        db.update(TABLE_NEW_COURSES, values, COLUMN_COURSE_ID + " = ?",
                new String[]{String.valueOf(course.getId())});
        db.close(); // Closing database connection
    }


    public void deleteCourse(int courseId) {
        Log.d("Database", "Deleting course with ID: " + courseId); // Print courseId to log
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_NEW_COURSES, COLUMN_COURSE_ID + " = ?", new String[]{String.valueOf(courseId)});
        db.close(); // Closing database connection

        if (deletedRows > 0) {
            Log.d("Database", "Course deleted successfully");
        } else {
            Log.d("Database", "Failed to delete course");
        }
    }


    public void updateStudentCourse(String email, String course, String branch, String registerDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE, course);
        values.put(COLUMN_Branch, branch);
        values.put(COLUMN_REGISTER, registerDate);

        // Update the student record
        int rowsAffected = db.update(TABLE_STUDENTS, values, COLUMN_EMAIL + " = ?", new String[]{email});
        db.close(); // Closing database connection

        if (rowsAffected > 0) {
            Log.d("Database", "Student record updated successfully");
        } else {
            Log.d("Database", "Failed to update student record");
        }
    }

    public void enrollCourse(Enrollment enrollment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_EMAIL, enrollment.getStudentEmail());
        values.put(COLUMN_ENROLLED_COURSE, enrollment.getEnrolledCourse());
        values.put(COLUMN_ENROLLED_BRANCH, enrollment.getEnrolledBranch());
        values.put(COLUMN_ENROLLMENT_DATE, enrollment.getEnrollmentDate());

        // Inserting Row
        db.insert(TABLE_ENROLLMENTS, null, values);
        db.close(); // Closing database connection
    }


    @SuppressLint("Range")
    public List<Enrollment> getEnrollmentsByEmail(String email) {
        List<Enrollment> enrollments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ENROLLMENTS + " WHERE " + COLUMN_STUDENT_EMAIL + " = ?", new String[]{email});
        if (cursor.moveToFirst()) {
            do {
                Enrollment enrollment = new Enrollment();
                enrollment.setEnrollmentId(cursor.getInt(cursor.getColumnIndex(COLUMN_ENROLLMENT_ID)));
                enrollment.setStudentEmail(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)));
                enrollment.setEnrolledCourse(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLED_COURSE)));
                enrollment.setEnrolledBranch(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLED_BRANCH)));
                enrollment.setEnrollmentDate(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLMENT_DATE)));
                enrollments.add(enrollment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return enrollments;
    }



    @SuppressLint("Range")
    public List<Enrollment> getdetailsByEmail(String email) {
        List<Enrollment> enrollments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ENROLLMENTS + " WHERE " + COLUMN_STUDENT_EMAIL + " = ?", new String[]{email});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Enrollment enrollment = new Enrollment();
                    enrollment.setEnrollmentId(cursor.getInt(cursor.getColumnIndex(COLUMN_ENROLLMENT_ID)));
                    enrollment.setStudentEmail(cursor.getString(cursor.getColumnIndex(COLUMN_STUDENT_EMAIL)));
                    enrollment.setEnrolledCourse(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLED_COURSE)));
                    enrollment.setEnrolledBranch(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLED_BRANCH)));
                    enrollment.setEnrollmentDate(cursor.getString(cursor.getColumnIndex(COLUMN_ENROLLMENT_DATE)));
                    enrollments.add(enrollment);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return enrollments;
    }



    public void incrementCurrentStudentCount(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CURRENT_STUDENT_COUNT +
                " FROM " + TABLE_NEW_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?", new String[]{course.getName()});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") int currentStudentCount = cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_STUDENT_COUNT));
            int updatedStudentCount = currentStudentCount + 1;

            ContentValues values = new ContentValues();
            values.put(COLUMN_CURRENT_STUDENT_COUNT, updatedStudentCount);
            db.update(TABLE_NEW_COURSES, values, COLUMN_COURSE_NAME + " = ?", new String[]{course.getName()});
        }
        cursor.close();
    }


    @SuppressLint("Range")
    private int getCurrentStudentCount(Course course) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_CURRENT_STUDENT_COUNT +
                " FROM " + TABLE_NEW_COURSES + " WHERE " + COLUMN_COURSE_NAME + " = ?", new String[]{course.getName()});
        int currentStudentCount = 0; // Default value
        if (cursor.moveToFirst()) {
            currentStudentCount = cursor.getInt(cursor.getColumnIndex(COLUMN_CURRENT_STUDENT_COUNT));
        }
        cursor.close();
        return currentStudentCount;
    }




}
