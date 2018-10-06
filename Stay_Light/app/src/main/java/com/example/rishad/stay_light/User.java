package com.example.rishad.stay_light;

public class User {
    public String username;
    public String email_id;
    public String pass;
    public String phoneno,dob,nationality,nidNo;

    public User() {

    }

    public User(String username, String email_id, String pass, String phoneno, String dob, String nationality, String nidNo) {
        this.username = username;
        this.email_id = email_id;
        this.pass = pass;
        this.phoneno = phoneno;
        this.dob = dob;
        this.nationality = nationality;
        this.nidNo = nidNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNidNo() {
        return nidNo;
    }

    public void setNidNo(String nidNo) {
        this.nidNo = nidNo;
    }
}
