package com.QuickMatch.quickmatchv110.JobPage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;

import java.util.List;

public class Job_F_Soldier_Adapter extends RecyclerView.Adapter<Job_F_Soldier_Adapter.F_SoldierViewHolder> {

    private List<Job_F_Soldier> datas;

    public Job_F_Soldier_Adapter(List<Job_F_Soldier> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public F_SoldierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new F_SoldierViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.job_find_soldier_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull F_SoldierViewHolder holder, int position) {
        Job_F_Soldier data = datas.get(position);
        holder.userName.setText("작성자: " + data.getNickname());
        holder.title.setText("제목: " + data.getTitle());
        holder.city.setText("활동 지역: " + data.getCity() + ", " + data.getState());
        holder.position.setText("필요한 포지션: " + data.getPosition());
        holder.contents.setText("내용: " + data.getContents());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class F_SoldierViewHolder extends RecyclerView.ViewHolder {

        private TextView userName;
        private TextView title;
        private TextView city;
        private TextView position;
        private TextView contents;

        public F_SoldierViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.job_find_soldier_username);
            title = itemView.findViewById(R.id.job_find_soldier_title);
            city = itemView.findViewById(R.id.job_find_soldier_city);
            position = itemView.findViewById(R.id.job_find_soldier_position);
            contents = itemView.findViewById(R.id.job_find_soldier_contents);
        }
    }
}
