package com.QuickMatch.quickmatchv110.Teampage;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;
import com.bumptech.glide.Glide;


import java.util.List;


public class MyteamAdapter extends RecyclerView.Adapter<MyteamAdapter.MyteamViewHolder> {

    private List<Team> datas;

    public MyteamAdapter(List<Team> datas) {
        this.datas = datas;
    }
    @NonNull
    @Override
    public MyteamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyteamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.teampage_team_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyteamViewHolder holder, int position) {
        Team data= datas.get(position);

        holder.teamName.setText("팀 이름: " + data.getTeamName());
        holder.captainName.setText("주장 이름: " + data.getCaptainname());
        holder.memberNum.setText("팀 인원: " + data.getMembernum()+"명");
        holder.teamLevel.setText("팀 수준: " + data.getTeamlevel());

        Glide.with(holder.itemView.getContext())
                .load(data.getLogouri())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyteamViewHolder extends RecyclerView.ViewHolder{
        private TextView teamName;
        private TextView captainName;
        private TextView memberNum;
        private TextView teamLevel;
        private ImageView imageView;

        public MyteamViewHolder(@NonNull View itemView) {
            super(itemView);

            teamName = itemView.findViewById(R.id.teampage_myteam_teamname);
            captainName = itemView.findViewById(R.id.teampage_myteam_captainname);
            memberNum = itemView.findViewById(R.id.teampage_myteam_membernum);
            teamLevel = itemView.findViewById(R.id.teampage_myteam_teamlevel);
            imageView = itemView.findViewById(R.id.teampage_myteam_logo);
        }
    }
}
