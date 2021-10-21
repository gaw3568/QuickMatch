package com.QuickMatch.quickmatchv110;

import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.QuickMatch.quickmatchv110.JobPage.Job_R_Soldier_Info;
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

public class Job_Add_Request_Soldier extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private String nickname;            // 현재 로그인 되어있는 사용자인지 확인하기 위해 변수 설정.
    private EditText title, contents, phoneNum;
    private Button j_city, j_state, j_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_add_request_soldier);

        title = findViewById(R.id.add_request_soldier_title);
        contents = findViewById(R.id.add_request_soldier_content);
        phoneNum = findViewById(R.id.add_request_soldier_phoneNum);

        j_city = findViewById(R.id.j_city);
        j_state = findViewById(R.id.j_state);
        j_position = findViewById(R.id.j_position);

        j_city.setOnClickListener(this);
        j_state.setOnClickListener(this);
        j_position.setOnClickListener(this);

        phoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());       // 전화번호 입력 시, 자동으로 '-' 생성

        findViewById(R.id.add_request_soldier_submit).setOnClickListener(this);

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
    public void onClick(View view) {
        switch (view.getId()) {//뷰들의 아이디를 가져옴
            case R.id.j_city://첫번째 지역 버튼을 눌렀을때(서울 경기 등등)
                AlertDialog.Builder citydialog = new AlertDialog.Builder(Job_Add_Request_Soldier.this);
                final String city[] = {"서울", "경기"};
                // , "강원", "인천", "대전", "울산", "광주", "세종", "부산", "대구", "충북", "충남", "전북", "전남", "경북", "경남", "제주"

                citydialog.setSingleChoiceItems(city, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        j_city.setText(city[i]);

                        if (city[i].equals("서울")) {
                            j_state.setText("중랑");
                        } else if (city[i].equals("경기")) {
                            j_state.setText("남양주");
                        }

                        dialogInterface.dismiss();
                    }
                });

                citydialog.show();
                break;
            case R.id.j_state://두번째 지역 버튼을 눌렀을때(중랑구, 남양주시)
                AlertDialog.Builder statedialog = new AlertDialog.Builder(Job_Add_Request_Soldier.this);
                final String seoul[] = {"중랑", "강남", "강북"};
                final String gyeonggi[] = {"가평", "고양", "과천", "광명", "광주", "구리", "군포", "김포", "남양주"
                        , "동두천", "부천", "성남", "수원", "시흥", "안산", "안성", "안양", "양주", "양평"
                        , "여주", "연쳔", "오산", "용인", "의왕", "의정부", "이천", "파주"
                        , "평택", "포천", "하남", "화성"};
                String[] state = seoul;
                switch (j_city.getText().toString()) {
                    case "경기":
                        state = gyeonggi;
                        break;
                }

                final String[] finalState = state;
                statedialog.setSingleChoiceItems(state, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        j_state.setText(finalState[i]);
                        dialogInterface.dismiss();
                    }
                });

                statedialog.show();
                break;

            case R.id.j_position://팀 수준 버튼을 눌렀을때
                AlertDialog.Builder positionDialog = new AlertDialog.Builder(Job_Add_Request_Soldier.this);
                final String position[] = {"FW", "MF", "DF", "GK"};
                positionDialog.setSingleChoiceItems(position, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        j_position.setText(position[i]);

                        dialogInterface.dismiss();
                    }
                });
                positionDialog.show();
                break;

            case R.id.add_request_soldier_submit:
                if(mAuth.getCurrentUser() != null) {         // 게시글 작성
                    String postID = mStore.collection(Job_R_Soldier_Info.jobRequestSoldier).document().getId();
                    Map<String, Object> data = new HashMap<>();
                    data.put(Job_R_Soldier_Info.jobPostID, mAuth.getCurrentUser().getUid());
                    data.put(Job_R_Soldier_Info.nickname, nickname);
                    data.put(Job_R_Soldier_Info.title, title.getText().toString());
                    data.put(Job_R_Soldier_Info.city, j_city.getText().toString());
                    data.put(Job_R_Soldier_Info.state, j_state.getText().toString());
                    data.put(Job_R_Soldier_Info.position, j_position.getText().toString());
                    data.put(Job_R_Soldier_Info.contents, contents.getText().toString());
                    data.put(Job_R_Soldier_Info.phoneNum, phoneNum.getText().toString());
                    data.put(Job_R_Soldier_Info.timestamp, FieldValue.serverTimestamp());
                    mStore.collection(Job_R_Soldier_Info.jobRequestSoldier).document(postID).set(data, SetOptions.merge());
                    Toast.makeText(Job_Add_Request_Soldier.this, "작성 완료", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                }
        }
    }
}
