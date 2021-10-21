package com.QuickMatch.quickmatchv110;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Job_Adapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();

    public Job_Adapter(FragmentManager fm) {
        super(fm);
        items = new ArrayList<Fragment>();
        items.add(new Job_Find_Team());
        items.add(new Job_Find_TeamPlayer());
        items.add(new Job_Request_Soldier());
        items.add(new Job_Find_Soldier());
        //리스트에 탭 메뉴에 사욜될 페이지들 추가

        itext.add("팀 구함");
        itext.add("팀원 구함");
        itext.add("용병 신청");
        itext.add("용병 구함");
        //스와이프와 탬을 연동해서 사용할경우 텍스트가 출력이 안되기 때문에 텍스트를 따로 배열에 저장
    }

    public Fragment getItem(int position) {
        return items.get(position);
    }//탭 내용

    @Override
    public int getCount() {
        return items.size();
    }//탭 개수

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return itext.get(position);
    }//탭 이름을 가져옴
}
