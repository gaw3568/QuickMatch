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

import com.QuickMatch.quickmatchv110.JobPage.Job_TeamPlayer;
import com.QuickMatch.quickmatchv110.JobPage.Job_TeamPlayer_Info;
import com.google.firebase.auth.FirebaseAuth;

public class Job_Find_TeamPlayer_PostingPage extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Toolbar job_posting_page_toolbar;
    private Job_TeamPlayer job_teamPlayer;
    private TextView posting_name, job_title, job_city, job_position, job_content, job_teamName;
    private Button j_requesting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_find_teamplayer_postingpage);

        Intent getIntent = getIntent();
        job_teamPlayer = getIntent.getParcelableExtra(Job_TeamPlayer_Info.jobFindTeamplayer);
        job_posting_page_toolbar = findViewById(R.id.m_Toolbar);
        setSupportActionBar(job_posting_page_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        posting_name = findViewById(R.id.find_teamplayer_posting_name);
        job_title = findViewById(R.id.find_teamplayer_posting_title);
        job_city = findViewById(R.id.find_teamplayer_posting_city);
        job_position = findViewById(R.id.find_teamplayer_posting_position);
        job_content = findViewById(R.id.find_teamplayer_posting_contents);
        j_requesting = findViewById(R.id.find_teamplayer_request);
        job_teamName = findViewById(R.id.find_teamplayer_posting_teamName);

        if (job_teamPlayer != null) {
            posting_name.setText("작성자 : " + job_teamPlayer.getNickname());
            job_title.setText("제목 : " + job_teamPlayer.getTitle());
            job_city.setText("활동 지역 : " + job_teamPlayer.getCity()+" "+job_teamPlayer.getState());
            job_position.setText("필요 포지션 : " + job_teamPlayer.getPosition());
            job_content.setText("내용 : " + String.valueOf(job_teamPlayer.getContents()));
            job_teamName.setText("팀 이름 : " + job_teamPlayer.getTeamName());
        }

        j_requesting.setOnClickListener(this);
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
    public void onClick(View v) {
        if (job_teamPlayer.getUserID().equals(mAuth.getCurrentUser().getUid()))
            Toast.makeText(Job_Find_TeamPlayer_PostingPage.this, "작성자는 신청할 수 없습니다.", Toast.LENGTH_SHORT).show();
        else {
            // intent = new Intent(Job_Find_TeamPlayer_PostingPage.this, Job_Request_Soldier_Chating.class);
            //intent.putExtra(Job_TeamPlayer_Info.jobFindTeamplayer, job_teamPlayer.getTeamID());
            //startActivity(intent);
        }
    }
}