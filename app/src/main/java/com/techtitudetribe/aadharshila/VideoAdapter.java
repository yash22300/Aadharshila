package com.techtitudetribe.aadharshila;

public class VideoAdapter {
    public String vidTitle,coverUrl,vidUrl,vidDescription;

    public VideoAdapter()
    {

    }

    public VideoAdapter(String vidTitle, String coverUrl,String vidUrl,String vidDescription)
    {
        this.vidTitle=vidTitle;
        this.coverUrl=coverUrl;
        this.vidUrl=vidUrl;
        this.vidDescription=vidDescription;
    }

    public String getVidTitle() {
        return vidTitle;
    }

    public void setVidTitle(String vidTitle) {
        this.vidTitle = vidTitle;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getVidUrl() {
        return vidUrl;
    }

    public void setVidUrl(String vidUrl) {
        this.vidUrl = vidUrl;
    }

    public String getVidDescription() {
        return vidDescription;
    }

    public void setVidDescription(String vidDescription) {
        this.vidDescription = vidDescription;
    }
}
