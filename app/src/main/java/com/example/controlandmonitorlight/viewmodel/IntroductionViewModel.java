/*viewmodel*/
package com.example.controlandmonitorlight.viewmodel;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.controlandmonitorlight.R;
import com.example.controlandmonitorlight.model.Introduction;

import java.util.ArrayList;
import java.util.List;

public class IntroductionViewModel extends ViewModel {
    private MutableLiveData<List<Introduction>> intro = new MutableLiveData<>() ;
    public Comunication clicked ;
    public MutableLiveData<List<Introduction>> getIntro() {
        return intro;
    }
    public void clicked(Comunication clicked)
    {
        this.clicked = clicked ;
    }
    public void SetData()
    {
        List<Introduction> list;
        list = new ArrayList<>();

        list.add(new Introduction("KidRoom","", R.drawable.ic_business_black_24dp,"Devices: 3",1)) ;
        list.add(new Introduction("LivingRoom","",R.drawable.ic_business_black_24dp,"Devices: 3",2)) ;
        list.add(new Introduction("","",R.drawable.ic_business_black_24dp,"Devices: 3",3));
        list.add(new Introduction("","",R.drawable.ic_business_black_24dp,"Devices: 3",4)) ;
        list.add(new Introduction("","",R.drawable.ic_business_black_24dp,"Devices: 3",5));
        list.add(new Introduction("","",R.drawable.ic_business_black_24dp,"Devices: 3",6)) ;
        this.intro.setValue(list);
    }

}

