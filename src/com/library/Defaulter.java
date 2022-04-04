package com.library;

public class Defaulter {
    private String id;
    private String cost;
    private String firstName;
    private String lastName;
    private String sex;
    private String dob;
    private String department;
    private String course;
    private String number;
    private String emailId;
    Defaulter(){
        this.id="";
        this.cost="";
        this.firstName="";
        this.lastName="";
        this.sex="";
        this.dob="";
        this.department="";
        this.emailId="";
        this.number="";
        this.course="";
    }
   Defaulter(String id,String cost, String fName,String lastName,String sex,String dob,String department,String course,String number,String email){
       this.id=id;
       this.cost=cost;
       this.firstName=fName;
       this.lastName=lastName;
       this.sex=sex;
       this.dob=dob;
       this.department=department;
       this.emailId=email;
       this.number=number;
       this.course=course;

   }
    public String getId() {
        return id;
    }

    public String getCost() {
        return cost;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSex() {
        return sex;
    }

    public String getDob() {
        return dob;
    }

    public String getDepartment() {
        return department;
    }

    public String getCourse() {
        return course;
    }

    public String getNumber() {
        return number;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
}
