package com.QuickMatch.quickmatchv110;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.QuickMatch.quickmatchv110.JobPage.Job_R_Soldier_Info;


public class Job_Request_Soldier_Chating extends AppCompatActivity {

    private final int MY_PERMISSION_REQUEST_SMS = 1001;

    private Button job_send_message;
    private EditText job_send_phoneNum, job_send_contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_request_soldier_chating);

        Intent getIntent = getIntent();

        //권한 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            //권한이 필요한 이유 설명
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)){

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("info");
                builder.setMessage("This app won't work properly unless you grant SMS permission.");
                builder.setIcon(android.R.drawable.ic_dialog_info);

                builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //권한 요청
                        ActivityCompat.requestPermissions(Job_Request_Soldier_Chating.this, new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                ActivityCompat.requestPermissions(this,  new String[] {Manifest.permission.SEND_SMS}, MY_PERMISSION_REQUEST_SMS);
            }
        }

        job_send_phoneNum = findViewById(R.id.request_soldier_chating_phoneNum);
        job_send_contents = findViewById(R.id.request_soldier_chating_contents);
        job_send_message = findViewById(R.id.request_soldier_chating_send);

        job_send_phoneNum.setText(getIntent.getStringExtra(Job_R_Soldier_Info.jobRequestSoldier));  // 게시글의 전화번호를 전달받아 자동으로 입력
        job_send_phoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());       // 전화번호 입력 시, 자동으로 '-' 생성

        //버튼 클릭이벤트
        job_send_message.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //입력한 값을 가져와 변수에 담는다
                String phoneNum = job_send_phoneNum.getText().toString();
                String sms = job_send_contents.getText().toString();

                try {
                    //전송
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNum, null, sms, null, null);
                    Toast.makeText(getApplicationContext(), "전송 완료!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "SMS faild, please try again later!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
    }

    //권한 요청 결과 가져오기
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_SMS : {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"permission granted.",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
