package com.QuickMatch.quickmatchv110.JobPage;

import android.os.Parcel;
import android.os.Parcelable;

public class Job_TeamPlayer implements Parcelable {
    private String userID;
    private String nickname;
    private String title;
    private String contents;
    private String city ;//서울 경기
    private String state;//중랑, 남양주
    private String position;
    private String teamName;
    private String teamID;

    public Job_TeamPlayer(String userID, String nickname, String title, String city, String state, String position, String contents, String teamName, String teamID) {
        this.userID = userID;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.city = city;
        this.state = state;
        this.position = position;
        this.teamName = teamName;
        this.teamID = teamID;
    }


    protected Job_TeamPlayer(Parcel in) {
        userID = in.readString();
        nickname = in.readString();
        title = in.readString();
        contents = in.readString();
        city = in.readString();
        state = in.readString();
        position = in.readString();
        teamName = in.readString();
        teamID = in.readString();
    }

    public static final Creator<Job_TeamPlayer> CREATOR = new Creator<Job_TeamPlayer>() {
        @Override
        public Job_TeamPlayer createFromParcel(Parcel in) {
            return new Job_TeamPlayer(in);
        }

        @Override
        public Job_TeamPlayer[] newArray(int size) {
            return new Job_TeamPlayer[size];
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

    public String getPosition() { return position; }

    public void setPosition(String position) { this.position = position; }

    public String getTeamName() { return teamName; }

    public void setTeamName(String teamName) { this.teamName = teamName; }

    public String getTeamID() { return teamID; }

    public void setTeamID(String teamID) { this.teamID = teamID; }

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
        dest.writeString(city);
        dest.writeString(state);
        dest.writeString(position);
        dest.writeString(teamName);
        dest.writeString(teamID);
    }

    @Override
    public String toString() {
        return "Job_TeamPlayer{" +
                "userID='" + userID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", position='" + position + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamID='" + teamID + '\'' +
                '}';
    }
}
