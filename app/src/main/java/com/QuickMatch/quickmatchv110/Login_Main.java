package com.QuickMatch.quickmatchv110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_Main extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText l_Main_Email, l_Main_Password;
    private CheckBox auto_login;

    private SharedPreferences lp;
    private SharedPreferences.Editor lEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        l_Main_Email = findViewById(R.id.l_Main_email);
        l_Main_Password = findViewById(R.id.l_Main_password);
        auto_login = findViewById(R.id.auto_login);

        findViewById(R.id.l_Main_signup).setOnClickListener(this);
        findViewById(R.id.l_Main_login).setOnClickListener(this);

        lp = getSharedPreferences("login", MODE_PRIVATE);
        lEdit = lp.edit();

        lEdit.putBoolean("auto_login", false);
        lEdit.commit();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {//뷰들의 아이디를 가져옴
            case R.id.l_Main_signup://회원가입 버튼을 눌렀을때
                startActivity(new Intent(this, Login_Signup.class));
                break;

            case R.id.l_Main_login://로그인 버튼을 눌렀을때
                if(l_Main_Email.getText().toString().equals("")){//이메일이 빈칸이면
                    Toast.makeText(Login_Main.this, "이메일을 입력하세요!", Toast.LENGTH_SHORT).show();
                }
                else if(l_Main_Password.getText().toString().equals("")){
                    Toast.makeText(Login_Main.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                }
                else {//이메일 , 비밀번호가 빈칸이 아니어야 함
                    mAuth.signInWithEmailAndPassword(l_Main_Email.getText().toString(), l_Main_Password.getText().toString())//이메일과 비밀번호를 가져옴
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            Intent intent = new Intent(Login_Main.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            //로그인 액티비티 -> 메인 액티비티로 넘어갈때 로그인 액티비티 종료

                                            //체크가 되어 있으면 Sharedreference에
                                            if (auto_login.isChecked()) {
                                                lEdit.putBoolean("auto_login", true);
                                            } else {
                                                lEdit.putBoolean("auto_login", false);
                                            }
                                            lEdit.commit();

                                            Toast.makeText(Login_Main.this, "로그인 성공: ", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        }
                                    } else {
                                        Toast.makeText(Login_Main.this, "Login error!!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                break;



        }
    }
}
