package com.techtitudetribe.aadharshila;

public class UpdatePdf {
    public String title,url,description;

    public UpdatePdf()
    {

    }

    public UpdatePdf(String title, String url,String description)
    {
        this.title=title;
        this.url=url;
        this.description=description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
