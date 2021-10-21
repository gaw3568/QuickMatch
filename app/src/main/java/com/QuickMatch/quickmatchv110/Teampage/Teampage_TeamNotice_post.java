package com.QuickMatch.quickmatchv110.Teampage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.QuickMatch.quickmatchv110.R;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Teampage_TeamNotice_post extends AppCompatActivity {

    private EditText title, content;
    private Button submit;
    private Team myteam;
    private String nickname;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teampage_team_notice_post);

        Intent getIntent = getIntent();
        myteam = getIntent.getParcelableExtra(Teaminfo.team);

        title = findViewById(R.id.teampage_notice_postitle);
        content = findViewById(R.id.teampage_notice_content);
        submit = findViewById(R.id.teampage_notice_submit);

        if(mAuth.getCurrentUser() != null) {
            mStore.collection(UserID.user).document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.getResult() != null) {
                                nickname = (String) task.getResult().getData().get(UserID.nickname);
                            }
                        }
                    });
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAuth.getCurrentUser() != null) {
                    String postID = mStore.collection(TeamPostID.notice).document().getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put(TeamPostID.title, title.getText().toString());
                    data.put(TeamPostID.contents, content.getText().toString());
                    data.put(TeamPostID.timestamp, FieldValue.serverTimestamp());
                    data.put(TeamPostID.nickname, nickname);
                    data.put(TeamPostID.userID, mAuth.getCurrentUser().getUid());

                    mStore.collection(Teaminfo.team).document(myteam.getTeamID())
                            .collection(TeamPostID.notice).document(postID).set(data, SetOptions.merge());
                    finish();
                }else{
                    Toast.makeText(Teampage_TeamNotice_post.this, "회원 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
