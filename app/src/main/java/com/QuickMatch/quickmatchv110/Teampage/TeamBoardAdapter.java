package com.QuickMatch.quickmatchv110.Teampage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;

import java.util.List;

public class TeamBoardAdapter extends RecyclerView.Adapter<TeamBoardAdapter.TeamBoardViewHolder>{
    private List<TeamBoard> datas;

    public TeamBoardAdapter(List<TeamBoard> datas) {
        this.datas = datas;
    }


    @NonNull
    @Override
    public TeamBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamBoardViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.teampage_teamboard_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamBoardViewHolder holder, int position) {
        TeamBoard data = datas.get(position);

        holder.title.setText(data.getTitle());
        holder.content.setText(data.getContents());
        holder.nickname.setText(data.getNickname());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class TeamBoardViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        private TextView nickname;

        public TeamBoardViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.teamboard_recycler_title);
            content = itemView.findViewById(R.id.teamboard_recycler_contents);
            nickname = itemView.findViewById(R.id.teamboard_recycler_nickname);

        }
    }
}
