package com.example.graduateapp;

import android.graphics.Bitmap;

public class FileItem {
    private String name;
    private Bitmap bitmap;

    public FileItem(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}

