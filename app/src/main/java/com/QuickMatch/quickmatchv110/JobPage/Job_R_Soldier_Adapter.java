package com.QuickMatch.quickmatchv110.JobPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;

import java.util.List;

public class Job_R_Soldier_Adapter extends RecyclerView.Adapter<Job_R_Soldier_Adapter.R_SoldierViewHolder> {

    private List<Job_R_Soldier> datas;

    public Job_R_Soldier_Adapter(List<Job_R_Soldier> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public R_SoldierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new R_SoldierViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_request_soldier_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull R_SoldierViewHolder holder, int position) {
        Job_R_Soldier data = datas.get(position);
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

    class R_SoldierViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView title;
        private TextView city;
        private TextView position;
        private TextView contents;

        public R_SoldierViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.job_request_soldier_username);
            title = itemView.findViewById(R.id.job_request_soldier_title);
            city = itemView.findViewById(R.id.job_request_soldier_city);
            position = itemView.findViewById(R.id.job_request_soldier_position);
            contents = itemView.findViewById(R.id.job_request_soldier_contents);
        }
    }
}
