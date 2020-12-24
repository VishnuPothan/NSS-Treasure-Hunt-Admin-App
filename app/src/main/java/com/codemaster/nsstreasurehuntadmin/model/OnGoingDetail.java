package com.codemaster.nsstreasurehuntadmin.model;

import android.text.format.DateFormat;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public String getDate() {
        long timeL = (long) time;
        Date date = new Date(timeL);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

}
