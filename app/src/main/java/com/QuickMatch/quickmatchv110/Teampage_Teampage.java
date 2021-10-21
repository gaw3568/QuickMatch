package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.QuickMatch.quickmatchv110.Teampage.Team;
import com.QuickMatch.quickmatchv110.Teampage.Teaminfo;
import com.QuickMatch.quickmatchv110.Teampage.Teampage_TeamNotice_post;
import com.QuickMatch.quickmatchv110.Teampage.Teampage_Teamboard_post;
import com.QuickMatch.quickmatchv110.Teampage.Teampage_Teampage_Adapter;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class Teampage_Teampage extends AppCompatActivity implements View.OnClickListener {
    private Toolbar t_Teampage_toolbar;
    private ViewPager vp;
    private Team myteam;
    private Teampage_Teampage_Adapter adapter;
    private TabLayout tab;
    private TextView text_addmain, text_addboaed;

    private FloatingActionButton addnotice, addboard, addbtn;

    private boolean isOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teampage_teampage);

        Intent getIntent = getIntent();
        myteam = getIntent.getParcelableExtra(Teaminfo.team);

        t_Teampage_toolbar = findViewById(R.id.m_Toolbar);
        t_Teampage_toolbar.setTitle("");
        setSupportActionBar(t_Teampage_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addbtn = findViewById(R.id.teampage_add_btn);
        addboard = findViewById(R.id.teampage_teampage_addboard);
        addnotice = findViewById(R.id.teampage_teampage_addnotice);

        text_addboaed = findViewById(R.id.text_teampage_addboard);
        text_addmain = findViewById(R.id.text_teampage_addnotice);

        vp = findViewById(R.id.teampage_teampage_viewpager);
        adapter = new Teampage_Teampage_Adapter(getSupportFragmentManager(), myteam);

        vp.setAdapter(adapter);
        tab = findViewById(R.id.teampage_teampage_tab);
        tab.setupWithViewPager(vp);



        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2 || position == 3) {
                    addbtn.hide();
                    addboard.hide();
                    addnotice.hide();
                    text_addboaed.setVisibility(View.GONE);
                    text_addmain.setVisibility(View.GONE);
                    isOpen = false;
                } else {
                    addbtn.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        isOpen = false;
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {

                    addboard.hide();
                    addnotice.hide();

                    text_addboaed.setVisibility(View.GONE);
                    text_addmain.setVisibility(View.GONE);
                    if (true) {
                        //매니저라면 공지 보이게, 아니면 안보임
                    }
                    isOpen = false;
                } else {

                    addboard.show();
                    addnotice.show();
                    text_addboaed.setVisibility(View.VISIBLE);
                    text_addmain.setVisibility(View.VISIBLE);

                    isOpen = true;
                }
            }
        });
        addboard.setOnClickListener(this);
        addnotice.setOnClickListener(this);

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) { //toolbar의 back키 눌렀을 때 동작
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.teampage_teampage_addnotice:
                intent = new Intent(Teampage_Teampage.this, Teampage_TeamNotice_post.class);
                intent.putExtra(Teaminfo.team, myteam);
                startActivity(intent);
                isOpen = false;
                break;

            case R.id.teampage_teampage_addboard:
                intent = new Intent(Teampage_Teampage.this, Teampage_Teamboard_post.class);
                intent.putExtra(Teaminfo.team, myteam);
                startActivity(intent);
                isOpen = false;
                break;
        }
    }

}
