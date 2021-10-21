package com.QuickMatch.quickmatchv110.JobPage;

import android.os.Parcel;
import android.os.Parcelable;

public class Job_Team implements Parcelable {
    private String userID;//
    private String nickname;
    private String title;
    private String contents;
    private String position;
    private String city ;//서울 경기
    private String state;//중랑, 남양주

    public Job_Team() {
        this.userID = " ";
        this.nickname = " ";
        this.title = " ";
        this.city = " ";
        this.state = " ";
        this.position = " ";
        this.contents = " ";
    }

    public Job_Team(String post_ID, String nickname, String title, String city, String state, String position, String contents) {
        this.userID = post_ID;
        this.nickname = nickname;
        this.title = title;
        this.city = city;
        this.state = state;
        this.position = position;
        this.contents = contents;
    }

    protected Job_Team(Parcel in) {
        userID = in.readString();
        nickname = in.readString();
        title = in.readString();
        contents = in.readString();
        position = in.readString();
        city = in.readString();
        state = in.readString();
    }

    public static final Creator<Job_Team> CREATOR = new Creator<Job_Team>() {
        @Override
        public Job_Team createFromParcel(Parcel in) {
            return new Job_Team(in);
        }

        @Override
        public Job_Team[] newArray(int size) {
            return new Job_Team[size];
        }
    };

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    @Override
    public String toString() {
        return "Job_Team{" +
                "post_ID='" + userID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", position='" + position + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userID);
        dest.writeString(nickname);
        dest.writeString(title);
        dest.writeString(contents);
        dest.writeString(position);
        dest.writeString(city);
        dest.writeString(state);
    }
}

