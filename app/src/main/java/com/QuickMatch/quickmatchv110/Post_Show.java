package com.QuickMatch.quickmatchv110;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.QuickMatch.quickmatchv110.Post.Post;
import com.QuickMatch.quickmatchv110.Post.PostID;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class Post_Show extends AppCompatActivity {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private TextView mTitleText, mContentsText, mNameText,mCityText, mPhoneNumber, mUniform, mManner;
    private String id;
    private Button mChatButton;
    private Post postID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__show);

        mTitleText = findViewById(R.id.post_show_title);
        mContentsText = findViewById(R.id.post_show_contents);
        mNameText = findViewById(R.id.post_show_name);
        mCityText = findViewById(R.id.post_show_city);
        mChatButton = findViewById(R.id.post_show_btn_chat);
        mPhoneNumber = findViewById(R.id.post_show_phoneNumber);
        mUniform = findViewById(R.id.post_show_uniform);
        mManner = findViewById(R.id.post_show_manner);

        final Intent getIntent = getIntent();
        postID = getIntent.getParcelableExtra(PostID.Post_ID);
        id = postID.getDocumentID();

        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Post_Show.this, ChatActivity.class);
                intent.putExtra(PostID.Post_ID, postID.getmPhoneNumber());
                startActivity(intent);
            }
        });



        mStore.collection(PostID.post).document(id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            if(task.getResult().exists()) {
                                if (task.getResult() != null) {
                                    Map<String, Object> snap = task.getResult().getData();
                                    String title = String.valueOf(snap.get(PostID.title));
                                    String contents = String.valueOf(snap.get(PostID.contents));
                                    String name = String.valueOf(snap.get(UserID.nickname));
                                    String phone = String.valueOf(snap.get(PostID.post_phoneNumber));
                                    String uniform = String.valueOf(snap.get(PostID.post_uniform));
                                    String manner = String.valueOf(snap.get(PostID.post_manner));
                                    String city = String.valueOf(snap.get(PostID.local));

                                    mTitleText.setText(title);
                                    mContentsText.setText(contents);
                                    mNameText.setText(name);
                                    mPhoneNumber.setText(phone);
                                    mUniform.setText(uniform);
                                    mManner.setText(manner);
                                    mCityText.setText(city);
                                }
                            }else{
                                Toast.makeText(Post_Show.this,"삭제된 문서입니다.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
