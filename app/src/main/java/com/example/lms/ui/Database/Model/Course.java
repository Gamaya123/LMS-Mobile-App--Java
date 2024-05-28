package com.example.lms.ui.Database.Model;

public class Course {
    private int id;
    private String name;
    private String fee;
    private String branches;
    private String duration;
    private String startingDate;
    private String publishedDate;
    private String registrationClosingDate;

    private String studentcount;

    private int currentstudentcount;

    public int getCurrentstudentcount() {
        return currentstudentcount;
    }

    public void setCurrentstudentcount(int currentstudentcount) {
        this.currentstudentcount = currentstudentcount;
    }

    // Constructors
    public Course() {

    }

    public Course(int id,String name, String fee, String branches, String duration, String startingDate, String publishedDate, String registrationClosingDate,String studentcount) {
        this.id=id;
        this.name = name;
        this.fee = fee;
        this.branches = branches;
        this.duration = duration;
        this.startingDate = startingDate;
        this.publishedDate = publishedDate;
        this.registrationClosingDate = registrationClosingDate;
        this.studentcount=studentcount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getBranches() {
        return branches;
    }

    public void setBranches(String branches) {
        this.branches = branches;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getRegistrationClosingDate() {
        return registrationClosingDate;
    }

    public void setRegistrationClosingDate(String registrationClosingDate) {
        this.registrationClosingDate = registrationClosingDate;
    }
    public String getStudentcount() {
        return studentcount;
    }

    public void setStudentcount(String studentcount) {
        this.studentcount = studentcount;
    }
}
