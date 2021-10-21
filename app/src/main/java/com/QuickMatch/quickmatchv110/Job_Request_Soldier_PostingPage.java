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

import com.QuickMatch.quickmatchv110.JobPage.Job_R_Soldier;
import com.QuickMatch.quickmatchv110.JobPage.Job_R_Soldier_Info;
import com.google.firebase.auth.FirebaseAuth;


public class Job_Request_Soldier_PostingPage extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Toolbar job_posting_page_toolbar;
    private Job_R_Soldier job_request_soldier;
    private TextView posting_name, job_title, job_city, job_position, job_content, job_phoneNum;
    private Button j_inviting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_request_soldier_postingpage);

        Intent getIntent = getIntent();
        job_request_soldier = getIntent.getParcelableExtra(Job_R_Soldier_Info.jobRequestSoldier);
        job_posting_page_toolbar = findViewById(R.id.m_Toolbar);
        setSupportActionBar(job_posting_page_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        posting_name = findViewById(R.id.request_soldier_posting_name);
        job_title = findViewById(R.id.request_soldier_posting_title);
        job_city = findViewById(R.id.request_soldier_posting_city);
        job_position = findViewById(R.id.request_soldier_posting_position);
        job_content = findViewById(R.id.request_soldier_posting_contents);
        job_phoneNum = findViewById(R.id.request_soldier_posting_phoneNum);
        j_inviting = findViewById(R.id.request_soldier_invite);

        if (job_request_soldier != null) {
            posting_name.setText("작성자 : " + job_request_soldier.getNickname());
            job_title.setText("제목 : " + job_request_soldier.getTitle());
            job_city.setText("희망 지역 : " + job_request_soldier.getCity()+" "+job_request_soldier.getState());
            job_position.setText("희망 포지션 : " + job_request_soldier.getPosition());
            job_content.setText("내용 : " + String.valueOf(job_request_soldier.getContents()));
            job_phoneNum.setText("연락처 : " + job_request_soldier.getPhoneNum());
        }

        j_inviting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (job_request_soldier.getPost_ID().equals(mAuth.getCurrentUser().getUid()))
                    Toast.makeText(Job_Request_Soldier_PostingPage.this, "메세지는 본인에게 보낼 수 없습니다.", Toast.LENGTH_SHORT).show();
                else {
                    Intent intent = new Intent(Job_Request_Soldier_PostingPage.this, Job_Request_Soldier_Chating.class);
                    intent.putExtra(Job_R_Soldier_Info.jobRequestSoldier, job_request_soldier.getPhoneNum());
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