package com.QuickMatch.quickmatchv110.Post;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Post implements Parcelable {
    private String documentID;
    private String nickname;
    private String title;
    private String contents;
    private String teamname;
    private Date mDate;
    private String mDatePicker;
    private String mPhoneNumber;
    private String mUniform;
    private String mManner;
    private String mLocal;

    public Post() {
    }

    public Post(String documentID, String nickname, String title, String contents, String teamname, String mDatePicker, String mPhoneNumber, String mUniform, String mManner, String mLocal) {
        this.documentID = documentID;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.teamname = teamname;
        this.mDatePicker = mDatePicker;
        this.mPhoneNumber = mPhoneNumber;
        this.mUniform = mUniform;
        this.mManner = mManner;
        this.mLocal = mLocal;
    }

    protected Post(Parcel in) {
        documentID = in.readString();
        nickname = in.readString();
        title = in.readString();
        contents = in.readString();
        teamname = in.readString();
        mDatePicker = in.readString();
        mPhoneNumber = in.readString();
        mUniform = in.readString();
        mManner = in.readString();
        mLocal = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmDatePicker() {
        return mDatePicker;
    }

    public void setmDatePicker(String mDatePicker) {
        this.mDatePicker = mDatePicker;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmUniform() {
        return mUniform;
    }

    public void setmUniform(String mUniform) {
        this.mUniform = mUniform;
    }

    public String getmManner() {
        return mManner;
    }

    public void setmManner(String mManner) {
        this.mManner = mManner;
    }

    public String getmLocal() {
        return mLocal;
    }

    public void setmLocal(String mLocal) {
        this.mLocal = mLocal;
    }

    @Override
    public String toString() {
        return "Post{" +
                "documentID='" + documentID + '\'' +
                ", nickname='" + nickname + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", teamname='" + teamname + '\'' +
                ", mDate=" + mDate +
                ", mDatePicker='" + mDatePicker + '\'' +
                ", mPhoneNumber='" + mPhoneNumber + '\'' +
                ", mUniform='" + mUniform + '\'' +
                ", mManner='" + mManner + '\'' +
                ", mLocal='" + mLocal + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(documentID);
        parcel.writeString(nickname);
        parcel.writeString(title);
        parcel.writeString(contents);
        parcel.writeString(teamname);
        parcel.writeString(mDatePicker);
        parcel.writeString(mPhoneNumber);
        parcel.writeString(mUniform);
        parcel.writeString(mManner);
        parcel.writeString(mLocal);
    }
}
