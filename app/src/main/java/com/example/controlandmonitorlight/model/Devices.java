/*model*/ /*Devices*/
package com.example.controlandmonitorlight.model;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Devices {
    private String Name ;
    private String Value ;
    private String Status;
    private int Images ;

    public Devices() {
    }

    public Devices(String name, String value, String status, int images) {
        Name = name;
        Value = value;
        Status = status;
        Images = images;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }
    @BindingAdapter("android:imagesrc")
    public static void setImage(View view, int image )
    {
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(image);
    }
}
