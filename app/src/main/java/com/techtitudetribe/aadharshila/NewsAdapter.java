package com.techtitudetribe.aadharshila;

public class NewsAdapter {
    String newsTitle,newsUrl,newsImageUrl,newsDescription;

    public NewsAdapter()
    {

    }

    public NewsAdapter(String newsTitle, String newsUrl, String newsImageUrl, String newsDescription) {
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.newsImageUrl = newsImageUrl;
        this.newsDescription = newsDescription;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }
}
