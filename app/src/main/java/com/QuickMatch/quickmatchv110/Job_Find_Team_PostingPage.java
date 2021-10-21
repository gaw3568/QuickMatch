package com.QuickMatch.quickmatchv110;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.QuickMatch.quickmatchv110.JobPage.Job_F_Team_Info;
import com.QuickMatch.quickmatchv110.JobPage.Job_Team;
import com.QuickMatch.quickmatchv110.Teampage.Team;
import com.QuickMatch.quickmatchv110.Teampage.Teaminfo;
import com.QuickMatch.quickmatchv110.USER.User;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Job_Find_Team_PostingPage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private Toolbar job_posting_page_toolbar;
    private Job_Team job_team;
    private TextView posting_name, job_title, job_city, job_position, job_content;
    private Button j_inviting;
    private String userID;
    private List<Team> teamList = new ArrayList<>();
    private List<String> teamnameList = new ArrayList<>();
    private String[] arrTeam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_find_team_postingpage);

        Intent getIntent = getIntent();
        job_team = getIntent.getParcelableExtra(Job_F_Team_Info.jobFindTeam);

        job_posting_page_toolbar = findViewById(R.id.m_Toolbar);
        setSupportActionBar(job_posting_page_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userID = job_team.getUserID();//초대받을 사람 ID

        posting_name = findViewById(R.id.find_team_posting_name);
        job_title = findViewById(R.id.find_team_posting_title);
        job_city = findViewById(R.id.find_team_posting_city);
        job_position = findViewById(R.id.find_team_posting_position);
        job_content = findViewById(R.id.find_team_posting_contents);
        j_inviting = findViewById(R.id.find_team_invite);

        if (job_team != null) {
            posting_name.setText("작성자 : " + job_team.getNickname());
            job_title.setText("제목 : " + job_team.getTitle());
            job_city.setText("활동 지역 : " + job_team.getCity() + " " + job_team.getState());
            job_position.setText("희망 포지션 : " + job_team.getPosition());
            job_content.setText("내용 : " + String.valueOf(job_team.getContents()));
        }

        j_inviting.setOnClickListener(this);

        mStore.collection(Teaminfo.team)
                .whereArrayContains(Teaminfo.memberlist, mAuth.getCurrentUser().getUid())
                .get()//배열에 포함된 값이 있으면
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            teamList.clear();
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = document.getData();
                                String teamId = String.valueOf(shot.get(Teaminfo.teamID));
                                String teamName = String.valueOf(shot.get(Teaminfo.teamName));
                                String introduce = String.valueOf(shot.get(Teaminfo.introduce));
                                String city = String.valueOf(shot.get(Teaminfo.city));
                                String state = String.valueOf(shot.get(Teaminfo.state));
                                String teamlevel = String.valueOf(shot.get(Teaminfo.teamlevel));
                                String captainID = String.valueOf(shot.get(Teaminfo.captainID));
                                String captainname = String.valueOf(shot.get(Teaminfo.captainname));
                                ArrayList memberlist = (ArrayList) shot.get(Teaminfo.memberlist);
                                ArrayList managerlist = (ArrayList) shot.get(Teaminfo.managerlist);
                                String logouri = String.valueOf(shot.get(Teaminfo.logouri));
                                int membernum = memberlist.size();

                                Team data = new Team(teamId, teamName, introduce, city,
                                        state, teamlevel, captainID, captainname, memberlist, managerlist, membernum, logouri);

                                teamList.add(data);
                                teamnameList.add(teamName);
                            }
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

    @Override
    public void onClick(View v) {
        if (userID.equals(mAuth.getCurrentUser().getUid())) {
            Toast.makeText(Job_Find_Team_PostingPage.this, "신청자는 초대할 수 없습니다.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            arrTeam = teamnameList.toArray(new String[0]);//팀이름 리스트
            final Team[] selectteam = new Team[1];
            AlertDialog.Builder builder = new AlertDialog.Builder(Job_Find_Team_PostingPage.this);
            builder.setTitle("초대할 팀을 선택해주세요. ");
            builder.setSingleChoiceItems(arrTeam, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectteam[0] = teamList.get(i);

                }
            });

            builder.setPositiveButton("예",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (selectteam[0].getMemberlist().contains(userID)) {
                                Toast.makeText(getApplicationContext(), job_team.getNickname() + " 선수는 이미 " + selectteam[0].getTeamName()
                                        + " 에 소속되어 있습니다. 다른 팀을 선택해 주세요.", Toast.LENGTH_LONG).show();
                            } else {
                                String invitingID = mStore.collection(Teaminfo.team).document(selectteam[0].getTeamID())
                                        .collection(Job_F_Team_Info.inviting).document().getId();//유저한테 넣어줘야함
                                String invitedID = mStore.collection(UserID.user).document(userID)
                                        .collection(Job_F_Team_Info.invited).document().getId();//팀페이지에 넣어줘야함

                                Map<String, Object> invited = new HashMap<>(); //유저한테 넣어줄 정보
                                invited.put(Teaminfo.teamID, selectteam[0].getTeamID());
                                invited.put(Teaminfo.teamName, selectteam[0].getTeamName());
                                invited.put(Teaminfo.city, selectteam[0].getCity());
                                invited.put(Teaminfo.state, selectteam[0].getState());
                                invited.put(Job_F_Team_Info.inviting, invitingID);
                                invited.put(Job_F_Team_Info.invited, invitedID);

                                Map<String, Object> inviting = new HashMap<>();//팀한테 넣어줄 정보
                                inviting.put(UserID.userCode, job_team.getUserID());
                                inviting.put(UserID.nickname, job_team.getNickname());
                                inviting.put(UserID.city,  job_team.getCity());
                                inviting.put(UserID.state, job_team.getState());
                                inviting.put(UserID.position, job_team.getPosition());
                                inviting.put(Job_F_Team_Info.invited, invitedID);
                                inviting.put(Job_F_Team_Info.inviting, invitingID);

                                mStore.collection(UserID.user).document(userID)
                                        .collection(Job_F_Team_Info.invited).document(invitedID).set(invited, SetOptions.merge());

                                mStore.collection(Teaminfo.team).document(selectteam[0].getTeamID())
                                        .collection(Job_F_Team_Info.inviting).document(invitingID).set(inviting, SetOptions.merge());


                            }
                        }
                    });
            builder.setNegativeButton("아니오",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
                        }
                    });
            builder.show();
        }
    }

}

