package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Job_Fragment extends Fragment {

    private FloatingActionButton j_Add_Post, j_Find_Team, j_Find_Teamplayer, j_Request_Soldier, j_Find_Soldier;
    private TextView t_Find_Team, t_Find_Teamplayer, t_Request_Soldier, t_Find_Soldier;
    private boolean isOpen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_fragment, container,false);//fragement_main을 보여준다는 뜻인듯? 아닐수도

        ViewPager vp = (ViewPager) view.findViewById(R.id.job_viewPager);
        Job_Adapter adapter = new Job_Adapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        //ViewPager와 Adapter(Job_Adapter)를 연결

        TabLayout tab = view.findViewById(R.id.job_tab);
        tab.setupWithViewPager(vp);
        //tab과 ViewPager를 연동시키는 부분

        j_Add_Post = (FloatingActionButton)view.findViewById(R.id.add_post);
        j_Find_Team = (FloatingActionButton)view.findViewById(R.id.add_find_team);
        j_Find_Teamplayer = (FloatingActionButton)view.findViewById(R.id.add_find_teamplayer);
        j_Request_Soldier = (FloatingActionButton)view.findViewById(R.id.add_request_soldier);
        j_Find_Soldier = (FloatingActionButton)view.findViewById(R.id.add_find_soldier);

        t_Find_Team = view.findViewById(R.id.text_find_team);
        t_Find_Teamplayer = view.findViewById(R.id.text_find_teamplayer);
        t_Request_Soldier = view.findViewById(R.id.text_request_soldier);
        t_Find_Soldier = view.findViewById(R.id.text_find_soldier);

        isOpen = false;
        j_Add_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isOpen) {
                    j_Find_Team.hide();
                    j_Find_Teamplayer.hide();
                    j_Request_Soldier.hide();
                    j_Find_Soldier.hide();

                    t_Find_Team.setVisibility(View.GONE);
                    t_Find_Teamplayer.setVisibility(View.GONE);
                    t_Request_Soldier.setVisibility(View.GONE);
                    t_Find_Soldier.setVisibility(View.GONE);

                    isOpen = false;
                } else {
                    j_Find_Team.show();
                    j_Find_Teamplayer.show();
                    j_Request_Soldier.show();
                    j_Find_Soldier.show();

                    t_Find_Team.setVisibility(View.VISIBLE);
                    t_Find_Teamplayer.setVisibility(View.VISIBLE);
                    t_Request_Soldier.setVisibility(View.VISIBLE);
                    t_Find_Soldier.setVisibility(View.VISIBLE);

                    isOpen = true;
                }
            }
        });
        // 큰 게시글 버튼을 눌렀을 경우, 내부의 4개 버튼이 활성화.

        j_Find_Team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Job_Add_Find_Team.class));
            }
        });

        j_Find_Teamplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Job_Add_Find_TeamPlayer.class));
            }
        });

        j_Request_Soldier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Job_Add_Request_Soldier.class));
            }
        });

        j_Find_Soldier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Job_Add_Find_Soldier.class));
            }
        });
        // 각각의 버튼을 클릭 시, 해당하는 페이지에 대한 게시글 작성페이지로 넘어가는 부분.

        return view;
    }
}
