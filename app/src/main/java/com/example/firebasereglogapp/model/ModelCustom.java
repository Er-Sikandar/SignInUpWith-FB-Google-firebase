package com.example.firebasereglogapp.model;

public class ModelCustom {
String name,age,mobile,email,passwd,userType;

    public ModelCustom() {
    }

    public ModelCustom(String name, String age, String mobile, String email, String passwd,String userType) {
        this.name = name;
        this.age = age;
        this.mobile = mobile;
        this.email = email;
        this.passwd = passwd;
        this.userType=userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
