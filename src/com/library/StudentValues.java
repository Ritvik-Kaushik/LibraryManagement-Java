package com.library;

public class StudentValues {
    private String id;
    private String firstName;
    private String lastName;
    private String sex;
    private String dob;
    private String department;
    private String course;
    private String contact;
    private String email;
    public StudentValues(){
        this.id="";
        this.firstName="";
        this.lastName="";
        this.sex="";
        this.dob="";
        this.department="";
        this.course="";
        this.contact="";
        this.email="";
    }
  public StudentValues(String id,String fName,String lName,String sex,String dob,String depart,String course,String contact,String email){
      this.id=id;
      this.firstName=fName;
      this.lastName=lName;
      this.sex=sex;
      this.dob=dob;
      this.department=depart;
      this.course=course;
      this.contact=contact;
      this.email=email;
  }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
