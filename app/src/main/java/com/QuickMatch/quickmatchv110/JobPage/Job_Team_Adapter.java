package com.QuickMatch.quickmatchv110.JobPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;

import java.util.List;

public class Job_Team_Adapter extends RecyclerView.Adapter<Job_Team_Adapter.JobTeamViewHolder> {

    private List<Job_Team> datas;

    public Job_Team_Adapter(List<Job_Team> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public JobTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobTeamViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_find_team_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobTeamViewHolder holder, int position) {
        Job_Team data = datas.get(position);
        holder.userName.setText("작성자: " + data.getNickname());
        holder.title.setText("제목: " + data.getTitle());
        holder.city.setText("희망 지역: " + data.getCity() + ", " + data.getState());
        holder.position.setText("희망하는 포지션: " + data.getPosition());
        holder.contents.setText("내용: " + data.getContents());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class JobTeamViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView title;
        private TextView city;
        private TextView position;
        private TextView contents;


        public JobTeamViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.job_team_username);
            title = itemView.findViewById(R.id.job_team_title);
            city = itemView.findViewById(R.id.job_team_city);
            position = itemView.findViewById(R.id.job_team_position);
            contents = itemView.findViewById(R.id.job_team_contents);
        }
    }
}
