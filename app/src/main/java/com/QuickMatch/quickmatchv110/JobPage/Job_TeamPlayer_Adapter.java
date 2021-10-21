package com.QuickMatch.quickmatchv110.JobPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;

import java.util.List;

public class Job_TeamPlayer_Adapter extends RecyclerView.Adapter<Job_TeamPlayer_Adapter.JobTeamPlayerViewHolder> {

    private List<Job_TeamPlayer> datas;

    public Job_TeamPlayer_Adapter(List<Job_TeamPlayer> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public JobTeamPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobTeamPlayerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_find_teamplayer_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobTeamPlayerViewHolder holder, int position) {
        Job_TeamPlayer data = datas.get(position);
        holder.userName.setText("작성자: " + data.getNickname());
        holder.title.setText("제목: " + data.getTitle());
        holder.city.setText("활동 지역: " + data.getCity() + ", " + data.getState());
        holder.position.setText("필요한 포지션: " + data.getPosition());
        holder.contents.setText("내용: " + data.getContents());
        holder.teamName.setText("팀 정보: " + data.getTeamName());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class JobTeamPlayerViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView title;
        private TextView city;
        private TextView position;
        private TextView contents;
        private TextView teamName;


        public JobTeamPlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.job_find_teamplayer_username);
            title = itemView.findViewById(R.id.job_find_teamplayer_title);
            city = itemView.findViewById(R.id.job_find_teamplayer_city);
            position = itemView.findViewById(R.id.job_find_teamplayer_position);
            contents = itemView.findViewById(R.id.job_find_teamplayer_contents);
            teamName = itemView.findViewById(R.id.job_find_teamplayer_teamName);
        }
    }
}
