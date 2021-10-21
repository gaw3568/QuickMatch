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

import com.QuickMatch.quickmatchv110.JobPage.Job_TeamPlayer;
import com.QuickMatch.quickmatchv110.JobPage.Job_TeamPlayerRecyclerViewItemClickListener;
import com.QuickMatch.quickmatchv110.JobPage.Job_TeamPlayer_Adapter;
import com.QuickMatch.quickmatchv110.JobPage.Job_TeamPlayer_Info;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Job_Find_TeamPlayer extends Fragment implements Job_TeamPlayerRecyclerViewItemClickListener.OnItemClickListener{

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView findTeamPlayerRecyclerView;
    private Job_TeamPlayer_Adapter mAdapter;
    private List<Job_TeamPlayer> mDatas;

    @Override
    public void onStart() {
        super.onStart();
        mDatas = new ArrayList<>();
        mStore.collection(Job_TeamPlayer_Info.jobFindTeamplayer)
                .orderBy(Job_TeamPlayer_Info.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            mDatas.clear();
                            for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String postID = String.valueOf(shot.get(Job_TeamPlayer_Info.userID));
                                String nickname = String.valueOf(shot.get(Job_TeamPlayer_Info.nickname));
                                String title = String.valueOf(shot.get(Job_TeamPlayer_Info.title));
                                String city = String.valueOf(shot.get(Job_TeamPlayer_Info.city));
                                String state = String.valueOf(shot.get(Job_TeamPlayer_Info.state));
                                String position = String.valueOf(shot.get(Job_TeamPlayer_Info.position));
                                String contents = String.valueOf(shot.get(Job_TeamPlayer_Info.contents));
                                String teamName = String.valueOf(shot.get(Job_TeamPlayer_Info.teamName));
                                String teamID = String.valueOf(shot.get(Job_TeamPlayer_Info.teamID));

                                Job_TeamPlayer data = new Job_TeamPlayer(postID, nickname, title, city, state, position, contents, teamName, teamID);
                                mDatas.add(data);
                            }

                            mAdapter = new Job_TeamPlayer_Adapter(mDatas);
                            findTeamPlayerRecyclerView.setAdapter(mAdapter);

                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_find_teamplayer, container,false);
        findTeamPlayerRecyclerView = view.findViewById(R.id.job_view_find_teamplayer);
        findTeamPlayerRecyclerView.addOnItemTouchListener(new Job_TeamPlayerRecyclerViewItemClickListener(getActivity(), findTeamPlayerRecyclerView, this));

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), Job_Find_TeamPlayer_PostingPage.class);
        intent.putExtra(Job_TeamPlayer_Info.jobFindTeamplayer, mDatas.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
