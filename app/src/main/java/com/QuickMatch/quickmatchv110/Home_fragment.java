package com.QuickMatch.quickmatchv110;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Home_fragment extends Fragment{
    private TextView day, temp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container,false);//fragement_main을 보여준다는 뜻인듯? 아닐수도




        ViewPager vp = (ViewPager) view.findViewById(R.id.home_viewpager);
        Home_Adapter adapter = new Home_Adapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        //ViewPager와 Adapter(Teampage_Adapter)를 연결해줌

        TabLayout tab = view.findViewById(R.id.home_tab);
        tab.setupWithViewPager(vp);
        //tab과 ViewPager 연동

        day = view.findViewById(R.id.day);
        temp = view.findViewById(R.id.temp);

        return view;
    }

    public void setDay(String data){
        if(data != null){
            day.setText(data);
        }
    }

    public void setTemp(String data){
        if(data != null){
            temp.setText(data);
        }
    }

}
