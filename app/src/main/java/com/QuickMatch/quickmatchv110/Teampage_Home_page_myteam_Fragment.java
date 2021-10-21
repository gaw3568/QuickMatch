package com.QuickMatch.quickmatchv110;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.Teampage.MyteamAdapter;
import com.QuickMatch.quickmatchv110.Teampage.Team;
import com.QuickMatch.quickmatchv110.Teampage.TeamRecyclerViewItemClickListener;
import com.QuickMatch.quickmatchv110.Teampage.Teaminfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Teampage_Home_page_myteam_Fragment extends Fragment implements TeamRecyclerViewItemClickListener.OnItemClickListener {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView myTeamRecyclerView;
    private MyteamAdapter tAdapter;
    private List<Team> tDatas = new ArrayList<>();

    @Override
    public void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            mStore.collection(Teaminfo.team)
                    .whereArrayContains(Teaminfo.memberlist, mAuth.getCurrentUser().getUid()).get()//배열에 포함된 값이 있으면
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (queryDocumentSnapshots != null) {
                                tDatas.clear();
                                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                    Map<String, Object> shot = document.getData();
                                    String teamId = String.valueOf(shot.get(Teaminfo.teamID));
                                    String teamName = String.valueOf(shot.get(Teaminfo.teamName));
                                    String introduce = String.valueOf(shot.get(Teaminfo.introduce));
                                    String city = String.valueOf(shot.get(Teaminfo.city));
                                    String state = String.valueOf(shot.get(Teaminfo.state));
                                    String teamlevel = String.valueOf(shot.get(Teaminfo.teamlevel));
                                    String captainID = String.valueOf(shot.get(Teaminfo.captainID));
                                    String captainname = String.valueOf(shot.get(Teaminfo.captainname));
                                    ArrayList memberlist = (ArrayList) shot.get(Teaminfo.memberlist);
                                    ArrayList managerlist = (ArrayList) shot.get(Teaminfo.managerlist);
                                    String logouri = String.valueOf(shot.get(Teaminfo.logouri));
                                    int membernum = memberlist.size();

                                    Team data = new Team(teamId, teamName, introduce, city,
                                            state, teamlevel, captainID, captainname, memberlist, managerlist, membernum, logouri);
                                    tDatas.add(data);
                                }
                                tAdapter = new MyteamAdapter(tDatas);
                                myTeamRecyclerView.setAdapter(tAdapter);
                            } else {
                                Log.d("asdf", "Error getting documents: ");
                            }
                        }
                    });
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teampage_home_myteamlist_fragment, container, false);
        myTeamRecyclerView = view.findViewById(R.id.teampage_Recyclerview);
        myTeamRecyclerView.addOnItemTouchListener(new TeamRecyclerViewItemClickListener(getActivity(), myTeamRecyclerView, this));

        return view;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), Teampage_Teampage.class);
        intent.putExtra(Teaminfo.team, tDatas.get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }
}
