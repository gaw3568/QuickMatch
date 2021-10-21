package com.QuickMatch.quickmatchv110.Teampage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Team implements Parcelable {



    private String teamID ;//각 팀의 ID
    private String teamName;
    private String introduce;//팀 소개
    private String city ;//서울 경기
    private String state;//중랑, 남양주
    private String teamlevel;
    private String captainID;
    private String captainname;//주장 이름
    private ArrayList memberlist;
    private ArrayList managerlist;
    private int membernum =1;
    private String logouri;

    protected Team(Parcel in) {
        teamID = in.readString();
        teamName = in.readString();
        introduce = in.readString();
        city = in.readString();
        state = in.readString();
        teamlevel = in.readString();
        captainID = in.readString();
        captainname = in.readString();
        membernum = in.readInt();
        logouri = in.readString();
        invitingID = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getInvitingID() {
        return invitingID;
    }

    public void setInvitingID(String invitingID) {
        this.invitingID = invitingID;
    }

    public Team(String teamID, String teamName, String city, String state, String invitingID) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.city = city;
        this.state = state;
        this.invitingID = invitingID;
    }

    private String invitingID;//팀이 유저를 초대할 때 팀한테 생기는 코드

    public Team() {
        this.teamID = "";
        this.teamName = "";
        this.introduce = "";
        this.city = "";
        this.state = "";
        this.teamlevel = "";
        this.captainID = "";
        this.captainname = "";
        this.memberlist = null;
        this.managerlist = null;
        this.membernum = 0;
        this.logouri = "";
        this.invitingID="";
    }


    public Team(String teamID, String teamName, String introduce, String city,
                String state, String teamlevel, String captainID, String captainname, ArrayList memberlist, ArrayList managerlist, int membernum, String imageuri) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.introduce = introduce;
        this.city = city;
        this.state = state;
        this.teamlevel = teamlevel;
        this.captainID = captainID;
        this.captainname = captainname;
        this.memberlist = memberlist;
        this.managerlist = managerlist;
        this.membernum = membernum;
        this.logouri = imageuri;
    }


    public Team(String teamID, String teamName, String introduce, String city, String state, String teamlevel, String captainID, String captainname, ArrayList memberlist, ArrayList managerlist, int membernum) {
        this.teamID = teamID;
        this.teamName = teamName;
        this.introduce = introduce;
        this.city = city;
        this.state = state;
        this.teamlevel = teamlevel;
        this.captainID = captainID;
        this.captainname = captainname;
        this.memberlist = memberlist;
        this.managerlist = managerlist;
        this.membernum = membernum;
    }

    public String getTeamID() {
        return teamID;
    }

    public void setTeamID(String teamID) {
        this.teamID = teamID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public String getTeamlevel() {
        return teamlevel;
    }

    public void setTeamlevel(String teamlevel) {
        this.teamlevel = teamlevel;
    }

    public String getCaptainID() {
        return captainID;
    }

    public void setCaptainID(String captainID) {
        this.captainID = captainID;
    }

    public String getCaptainname() {
        return captainname;
    }

    public void setCaptainname(String captainname) {
        this.captainname = captainname;
    }

    public ArrayList getMemberlist() {
        return memberlist;
    }

    public void setMemberlist(ArrayList memberlist) {
        this.memberlist = memberlist;
    }

    public ArrayList getManagerlist() {
        return managerlist;
    }

    public void setManagerlist(ArrayList managerlist) {
        this.managerlist = managerlist;
    }

    public int getMembernum() {
        return membernum;
    }

    public void setMembernum(int membernum) {
        this.membernum = membernum;
    }

    public String getLogouri() {
        return logouri;
    }

    public void setLogouri(String logouri) {
        this.logouri = logouri;
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamID='" + teamID + '\'' +
                ", teamName='" + teamName + '\'' +
                ", introduce='" + introduce + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", teamlevel='" + teamlevel + '\'' +
                ", captainID='" + captainID + '\'' +
                ", captainname='" + captainname + '\'' +
                ", memberlist=" + memberlist +
                ", managerlist=" + managerlist +
                ", membernum=" + membernum +
                ", imageuri='" + logouri + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(teamID);
        parcel.writeString(teamName);
        parcel.writeString(introduce);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(teamlevel);
        parcel.writeString(captainID);
        parcel.writeString(captainname);
        parcel.writeInt(membernum);
        parcel.writeString(logouri);
        parcel.writeString(invitingID);
    }
}
