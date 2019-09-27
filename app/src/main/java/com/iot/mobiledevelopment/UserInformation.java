package com.iot.mobiledevelopment;

public class UserInformation {

    public UserInformation(String username, String email, String phonenumber) {
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
    }

    public UserInformation() {

    }

    public String username;

    public String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String phonenumber;

}
