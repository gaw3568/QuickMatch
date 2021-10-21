package com.QuickMatch.quickmatchv110;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.QuickMatch.quickmatchv110.Post.PostID;
import com.QuickMatch.quickmatchv110.Teampage.Teaminfo;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private TextView mCity;
    private String nickname;
    private EditText mTitle, mContexts, mPhoneNumber, mUniform, mManner;
    private DatePicker mDatePicker;
    private String str_mDatePicker;
    private Button mButton;
    private List<String> teamList = new ArrayList<>();
    private List<String> teamidList = new ArrayList<>();
    String mteamId, city;
    String[] arrTeam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        Intent getIntent=getIntent();
        city = getIntent.getStringExtra("city");

        mTitle = findViewById(R.id.post_title_edit);
        mContexts = findViewById(R.id.post_contest_edit);
        mDatePicker = findViewById(R.id.post_date);
        mPhoneNumber = findViewById(R.id.post_phoneNumber);
        mUniform = findViewById(R.id.post_uniform);
        mManner = findViewById(R.id.post_manner);
        mCity = findViewById(R.id.post_city);

        mCity.setText(city);    // 강북 넣기

        findViewById(R.id.btn_post_save).setOnClickListener(this);
        mButton = findViewById(R.id.post_teamname_edit);

        mButton.setOnClickListener(this);


        mPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());       // 전화번호 입력 시, 자동으로 '-' 생성

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
    }
    @Override
    public void onStart() {
        super.onStart();
        mStore.collection(Teaminfo.team)
                .whereArrayContains(Teaminfo.memberlist, mAuth.getCurrentUser().getUid()).get()//배열에 포함된 값이 있으면
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            teamList.clear();
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = document.getData();
                                String teamName = String.valueOf(shot.get(Teaminfo.teamName));
                                String teamId = String.valueOf(shot.get(Teaminfo.teamID));
                                //크
                                teamList.add(teamName);
                                teamidList.add(teamId);
                            }
                        } else {
                            Log.d("asdf", "Error getting documents: ");
                        }
                    }
                });



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_post_save :
                if (mAuth.getCurrentUser() != null) {
                    String postID = mStore.collection(PostID.post).document().getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put(PostID.Post_ID, postID);
                    data.put(UserID.nickname, nickname);
                    data.put(PostID.title, mTitle.getText().toString());
                    data.put(PostID.contents, mContexts.getText().toString());
                    data.put(PostID.timestamp, FieldValue.serverTimestamp());
                    data.put(PostID.post_phoneNumber, mPhoneNumber.getText().toString());
                    data.put(PostID.post_uniform, mUniform.getText().toString());
                    data.put(PostID.post_manner, mManner.getText().toString());
                    data.put(PostID.local, mCity.getText().toString());
                    str_mDatePicker = mDatePicker.getYear() + "/" + (mDatePicker.getMonth() + 1) + "/" + mDatePicker.getDayOfMonth();
                    data.put(PostID.datepicker, str_mDatePicker);
                    data.put(PostID.teamname,mButton.getText().toString());
                    data.put(PostID.teamID, mteamId);
                    mStore.collection(PostID.post).document(postID).set(data, SetOptions.merge());
                    finish();
                }
                break;

            case R.id.post_teamname_edit :
                arrTeam = teamList.toArray(new String[0]);
                final AlertDialog.Builder mteamList = new AlertDialog.Builder(PostActivity.this);
                mteamList.setSingleChoiceItems(arrTeam, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mButton.setText(arrTeam[i]);
                        mteamId = teamidList.get(i);
                        dialogInterface.dismiss();

                    }
                });
                mteamList.show();
                break;

        }
    }
}
