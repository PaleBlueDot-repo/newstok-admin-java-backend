package com.NewsTok.Admin.Models;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name="news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String newspaperName;
    private String category;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String title;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String article;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String link;

    private String published;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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


}
