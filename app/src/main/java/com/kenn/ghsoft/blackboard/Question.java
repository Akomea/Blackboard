package com.kenn.ghsoft.blackboard;


public class Question {
    private int id;
    private String title;
    private String shortdesc;
    private String translate;
    private int image;
    private int audio;

    public Question(int id, String title, String shortdesc, String translate, int image, int audio) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;
        this.translate = translate;
        this.image = image;
        this.audio = audio;

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

    public String getRating() {
        return translate;
    }

    public int getImage() {
        return image;
    }

    public int getAudio() {
        return audio;
    }

}
