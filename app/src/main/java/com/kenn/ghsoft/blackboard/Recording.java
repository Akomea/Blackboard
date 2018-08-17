package com.kenn.ghsoft.blackboard;

public class Recording {
    private int id;
    private String Uri;
    private String fileName;
    private boolean isPlaying;


    Recording(String uri, String fileName, boolean isPlaying, int id) {
        Uri = uri;
        this.fileName = fileName;
        this.isPlaying = isPlaying;
        this.id = id;
    }

    public String getUri() {
        return Uri;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public int getId() {
        return id;
    }

    public void setPlaying(boolean playing) {

        this.isPlaying = playing;
    }
}
