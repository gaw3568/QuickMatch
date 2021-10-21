package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class Teampage_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teampage_fragment, container, false);

        ViewPager vp = (ViewPager) view.findViewById(R.id.teampage_viewpager);
        //프래그먼트에서 프래그먼트를 가져오기 위해 findViewById를 사용하려면 view.으로 사용(액티비티와 다름)
        //탭한 프래그먼트(화면)을 보여주는 곳

        Teampage_Adapter adapter = new Teampage_Adapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        //ViewPager와 Adapter(Teampage_Adapter)를 연결해줌

        TabLayout tab = view.findViewById(R.id.teampage_tab);
        tab.setupWithViewPager(vp);
        //tab과 ViewPager 연동

//        ArrayList<Integer> images = new ArrayList<>();
//        images.add(R.drawable.cal);
//        images.add(R.drawable.sea);
//        images.add(R.drawable.set);
//
//        for(int i=0; i<3; i++) tab.getTabAt(i).setIcon(images.get(i));
        //나중에 혹시 이미지 파일을 사용할때 사용

        return view;
    }


    //-----------------------------------------------------------------------프레그먼트마다 툴바 메뉴 다르게 하기위한 코드   -------------------
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.teampage_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.Teampage_addteam) {
            Intent intent = new Intent(getActivity(), Teampage_AddTeam.class);
            Toast.makeText(getActivity(), "팀 추가", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    //-----------------------------------------------------------------------프레그먼트마다 툴바 메뉴 다르게 하기위한 코드   -------------------


}
