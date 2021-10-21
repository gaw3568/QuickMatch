package com.QuickMatch.quickmatchv110.Teampage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;
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

public class Teampage_Teampage_Teamboard extends Fragment implements TeamRecyclerViewItemClickListener.OnItemClickListener{
    private Bundle bundle;
    private Team myteam;
    private String id;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView teamBoardRecyclerView;
    private List<TeamBoard> uDatas = new ArrayList<>();
    private TeamBoardAdapter tAdapter;

    @Override
    public void onStart() {

        mStore.collection(Teaminfo.team).document(id)
                .collection(TeamPostID.board)
                .orderBy(TeamPostID.timestamp, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (queryDocumentSnapshots != null) {
                            uDatas.clear();
                            for(DocumentSnapshot snap : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = snap.getData();
                                String postID =  String.valueOf(shot.get(TeamPostID.postID));
                                String title =  String.valueOf(shot.get(TeamPostID.title));
                                String content =  String.valueOf(shot.get(TeamPostID.contents));
                                String nickname =  String.valueOf(shot.get(TeamPostID.nickname));
                                Timestamp timestamp = (Timestamp) shot.get(TeamPostID.timestamp);

                                TeamBoard data = new TeamBoard(postID, nickname, title, content, timestamp);
                                uDatas.add(data);
                            }
                            tAdapter = new TeamBoardAdapter(uDatas);
                            teamBoardRecyclerView.setAdapter(tAdapter);
                        }
                    }
                });

        super.onStart();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teampage_teampage_teamboard, container, false);

        bundle = getArguments();
        myteam = bundle.getParcelable(Teaminfo.team);
        id = myteam.getTeamID();

        teamBoardRecyclerView = view.findViewById(R.id.teampage_board_recyclerview);

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
