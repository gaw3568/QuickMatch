package com.QuickMatch.quickmatchv110.Teampage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;
import com.QuickMatch.quickmatchv110.Teampage_Teampage;
import com.bumptech.glide.Glide;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Teampage_Teampage_Notice extends Fragment {

    private Bundle bundle;
    private Team myteam;
    private String id;
    private TextView teamname, local, mNumber;
    private ImageView teamlogo;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView noticeRecyclerView;
    private List<TeamBoard> nDatas = new ArrayList<>();
    private NoticeAdapter tAdapter;


    @Override
    public void onStart() {
        super.onStart();

        mStore.collection(Teaminfo.team).document(id)
                .collection(TeamPostID.notice)
                .orderBy(TeamPostID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            nDatas.clear();
                            for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String postID =  String.valueOf(shot.get(TeamPostID.postID));
                                String title =  String.valueOf(shot.get(TeamPostID.title));
                                String content =  String.valueOf(shot.get(TeamPostID.contents));
                                String nickname =  String.valueOf(shot.get(TeamPostID.nickname));
                                Timestamp timestamp = (Timestamp) shot.get(TeamPostID.timestamp);

                                TeamBoard data = new TeamBoard(postID, nickname, title, content, timestamp);
                                nDatas.add(data);
                            }
                            tAdapter = new NoticeAdapter(nDatas);
                            noticeRecyclerView.setAdapter(tAdapter);
                        }
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teampage_teampage_notice, container, false);


        bundle = getArguments();
        myteam = bundle.getParcelable(Teaminfo.team);
        id = myteam.getTeamID();

        teamname = view.findViewById(R.id.teampage_T_teamname);
        local =  view.findViewById(R.id.teampage_T_local);
        mNumber =  view.findViewById(R.id.teampage_T_mNumber);
        teamlogo =  view.findViewById(R.id.teampage_T_logo);



        Glide.with(Teampage_Teampage_Notice.this)
                .load(myteam.getLogouri())
                .into(teamlogo);

        if (myteam != null) {
            teamname.setText(myteam.getTeamName());
            local.setText(myteam.getCity() + " " + myteam.getState());
            mNumber.setText(myteam.getMembernum() + "명");
        }


        noticeRecyclerView = view.findViewById(R.id.teampage_notice_recyclerview);
        return view;
    }
}
