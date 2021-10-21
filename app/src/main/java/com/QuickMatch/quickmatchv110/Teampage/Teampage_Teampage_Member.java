package com.QuickMatch.quickmatchv110.Teampage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;
import com.QuickMatch.quickmatchv110.USER.User;
import com.QuickMatch.quickmatchv110.USER.UserID;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Teampage_Teampage_Member extends Fragment {
    private Bundle bundle;
    private Team myteam;
    private String id;

    private FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private RecyclerView teamMemberRecyclerView;
    private List<User> uDatas = new ArrayList<>();
    private MemberAdapter mAdapter;

    @Override
    public void onStart() {
        //나중에 addSnapshotListener으로 바꿔야할수도있음
        super.onStart();
        mStore.collection(UserID.user)
                .whereArrayContains(UserID.temalist, id).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null) {
                            uDatas.clear();
                            for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                                Map<String, Object> shot = document.getData();
                                String userCode = String.valueOf(shot.get(UserID.userCode));
                                String nickname = String.valueOf(shot.get(UserID.nickname));
                                String email = String.valueOf(shot.get(UserID.email));
                                String age = String.valueOf(shot.get(UserID.age));
                                ArrayList teamlist = (ArrayList) shot.get(UserID.temalist);
                                String profileimage = String.valueOf(shot.get(UserID.imageuri));
                                String height = String.valueOf(shot.get(UserID.height));
                                String foot = String.valueOf(shot.get(UserID.foot));
                                String city = String.valueOf(shot.get(UserID.city));
                                String state = String.valueOf(shot.get(UserID.state));
                                String position = String.valueOf(shot.get(UserID.position));

                                User data = new User(userCode, nickname, email, age, teamlist, profileimage, height, foot,
                                        city, state, position);
                                uDatas.add(data);
                            }
                            mAdapter = new MemberAdapter(uDatas);
                            teamMemberRecyclerView.setAdapter(mAdapter);
                        } else {
                            Log.d("asdf", "Error getting documents: ");
                        }
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teampage_teampage_member, container, false);

        bundle = getArguments();
        myteam = bundle.getParcelable(Teaminfo.team);
        id = myteam.getTeamID();

        teamMemberRecyclerView = view.findViewById(R.id.teampage_member_recyclerview);

        return view;
    }
}
