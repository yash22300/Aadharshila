package com.techtitudetribe.aadharshila;

public class DoubtAdapter {
    public String stuDate,stuDoubt,stuName;

    public DoubtAdapter() {

    }

    public DoubtAdapter(String stuDate, String stuDoubt, String stuName) {
        this.stuDate = stuDate;
        this.stuDoubt = stuDoubt;
        this.stuName = stuName;
    }

    public String getStuDate() {
        return stuDate;
    }

    public void setStuDate(String stuDate) {
        this.stuDate = stuDate;
    }

    public String getStuDoubt() {
        return stuDoubt;
    }

    public void setStuDoubt(String stuDoubt) {
        this.stuDoubt = stuDoubt;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}
