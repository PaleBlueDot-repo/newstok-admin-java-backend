package com.NewsTok.Admin.Models;

public class ScrapNewsResult {

    private String article;
    private String link;
    private String published;
    private String title;

    // Getters and setters

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Result{" +
                "article='" + article + '\'' +
                ", link='" + link + '\'' +
                ", published='" + published + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
