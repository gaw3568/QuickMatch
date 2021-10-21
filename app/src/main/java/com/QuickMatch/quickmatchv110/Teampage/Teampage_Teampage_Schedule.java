package com.QuickMatch.quickmatchv110.Teampage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.Post.Post;
import com.QuickMatch.quickmatchv110.Post.PostID;
import com.QuickMatch.quickmatchv110.Post_Adapter.PostAdapter;
import com.QuickMatch.quickmatchv110.R;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Teampage_Teampage_Schedule extends Fragment {
    private Bundle bundle;
    private Team myteam;
    private String id;
    private RecyclerView mPostRecyclerView;

    private List<Post> mDatas = new ArrayList<>();
    private PostAdapter mAdapter;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();

    @Override
    public void onStart() {
        super.onStart();

        mStore.collection(PostID.post)
                .orderBy(PostID.timestamp, Query.Direction.DESCENDING)
                .whereArrayContains(PostID.teamID, id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            if (mDatas.size() != 0) {
                                mDatas.clear();
                            }
                            for (DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String datepicker = String.valueOf(shot.get(PostID.datepicker));
                                String documentID = String.valueOf(shot.get(PostID.Post_ID));
                                String nickname = String.valueOf(shot.get(UserID.nickname));
                                String title = String.valueOf(shot.get(PostID.title));
                                String contents = String.valueOf(shot.get(PostID.contents));
                                String teamname = String.valueOf(shot.get(PostID.teamname));
                                String phoneNumber = String.valueOf(shot.get(PostID.post_phoneNumber));
                                String uniform = String.valueOf(shot.get(PostID.post_uniform));
                                String manner = String.valueOf(shot.get(PostID.post_manner));
                                String city = String.valueOf(shot.get(PostID.local));
                                Post data = new Post(documentID, nickname, title, contents, teamname, datepicker, phoneNumber, uniform, manner, city);
                                mDatas.add(data);

                            }

                            mAdapter = new PostAdapter(mDatas);
                            mPostRecyclerView.setAdapter(mAdapter);

                        }
                    }
                });


    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teampage_teampage_schedule, container, false);

        bundle = getArguments();
        myteam = bundle.getParcelable(Teaminfo.team);
        id = myteam.getTeamID();

        mPostRecyclerView = view.findViewById(R.id.teampage_schedule_recyclerview);
        return view;
    }
}
