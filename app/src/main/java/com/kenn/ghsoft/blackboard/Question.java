package com.kenn.ghsoft.blackboard;


public class Question {
    private int id;
    private String title;
    private String shortdesc;
    private String translation;
    private int image;

    public Question(int id, String title, String shortdesc, String translation, int image) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.translation = translation;
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
        return translation;
    }

    public int getImage() {
        return image;
    }

}
