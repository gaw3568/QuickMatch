package com.QuickMatch.quickmatchv110.Teampage;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Teampage_Teampage_Adapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> items;
    private ArrayList<String> itext = new ArrayList<String>();
    Fragment mainfragment, teamboard, schedule, member;

    public Teampage_Teampage_Adapter(FragmentManager fm, Team myteam) {
        super(fm);
        items = new ArrayList<Fragment>();
        mainfragment = new Teampage_Teampage_Notice();
        teamboard = new Teampage_Teampage_Teamboard();
        schedule = new Teampage_Teampage_Schedule();
        member = new Teampage_Teampage_Member();

        items.add(mainfragment);
        items.add(teamboard);
        items.add(schedule);
        items.add(member);

        //리스트에 탭 메뉴에 사욜될 페이지들 추가
        Bundle bundle = new Bundle();
        bundle.putParcelable(Teaminfo.team, myteam);
        mainfragment.setArguments(bundle);
        teamboard.setArguments(bundle);
        schedule.setArguments(bundle);
        member.setArguments(bundle);

        itext.add("팀 공지");
        itext.add("게시 판");
        itext.add("일정");
        itext.add("로스터");
        //스와이프와 탬을 연동해서 사용할경우 텍스트가 출력이 안되기 때문에 텍스트를 따로 배열에 저장

    }

    @Override
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
