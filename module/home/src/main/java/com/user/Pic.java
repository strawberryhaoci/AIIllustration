package com.user;/*
 *name: AIIllustration
 *description:
 */

import android.graphics.Bitmap;

/**
 * @program: AIIllustration
 * @description: pic class
 */
public class Pic {
    private int picId;
    private String des;
    private int redrawFactor;
    private int seed;
    private Bitmap oriBitmap;
    private Bitmap genBitmap;
    private String username;
    private String oriPath;
    private String genPath;

    public Pic(){

    }
    public Pic(String username,String genPath){
        this.username = username;
        this.genPath = genPath;
    }
    public Pic(String username,String genPath,String des,int redrawFactor,int seed){
        this.username = username;
        this.genPath = genPath;
        this.des = des;
        this.redrawFactor = redrawFactor;
        this.seed = seed;
    }
    public void setDes(String des) {
        this.des = des;
    }

    public void setGenBitmap(Bitmap genBitmap) {
        this.genBitmap = genBitmap;
    }

    public void setOriBitmap(Bitmap oriBitmap) {
        this.oriBitmap = oriBitmap;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public void setRedrawFactor(int redrawFactor) {
        this.redrawFactor = redrawFactor;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOriPath(String oriPath) {
        this.oriPath = oriPath;
    }

    public void setGenPath(String genPath) {
        this.genPath = genPath;
    }

    public Bitmap getGenBitmap() {
        return genBitmap;
    }

    public Bitmap getOriBitmap() {
        return oriBitmap;
    }

    public int getPicId() {
        return picId;
    }

    public int getRedrawFactor() {
        return redrawFactor;
    }

    public int getSeed() {
        return seed;
    }

    public String getDes() {
        return des;
    }

    public String getUsername() {
        return username;
    }

    public String getOriPath() {
        return oriPath;
    }

    public String getGenPath() {
        return genPath;
    }
}
