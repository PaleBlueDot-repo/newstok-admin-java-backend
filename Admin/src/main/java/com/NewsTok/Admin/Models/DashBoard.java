package com.NewsTok.Admin.Models;

import com.NewsTok.Admin.Dtos.newsreelsDto;

import java.util.ArrayList;
import java.util.List;

public class DashBoard {

    private String NewsReel_Views;
    private String Watchtime;
    private String Published;
    private String Likes;

    private   List<newsreelsDto> reelsList=new ArrayList<>();

    public String getNewsReel_Views() {
        return NewsReel_Views;
    }

    public void setNewsReel_Views(String newsReel_Views) {
        NewsReel_Views = newsReel_Views;
    }

    public String getWatchtime() {
        return Watchtime;
    }

    public void setWatchtime(String watchtime) {
        Watchtime = watchtime;
    }

    public String getPublished() {
        return Published;
    }

    public void setPublished(String published) {
        Published = published;
    }

    public String getLikes() {
        return Likes;
    }

    public void setLikes(String likes) {
        Likes = likes;
    }

    public List<newsreelsDto> getReelsList() {
        return reelsList;
    }

    public void setReelsList(List<newsreelsDto> reelsList) {
        this.reelsList = reelsList;
    }
}