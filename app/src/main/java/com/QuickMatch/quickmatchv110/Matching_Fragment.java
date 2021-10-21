package com.QuickMatch.quickmatchv110;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Matching_Fragment extends Fragment implements View.OnClickListener {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matching_fragment, container, false);//fragment_matching을 보여준다는 뜻인듯? 아닐수도
        ImageButton btn_map1 = (ImageButton) view.findViewById(R.id.btn_map1);
        btn_map1.setOnClickListener(this);
        ImageButton btn_map2 = (ImageButton) view.findViewById(R.id.btn_map2);
        btn_map2.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View view) {
        String city;
        switch (view.getId()){
            case R.id.btn_map1 :
                city = "강북";
                Intent intent1 = new Intent(getActivity(),Matching_mapCalendar.class);
                intent1.putExtra("city", city);
                startActivity(intent1);
                break;
            case R.id.btn_map2 :
                city = "강남";
                Intent intent2 = new Intent(getActivity(),Matching_mapCalendar.class);
                intent2.putExtra("city", city);
                startActivity(intent2);
                break;
        }
    }
}
