package com.example.vizva.sns_app;

public class Post {


    public Post(String title, String article){
        this.title = title;
        this.article = title;
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

    private String title;
    private String article;
}
