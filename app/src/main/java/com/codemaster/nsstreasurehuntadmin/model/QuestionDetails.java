package com.codemaster.nsstreasurehuntadmin.model;

public class QuestionDetails {
    private String qNo;
    private String img;
    private boolean imgAvl;
    private String answer;

    public QuestionDetails() {
        //required
    }

    public QuestionDetails(String qNo, String img, boolean imgAvl, String answer) {
        this.qNo = qNo;
        this.img = img;
        this.imgAvl = imgAvl;
        this.answer = answer;
    }

    public String getqNo() {
        return qNo;
    }

    public void setqNo(String qNo) {
        this.qNo = qNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isImgAvl() {
        return imgAvl;
    }

    public void setImgAvl(boolean imgAvl) {
        this.imgAvl = imgAvl;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
