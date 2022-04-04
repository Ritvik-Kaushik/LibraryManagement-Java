package com.library;

public class GetValues {
    private  String id;
    private  String firstName;
    private  String lastName;
    private String email;
    private  String phone;
    private String address;
    private String dateOfBirth;
    private String passwords;
    public GetValues(){
        this.id="";
        this.firstName="";
        this.lastName="";
        this.email="";
        this.phone="";
        this.address="";
        this.dateOfBirth="";
        this.passwords="";
    }
    public GetValues(String id,String firstname,String lastname,String email,String phone,String address,String dateOfBirth,String passwords){
        this.id=id;
        this.firstName=firstname;
        this.lastName=lastname;
        this.email=email;
        this.phone=phone;
        this.address=address;
        this.dateOfBirth=dateOfBirth;
        this.passwords=passwords;

    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }
}
