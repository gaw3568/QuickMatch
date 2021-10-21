package com.QuickMatch.quickmatchv110.USER;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class User implements Parcelable {



    private String userCode ;//user ID
    private String nickname;
    private String email;
    private String age;
    private ArrayList teamlist;
    private String imageuri;

    public User(String userCode, String nickname, String city, String state, String position, String invited) {
        this.userCode = userCode;
        this.nickname = nickname;
        this.city = city;
        this.state = state;
        this.position = position;
        this.invited = invited;
    }

    private String height;
    private String foot;
    private String city;
    private String state;
    private String position;

    protected User(Parcel in) {
        userCode = in.readString();
        nickname = in.readString();
        email = in.readString();
        age = in.readString();
        imageuri = in.readString();
        height = in.readString();
        foot = in.readString();
        city = in.readString();
        state = in.readString();
        position = in.readString();
        invited = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getInvited() {
        return invited;
    }

    public void setInvited(String invited) {
        this.invited = invited;
    }


    private String invited;


    public User() {
    }

    public User(String userCode, String nickname, String email, String age, ArrayList teamlist, String imageuri, String height, String foot, String city, String state, String position) {
        this.userCode = userCode;
        this.nickname = nickname;
        this.email = email;
        this.age = age;
        this.teamlist = teamlist;
        this.imageuri = imageuri;
        this.height = height;
        this.foot = foot;
        this.city = city;
        this.state = state;
        this.position = position;
    }



    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public ArrayList getTeamlist() {
        return teamlist;
    }

    public void setTeamlist(ArrayList teamlist) {
        this.teamlist = teamlist;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "User{" +
                "userCode='" + userCode + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", teamlist=" + teamlist +
                ", imageuri='" + imageuri + '\'' +
                ", height='" + height + '\'' +
                ", foot='" + foot + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", position='" + position + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userCode);
        parcel.writeString(nickname);
        parcel.writeString(email);
        parcel.writeString(age);
        parcel.writeString(imageuri);
        parcel.writeString(height);
        parcel.writeString(foot);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(position);
        parcel.writeString(invited);
    }
}
