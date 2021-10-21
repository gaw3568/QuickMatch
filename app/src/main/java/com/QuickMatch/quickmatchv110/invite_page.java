package com.QuickMatch.quickmatchv110;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.QuickMatch.quickmatchv110.Teampage.Teaminfo;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class invite_page extends AppCompatActivity {
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    Button accept;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_page);

        linearLayout = findViewById(R.id.invite_linear);
        accept = findViewById(R.id.accept);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(invite_page.this);
                builder.setTitle("초대 수락. ");
                builder.setMessage("팀 초대를 수락하시겠습니까?");

                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //LRvhsLBC8ZcG5GgPiy5m

                                mStore.collection(Teaminfo.team).document("LRvhsLBC8ZcG5GgPiy5m")
                                        .update(Teaminfo.memberlist, FieldValue.arrayUnion("1VNETO0HSHbZwPwXXJClSWqEwNs1"));

                                mStore.collection(UserID.user).document(mAuth.getCurrentUser().getUid())
                                        .update(UserID.temalist, FieldValue.arrayUnion("LRvhsLBC8ZcG5GgPiy5m"));

                                linearLayout.setVisibility(View.GONE);

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
        });

    }
}
