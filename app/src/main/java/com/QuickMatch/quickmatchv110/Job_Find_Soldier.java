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

import com.QuickMatch.quickmatchv110.JobPage.Job_F_Soldier;
import com.QuickMatch.quickmatchv110.JobPage.Job_F_Soldier_Adapter;
import com.QuickMatch.quickmatchv110.JobPage.Job_F_Soldier_Info;
import com.QuickMatch.quickmatchv110.JobPage.Job_FindSoldierRecyclerViewItemClickListener;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Job_Find_Soldier extends Fragment implements Job_FindSoldierRecyclerViewItemClickListener.OnItemClickListener {

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView findSoldierRecyclerView;
    private Job_F_Soldier_Adapter jobAdapter;
    private List<Job_F_Soldier> mDatas;

    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(Job_F_Soldier_Info.jobfindSoldier)
                .orderBy(Job_F_Soldier_Info.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
                            for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String postID = String.valueOf(shot.get(Job_F_Soldier_Info.jobPostID));
                                String nickname = String.valueOf(shot.get(Job_F_Soldier_Info.nickname));
                                String title = String.valueOf(shot.get(Job_F_Soldier_Info.title));
                                String city = String.valueOf(shot.get(Job_F_Soldier_Info.city));
                                String state = String.valueOf(shot.get(Job_F_Soldier_Info.state));
                                String position = String.valueOf(shot.get(Job_F_Soldier_Info.position));
                                String contents = String.valueOf(shot.get(Job_F_Soldier_Info.contents));
                                String phoneNum = String.valueOf(shot.get(Job_F_Soldier_Info.phoneNum));

                                Job_F_Soldier data = new Job_F_Soldier(postID, nickname, title, city, state, position, contents, phoneNum);
                                mDatas.add(data);
                            }

                            jobAdapter = new Job_F_Soldier_Adapter(mDatas);
                            findSoldierRecyclerView.setAdapter(jobAdapter);

                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_find_soldier, container,false);
        findSoldierRecyclerView = view.findViewById(R.id.job_view_find_soldier);
        findSoldierRecyclerView.addOnItemTouchListener(new Job_FindSoldierRecyclerViewItemClickListener(getActivity(), findSoldierRecyclerView, this));

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), Job_Find_Soldier_PostingPage.class);
        intent.putExtra(Job_F_Soldier_Info.jobfindSoldier, mDatas.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
