package com.library;

public class TransactionDone {
    private String id;
    private  String name;
    private String date;
    private String fine;
    private String paid;
TransactionDone(){
    this.id="";
    this.name="";
    this.date="";
    this.fine="";
    this.paid="";
}
TransactionDone(String id,String name,String date,String fine,String paid){
    this.id=id;
    this.name=name;
    this.date=date;
    this.fine=fine;
    this.paid=paid;
}
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFine() {
        return fine;
    }

    public String getPaid() {
        return paid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }
}
