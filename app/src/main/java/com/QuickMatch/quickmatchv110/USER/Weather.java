package com.QuickMatch.quickmatchv110.USER;

public class Weather {


    private String local;// 지역
    private String hour;
    private String temp;
    private String weather;
    private String updatedata;//업데이트 시간


    public Weather() {
    }

    public Weather(String local, String hour, String temp, String weather, String updatedata) {
        this.local = local;
        this.hour = hour;
        this.temp = temp;
        this.weather = weather;
        this.updatedata = updatedata;
    }

    public String getUpdatedata() {
        return updatedata;
    }

    public void setUpdatedata(String updatedata) {
        this.updatedata = updatedata;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
