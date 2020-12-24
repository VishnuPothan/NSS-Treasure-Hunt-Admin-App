package com.codemaster.nsstreasurehuntadmin.model;

public class OnGoingDetail {
    private String currQno;
    private Object time;

    public OnGoingDetail() {
        // Default constructor required
    }

    public OnGoingDetail(String currQno, Object time) {
        this.currQno = currQno;
        this.time = time;
    }

    public String getCurrQno() {
        return currQno;
    }

    public void setCurrQno(String currQno) {
        this.currQno = currQno;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }
}
