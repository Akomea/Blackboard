package com.kenn.ghsoft.blackboard;


public class Question {
    private int id;
    private String title;
    private String shortdesc;
    private String translate;
    private int image;

    public Question(int id, String title, String shortdesc, String translate, int image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.translate = translate;
        this.image = image;

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public String getTranslation() {
        return translate;
    }

    public int getImage() {
        return image;
    }

}
