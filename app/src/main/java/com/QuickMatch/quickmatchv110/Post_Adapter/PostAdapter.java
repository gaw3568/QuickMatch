package com.QuickMatch.quickmatchv110.Post_Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.Post.Post;
import com.QuickMatch.quickmatchv110.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> datas;

    public PostAdapter(List<Post> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.matching_post, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post data = datas.get(position);

        holder.nickname.setText("작성자 : "+data.getNickname());
        holder.title.setText(data.getTitle());
        holder.contents.setText(data.getContents());
        holder.teamName.setText("팀 : "+data.getTeamname());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder{

        private TextView nickname;
        private TextView title;
        private TextView contents;
        private TextView teamName;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            nickname = itemView.findViewById(R.id.matching_post_nickname);
            title=itemView.findViewById(R.id.matching_post_title);
            contents=itemView.findViewById(R.id.matching_post_contents);
            teamName = itemView.findViewById(R.id.matching_post_teamname);
        }
    }
}
