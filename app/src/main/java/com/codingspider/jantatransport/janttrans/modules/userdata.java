package com.codingspider.jantatransport.janttrans.modules;

public class userdata {

    private String phone;
    private String email;
    private String fname;
    private String lname;
    private String password;

    public userdata(String id, String phone, String email, String fname, String lname, String password){

    }

    public userdata(String phone, String email, String fname, String lname, String password) {
        this.phone = phone;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getPassword() {
        return password;
    }
}
