package com.example.task_02.Models;

public class Phone {

    private String home;

    private String mobile;

    public Phone(){

    }

    public Phone(String home, String mobile) {
        this.home = home;
        this.mobile = mobile;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
