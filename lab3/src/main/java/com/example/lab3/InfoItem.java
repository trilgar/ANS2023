package com.example.lab3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class InfoItem {
    private String title;
    private String textBody;
    private String source;
    @JsonProperty("PubDate")
    private String pubDate;
    @JsonProperty("URL")
    private String url;

    public InfoItem() {
    }

    public InfoItem(String title, String textBody, String source, String pubDate, String url) {
        this.title = title;
        this.textBody = textBody;
        this.source = source;
        this.pubDate = pubDate;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextBody() {
        return textBody;
    }

    public void setTextBody(String textBody) {
        this.textBody = textBody;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (InfoItem) obj;
        return Objects.equals(this.title, that.title) &&
                Objects.equals(this.textBody, that.textBody) &&
                Objects.equals(this.source, that.source) &&
                Objects.equals(this.pubDate, that.pubDate) &&
                Objects.equals(this.url, that.url);
    }

    @Override
    public String toString() {
        return "InfoItem[" +
                "title=" + title + ", " +
                "textBody=" + textBody + ", " +
                "source=" + source + ", " +
                "pubDate=" + pubDate + ", " +
                "url=" + url + ']';
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, textBody, source, pubDate, url);
    }


}
