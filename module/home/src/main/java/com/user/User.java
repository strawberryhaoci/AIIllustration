package com.user;/*
 *name: AIIllustration
 *description:
 */

import java.util.ArrayList;

/**
 * @program: AIIllustration
 * @description: user class
 */
public class User {
    private String username;
    private int userid = 0;
    private String password;
    private int picNum = 0;
    ArrayList<Pic> picHistory;

    public User(String name, String pwd) {
        password = pwd;
        username = name;
    }
    public User(){

    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public int getPicNum() {
        return picNum;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setPicNum(int picNum) {
        this.picNum = picNum;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean loginin(String name, String pwd) {
        if (name.equals(this.username) && pwd.equals(this.password)) {
            return true;
        } else
            return false;
    }
}
