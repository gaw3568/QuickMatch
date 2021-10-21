package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.QuickMatch.quickmatchv110.JobPage.Job_F_Soldier;
import com.QuickMatch.quickmatchv110.JobPage.Job_F_Soldier_Info;
import com.QuickMatch.quickmatchv110.JobPage.Job_R_Soldier_Info;
import com.google.firebase.auth.FirebaseAuth;

public class Job_Find_Soldier_PostingPage extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Toolbar job_posting_page_toolbar;
    private Job_F_Soldier job_find_soldier;
    private TextView posting_name, job_title, job_city, job_position, job_content, job_phoneNum;
    private Button j_requesting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_find_soldier_postingpage);

        Intent getIntent = getIntent();
        job_find_soldier = getIntent.getParcelableExtra(Job_F_Soldier_Info.jobfindSoldier);
        job_posting_page_toolbar = findViewById(R.id.m_Toolbar);
        setSupportActionBar(job_posting_page_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        posting_name = findViewById(R.id.find_soldier_posting_name);
        job_title = findViewById(R.id.find_soldier_posting_title);
        job_city = findViewById(R.id.find_soldier_posting_city);
        job_position = findViewById(R.id.find_soldier_posting_position);
        job_content = findViewById(R.id.find_soldier_posting_contents);
        job_phoneNum = findViewById(R.id.find_soldier_posting_phoneNum);
        j_requesting = findViewById(R.id.find_soldier_request);

        if (job_find_soldier != null) {
            posting_name.setText("작성자 : " + job_find_soldier.getNickname());
            job_title.setText("제목 : " + job_find_soldier.getTitle());
            job_city.setText("활동 지역 : " + job_find_soldier.getCity()+" "+job_find_soldier.getState());
            job_position.setText("필요 포지션 : " + job_find_soldier.getPosition());
            job_content.setText("내용 : " + String.valueOf(job_find_soldier.getContents()));
            job_phoneNum.setText("연락처 : " + job_find_soldier.getPhoneNum());
        }

        j_requesting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (job_find_soldier.getPost_ID().equals(mAuth.getCurrentUser().getUid()))
                    Toast.makeText(Job_Find_Soldier_PostingPage.this, "메세지는 본인에게 보낼 수 없습니다.", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(Job_Find_Soldier_PostingPage.this, Job_Request_Soldier_Chating.class);
                    intent.putExtra(Job_R_Soldier_Info.jobRequestSoldier, job_find_soldier.getPhoneNum());
                    startActivity(intent);
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) { //toolbar의 back키 눌렀을 때 동작
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
