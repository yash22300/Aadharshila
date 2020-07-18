package com.techtitudetribe.aadharshila;

public class ResultAdapter {
    public String date,total,correct,wrong;

    public ResultAdapter() {

    }

    public ResultAdapter(String date, String total, String correct, String wrong) {
        this.date = date;
        this.total = total;
        this.correct = correct;
        this.wrong = wrong;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getWrong() {
        return wrong;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }
}
