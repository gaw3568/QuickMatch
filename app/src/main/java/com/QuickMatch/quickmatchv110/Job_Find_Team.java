package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.JobPage.Job_F_Team_Info;
import com.QuickMatch.quickmatchv110.JobPage.Job_Team;
import com.QuickMatch.quickmatchv110.JobPage.Job_TeamRecyclerViewItemClickListener;
import com.QuickMatch.quickmatchv110.JobPage.Job_Team_Adapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Job_Find_Team extends Fragment implements Job_TeamRecyclerViewItemClickListener.OnItemClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView findTeamRecyclerView;
    private Job_Team_Adapter jobAdapter;
    private List<Job_Team> mDatas;

    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(Job_F_Team_Info.jobFindTeam)
                .orderBy(Job_F_Team_Info.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
                            for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String postID = String.valueOf(shot.get(Job_F_Team_Info.userID));
                                String nickname = String.valueOf(shot.get(Job_F_Team_Info.nickname));
                                String title = String.valueOf(shot.get(Job_F_Team_Info.title));
                                String city = String.valueOf(shot.get(Job_F_Team_Info.city));
                                String state = String.valueOf(shot.get(Job_F_Team_Info.state));
                                String position = String.valueOf(shot.get(Job_F_Team_Info.position));
                                String contents = String.valueOf(shot.get(Job_F_Team_Info.contents));

                                Job_Team data = new Job_Team(postID, nickname, title, city, state, position, contents);
                                mDatas.add(data);
                            }

                            jobAdapter = new Job_Team_Adapter(mDatas);
                            findTeamRecyclerView.setAdapter(jobAdapter);
                        }
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_find_team, container,false);
        findTeamRecyclerView = view.findViewById(R.id.job_view_find_team);
        findTeamRecyclerView.addOnItemTouchListener(new Job_TeamRecyclerViewItemClickListener(getActivity(), findTeamRecyclerView, this));

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), Job_Find_Team_PostingPage.class);
        intent.putExtra(Job_F_Team_Info.jobFindTeam, mDatas.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
