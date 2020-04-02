package com.example.controlandmonitorlight.model;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Introduction {
    private String Title ;
    private String Description ;
    private Integer Image ;
    private String Devices;
    private int Id ;
    public Introduction() {

    }

    public Introduction(String title, String description, Integer image, String devices, int id) {
        Title = title;
        Description = description;
        Image = image;
        Devices = devices;
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Integer getImage() {
        return Image;
    }

    public void setImage(Integer image) {
        Image = image;
    }

    public String getDevices() {
        return Devices;
    }

    public void setDevices(String devices) {
        Devices = devices;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    @BindingAdapter("android:imageurl")
    public static void setImage(View view, int img)
    {
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(img);
    }
}
