package com.r_time_run.newmess.bean;

/**
 * Created by zouxiaobang on 15/9/20.
 */
public class UserInfo {
    private String name;
    private String password;
    private String againPassword;
    private String studentId;
    private String studentServicePassword;

    @Override
    public String toString() {
        return "UserInfo{" +
                "againPassword='" + againPassword + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", studentId='" + studentId + '\'' +
                ", studentServicePassword='" + studentServicePassword + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    private String email;
    private String phone;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAgainPassword() {
        return againPassword;
    }

    public void setAgainPassword(String againPassword) {
        this.againPassword = againPassword;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentServicePassword() {
        return studentServicePassword;
    }

    public void setStudentServicePassword(String studentServicePassword) {
        this.studentServicePassword = studentServicePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
