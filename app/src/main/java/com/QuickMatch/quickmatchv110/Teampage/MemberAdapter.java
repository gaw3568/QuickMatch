package com.QuickMatch.quickmatchv110.Teampage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.QuickMatch.quickmatchv110.R;
import com.QuickMatch.quickmatchv110.USER.User;
import com.bumptech.glide.Glide;

import java.util.List;


public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {

    private List<User> datas;

    public MemberAdapter(List<User> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MemberViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.teampage_member_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        User data = datas.get(position);
        String image = data.getImageuri();

        holder.name.setText("이름: " + data.getNickname());
        holder.position.setText("포지션: " + data.getPosition());
        holder.foot.setText("주발: " + data.getFoot());
        holder.age.setText("나이: " + data.getAge());

        if (!image.equals("")) {
            Glide.with(holder.itemView.getContext())
                    .load(image)
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MemberViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView position;
        private TextView foot;
        private TextView age;
        private ImageView imageView;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.teampage_member_image);
            name = itemView.findViewById(R.id.teampage_member_nickname);
            position = itemView.findViewById(R.id.teampage_member_position);
            foot = itemView.findViewById(R.id.teampage_member_foot);
            age = itemView.findViewById(R.id.teampage_member_age);
        }
    }
}
