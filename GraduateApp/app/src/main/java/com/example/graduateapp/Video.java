package com.example.graduateapp;

import android.graphics.Bitmap;

public class Video {
    private String name;
    private Bitmap bitmap;
    private int position;
    private int chosen;



    public Video(String name, Bitmap bitmap,int position,int chosen) {
        this.name = name;
        this.bitmap = bitmap;
        this.position=position;
        this.chosen=chosen;


    }

    public String getName() {
        return name;
    }

    public void setName(String str){this.name=str;}

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) { this.bitmap=bitmap;}

    public int getPosition()    {return position;}

    public void setPosition(int position)    {this.position=position;}

    public int getChosen() {return chosen;}

    public void resetChosen(int num){ this.chosen=num; }

}
