package com.example.lms.ui.Database.Model;

public class Enrollment {
    private int enrollmentId;
    private String studentEmail;
    private String enrolledCourse;
    private String enrolledBranch;
    private String enrollmentDate;

    // Constructor
    public Enrollment() {
    }


    // Getters and setters
    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getEnrolledCourse() {
        return enrolledCourse;
    }

    public void setEnrolledCourse(String enrolledCourse) {
        this.enrolledCourse = enrolledCourse;
    }

    public String getEnrolledBranch() {
        return enrolledBranch;
    }

    public void setEnrolledBranch(String enrolledBranch) {
        this.enrolledBranch = enrolledBranch;
    }

    public String getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(String enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }


}
