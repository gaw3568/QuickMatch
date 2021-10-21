package com.QuickMatch.quickmatchv110.JobPage;

import android.os.Parcel;
import android.os.Parcelable;

public class Job_F_Soldier implements Parcelable {
    private String post_ID;
    private String nickname;
    private String title;
    private String contents;
    private String position;
    private String city ;//서울 경기
    private String state;//중랑, 남양주
    private String phoneNum;

    @Override
    public String toString() {
        return "Job_F_Soldier{" +
                "post_ID='" + post_ID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", position='" + position + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }

    public Job_F_Soldier(String post_ID, String nickname, String title, String city, String state, String position, String contents, String phoneNum) {
        this.post_ID = post_ID;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.city = city;
        this.state = state;
        this.position = position;
        this.phoneNum = phoneNum;
    }

    public Job_F_Soldier() {
        this.post_ID = " ";
        this.nickname = " ";
        this.title = " ";
        this.city = " ";
        this.state = " ";
        this.position = " ";
        this.contents = " ";
    }

    protected Job_F_Soldier(Parcel in) {
        post_ID = in.readString();
        nickname = in.readString();
        title = in.readString();
        contents = in.readString();
        position = in.readString();
        city = in.readString();
        state = in.readString();
        phoneNum = in.readString();
    }

    public static final Creator<Job_F_Soldier> CREATOR = new Creator<Job_F_Soldier>() {
        @Override
        public Job_F_Soldier createFromParcel(Parcel in) {
            return new Job_F_Soldier(in);
        }

        @Override
        public Job_F_Soldier[] newArray(int size) {
            return new Job_F_Soldier[size];
        }
    };

    public String getPost_ID() {
        return post_ID;
    }

    public void setPost_ID(String post_ID) {
        this.post_ID = post_ID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhoneNum() { return phoneNum; }

    public void setPhoneNum(String phoneNum) { this.phoneNum = phoneNum; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(post_ID);
        dest.writeString(nickname);
        dest.writeString(title);
        dest.writeString(contents);
        dest.writeString(position);
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(phoneNum);
    }
}
