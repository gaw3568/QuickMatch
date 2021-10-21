package com.QuickMatch.quickmatchv110.Teampage;

import com.google.firebase.Timestamp;


public class TeamBoard {
    private String postID;
    private String nickname;
    private String title;
    private String contents;
    private Timestamp timestamp;

    public TeamBoard() {
    }

    public TeamBoard(String postID, String nickname, String title, String contents, Timestamp timestamp) {
        this.postID = postID;
        this.nickname = nickname;
        this.title = title;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
