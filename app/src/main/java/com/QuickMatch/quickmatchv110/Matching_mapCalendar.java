package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.Post.Post;
import com.QuickMatch.quickmatchv110.Post.PostID;
import com.QuickMatch.quickmatchv110.Post_Adapter.PostAdapter;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;



public class Matching_mapCalendar extends AppCompatActivity implements View.OnClickListener, RecyclerViewItemClickListener.OnItemClickListener {

    final String[] date = {""};
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore;
    private RecyclerView mPostRecyclerView;
    private PostAdapter mAdapter;
    private List<Post> mDatas;

    CalendarView calendarView;
    Button btn_test;
    String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching_calendar);
        mStore = FirebaseFirestore.getInstance();

        Intent getIntent=getIntent();
        city = getIntent.getStringExtra("city");

        calendarView=(CalendarView)findViewById(R.id.calendarView);
        btn_test = (Button)findViewById(R.id.btn_test);
        mPostRecyclerView=findViewById(R.id.matching_recyclerview);

        mPostRecyclerView.addOnItemTouchListener(new RecyclerViewItemClickListener(this, mPostRecyclerView, this));

        //현재시간 불러오기
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/M/d");
        date[0] = format.format(Calendar.getInstance().getTime());

        findViewById(R.id.btn_post).setOnClickListener(this);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date[0] = i + "/" + (i1+1) +"/" + (i2);

            }
        });

        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("asd",date[0]);
                mDatas = new ArrayList<>();
                mStore.collection(PostID.post)
                        .orderBy(PostID.timestamp, Query.Direction.DESCENDING)
                        .whereEqualTo("local", city)

                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                if (queryDocumentSnapshots != null) {
                                    mDatas.clear();
                                    for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                        Map<String, Object> shot = snap.getData();
                                        String datepicker = String.valueOf(shot.get(PostID.datepicker));

                                        if(date[0].equals(datepicker)) {
                                            String documentID = String.valueOf(shot.get(PostID.Post_ID));
                                            String nickname = String.valueOf(shot.get(UserID.nickname));
                                            String title = String.valueOf(shot.get(PostID.title));
                                            String contents = String.valueOf(shot.get(PostID.contents));
                                            String teamname = String.valueOf(shot.get(PostID.teamname));
                                            String phoneNumber = String.valueOf(shot.get(PostID.post_phoneNumber));
                                            String uniform = String.valueOf(shot.get(PostID.post_uniform));
                                            String manner = String.valueOf(shot.get(PostID.post_manner));
                                            String city = String.valueOf(shot.get(PostID.local));
                                            Post data = new Post(documentID, nickname, title, contents,teamname, datepicker, phoneNumber, uniform, manner, city);
                                            mDatas.add(data);
                                        }

                                    }

                                    mAdapter = new PostAdapter(mDatas);
                                    mPostRecyclerView.setAdapter(mAdapter);

                                }
                            }
                        });
            }
        });

        //받아온 string
        //if(string == 불러올 게시글 지역)
        //게시글
    }

    @Override
    protected void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(PostID.post)
                .whereEqualTo("local", city)
                .orderBy(PostID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
                            for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String datepicker = String.valueOf(shot.get(PostID.datepicker));
                                if(date[0].equals(datepicker)) {

                                    String documentID = String.valueOf(shot.get(PostID.Post_ID));
                                    String nickname = String.valueOf(shot.get(UserID.nickname));
                                    String title = String.valueOf(shot.get(PostID.title));
                                    String contents = String.valueOf(shot.get(PostID.contents));
                                    String teamname = String.valueOf(shot.get(PostID.teamname));
                                    String phoneNumber = String.valueOf(shot.get(PostID.post_phoneNumber));
                                    String uniform = String.valueOf(shot.get(PostID.post_uniform));
                                    String manner = String.valueOf(shot.get(PostID.post_manner));
                                    String city = String.valueOf(shot.get(PostID.local));
                                    Post data = new Post(documentID, nickname, title, contents,teamname, datepicker, phoneNumber, uniform, manner,city);
                                    mDatas.add(data);
                                }
                            }
                            mAdapter = new PostAdapter(mDatas);
                            mPostRecyclerView.setAdapter(mAdapter);

                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra("city",city);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, Post_Show.class);
        intent.putExtra(PostID.Post_ID, mDatas.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
