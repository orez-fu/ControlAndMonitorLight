/* model */ /* Data*/
package com.example.controlandmonitorlight.model;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class Data {
    private String Value ;
    private String Type  ;
    private int Images ;

    public Data(String value, String type, int images) {
        this.Value = value;
        this.Type = type;
        this.Images = images;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getImages() {
        return Images;
    }

    public void setImages(int images) {
        Images = images;
    }
    @BindingAdapter("android:imageurl1")
    public static void setImage(View view, int image){
        ImageView imageView = (ImageView) view;
        imageView.setImageResource(image);
    }
}
