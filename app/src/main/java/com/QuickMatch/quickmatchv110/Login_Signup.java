package com.QuickMatch.quickmatchv110;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Login_Signup extends AppCompatActivity implements View.OnClickListener, TextWatcher, RadioGroup.OnCheckedChangeListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    private EditText l_Signup_Nickname, l_Signup_Email, l_Signup_Password, l_Signup_Age, l_Signup_Repassword, l_Signup_height;
    private TextView l_Signup_warn;
    private RadioGroup l_Signup_rg, l_Signup_rg2;
    private Button l_Signup_City, l_Signup_State;

    private String l_Signup_foot = "양발", l_Signup_position = "공격수";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_signup);

        l_Signup_Email = findViewById(R.id.l_Signup_Email);
        l_Signup_Password = findViewById(R.id.l_Signup_Password);
        l_Signup_Nickname = findViewById(R.id.l_Signup_Nickname);
        l_Signup_Age = findViewById(R.id.l_Signup_Age);
        l_Signup_Repassword = findViewById(R.id.l_Signup_RePassword);
        l_Signup_warn = findViewById(R.id.l_Signup_Warn);
        l_Signup_height = findViewById(R.id.l_Signup_H);

        l_Signup_rg = findViewById(R.id.l_Signup_RG);
        l_Signup_rg2 = findViewById(R.id.l_Signup_RG2);

        l_Signup_City = findViewById(R.id.l_Signup_City);
        l_Signup_State = findViewById(R.id.l_Signup_State);

        findViewById(R.id.l_Signup_Success).setOnClickListener(this);

        l_Signup_City.setOnClickListener(this);
        l_Signup_State.setOnClickListener(this);

        l_Signup_Password.addTextChangedListener(this);
        l_Signup_Repassword.addTextChangedListener(this);

        l_Signup_rg.setOnCheckedChangeListener(this);
        l_Signup_rg2.setOnCheckedChangeListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {//뷰들의 아이디를 가져옴
            case R.id.l_Signup_Success:
                if (l_Signup_Email.getText().toString().equals("")) {
                    Toast.makeText(Login_Signup.this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                } else if (l_Signup_Password.getText().toString().equals("")) {
                    Toast.makeText(Login_Signup.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                } else if (l_Signup_Age.getText().toString().equals("")) {
                    Toast.makeText(Login_Signup.this, "나이를 입력하세요!", Toast.LENGTH_SHORT).show();
                } else if (!l_Signup_Password.getText().toString().equals(l_Signup_Repassword.getText().toString())) {
                    Toast.makeText(Login_Signup.this, "비밀번호가 다릅니다!", Toast.LENGTH_SHORT).show();
                } else if (l_Signup_height.getText().toString().equals("")) {
                    Toast.makeText(Login_Signup.this, "키를 입력하세요!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(l_Signup_Email.getText().toString(), l_Signup_Password.getText().toString())
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            Map<String, Object> userMap = new HashMap<>();
                                            userMap.put(UserID.userCode, user.getUid());//유저 아이디(코드)
                                            userMap.put(UserID.nickname, l_Signup_Nickname.getText().toString());
                                            userMap.put(UserID.email, l_Signup_Email.getText().toString());//유저 이메일
                                            userMap.put(UserID.password, l_Signup_Password.getText().toString());//유저 비밀번호
                                            userMap.put(UserID.age, l_Signup_Age.getText().toString());//유저 나이
                                            userMap.put(UserID.imageuri, "");//프로필 사진 주소
                                            userMap.put(UserID.foot, l_Signup_foot);
                                            userMap.put(UserID.height, l_Signup_height.getText().toString());
                                            userMap.put(UserID.city, l_Signup_City.getText().toString());
                                            userMap.put(UserID.state, l_Signup_State.getText().toString());
                                            userMap.put(UserID.position, l_Signup_position);

                                            mStore.collection(UserID.user).document(user.getUid()).set(userMap, SetOptions.merge());
                                            Toast.makeText(Login_Signup.this, "회원가입 완료",Toast.LENGTH_SHORT).show();
                                            //merge덮어쓰기
                                            finish();
                                        }

                                    } else {//회원가입을 실패했을때
                                        Toast.makeText(Login_Signup.this, "Sign up error",
                                                Toast.LENGTH_SHORT).show();//SignupActivity 회원가입 액티비티
                                    }

                                }
                            });
                }
                break;
            case R.id.l_Signup_City:
                AlertDialog.Builder citydialog = new AlertDialog.Builder(Login_Signup.this);
                final String city[] = {"서울", "경기"};
                // , "강원", "인천", "대전", "울산", "광주", "세종", "부산", "대구", "충북", "충남", "전북", "전남", "경북", "경남", "제주"

                citydialog.setSingleChoiceItems(city, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        l_Signup_City.setText(city[i]);

                        if (city[i].equals("서울")) {
                            l_Signup_State.setText("중랑");
                        } else if (city[i].equals("경기")) {
                            l_Signup_State.setText("남양주");
                        }
                        dialogInterface.dismiss();
                    }
                });

                citydialog.show();
                break;
            case R.id.l_Signup_State:
                AlertDialog.Builder statedialog = new AlertDialog.Builder(Login_Signup.this);
                final String seoul[] = {"중랑", "강남", "강북"};
                final String gyeonggi[] = {"가평", "고양", "과천", "광명", "광주", "구리", "군포", "김포", "남양주"
                        , "동두천", "부천", "성남", "수원", "시흥", "안산", "안성", "안양", "양주", "양평"
                        , "여주", "연쳔", "오산", "용인", "의왕", "의정부", "이천", "파주"
                        , "평택", "포천", "하남", "화성"};
                String[] state = seoul;
                switch (l_Signup_City.getText().toString()) {
                    case "경기":
                        state = gyeonggi;
                        break;
                }

                final String[] finalState = state;
                statedialog.setSingleChoiceItems(state, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        l_Signup_State.setText(finalState[i]);
                        dialogInterface.dismiss();
                    }
                });
                statedialog.show();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (l_Signup_Password.getText().toString().equals(l_Signup_Repassword.getText().toString())) {
            l_Signup_warn.setVisibility(View.GONE);
        } else {
            l_Signup_warn.setVisibility(View.VISIBLE);
            l_Signup_warn.setText("비밀번호가 다릅니다.");
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()) {
            case R.id.l_Signup_RG:
                if (i == R.id.l_Signup_left) {
                    l_Signup_foot = "왼발";
                } else if (i == R.id.l_Signup_right) {
                    l_Signup_foot = "오른발";
                } else {
                    l_Signup_foot = "양발";
                }
                break;

            case R.id.l_Signup_RG2:
                if (i == R.id.l_Signup_GK) {
                    l_Signup_position = "골키퍼";
                } else if (i == R.id.l_Signup_DF) {
                    l_Signup_position = "수비수";
                } else if (i == R.id.l_Signup_MF) {
                    l_Signup_position = "미드필더";
                } else {
                    l_Signup_position = "공격수";
                }
                break;
        }
    }
}
