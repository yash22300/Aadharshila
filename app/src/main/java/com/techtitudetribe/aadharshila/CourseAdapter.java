package com.techtitudetribe.aadharshila;

public class CourseAdapter {
    public String crsTitle,crsDescription,crsImageUrl,crsDuration,crsPrice,crsRequirement;

    public CourseAdapter()
    {

    }

    public CourseAdapter(String crsTitle, String crsDescription, String crsImageUrl, String crsDuration, String crsPrice, String crsRequirement) {
        this.crsTitle = crsTitle;
        this.crsDescription = crsDescription;
        this.crsImageUrl = crsImageUrl;
        this.crsDuration = crsDuration;
        this.crsPrice = crsPrice;
        this.crsRequirement = crsRequirement;
    }

    public String getCrsTitle() {
        return crsTitle;
    }

    public void setCrsTitle(String crsTitle) {
        this.crsTitle = crsTitle;
    }

    public String getCrsDescription() {
        return crsDescription;
    }

    public void setCrsDescription(String crsDescription) {
        this.crsDescription = crsDescription;
    }

    public String getCrsImageUrl() {
        return crsImageUrl;
    }

    public void setCrsImageUrl(String crsImageUrl) {
        this.crsImageUrl = crsImageUrl;
    }

    public String getCrsDuration() {
        return crsDuration;
    }

    public void setCrsDuration(String crsDuration) {
        this.crsDuration = crsDuration;
    }

    public String getCrsPrice() {
        return crsPrice;
    }

    public void setCrsPrice(String crsPrice) {
        this.crsPrice = crsPrice;
    }

    public String getCrsRequirement() {
        return crsRequirement;
    }

    public void setCrsRequirement(String crsRequirement) {
        this.crsRequirement = crsRequirement;
    }
}
