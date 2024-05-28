package com.example.lms.ui.Database.Model;

public class Student {
    private int id;
    private String name;
    private String address;
    private String city;
    private String dob;
    private String nic;
    private String email;
    private String gender;
    private String mobile;
    private String course;
    private String rDate; // Assuming this is the registration date
    private String branch;

    public Student(int id,String name, String address, String city, String dob, String nic, String email, String gender, String mobile) {

        this.id=id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.dob = dob;
        this.nic = nic;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
    }

    // Constructor for registering with course information
    public  Student(String id, String course, String rDate, String branch) {
        this.id = Integer.parseInt(id);
        this.course = course;
        this.rDate = rDate;
        this.branch = branch;
    }




    public Student() {

    }


    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public int getId() {
        return id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setId(int id) {
        this.id= id;
    }

    public String getRDate() {
        return rDate;
    }

    public void setRDate(String rDate) {
        this.rDate = rDate;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
