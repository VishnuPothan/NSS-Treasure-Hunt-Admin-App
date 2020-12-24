package com.codemaster.nsstreasurehuntadmin.model;

public class StartQuizDetails {
    String time;
    boolean start;

    public StartQuizDetails() {
        //required for getting firebase
    }

    public StartQuizDetails(String time, boolean start) {
        this.time = time;
        this.start = start;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }
}
